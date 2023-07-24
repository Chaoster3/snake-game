package snake;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact
 * with one another.
 */
public class GameCourt extends JPanel {

    private Snake snake; // the snake, keyboard control
    private Fruit fruit; // a fruit, does not move
    private boolean playing = false; // whether the game is running
    private final JLabel score; // current score text
    private int points = 0; // current number of points

    // Game constants
    public static final int COURT_WIDTH = 400;
    public static final int COURT_HEIGHT = 400;

    // Initial update interval for timer, in milliseconds
    public static final int INTERVAL = 200;

    private Timer timer;
    private boolean wantInstructions = false; // Whether the user wants instructions
    private static BufferedImage instructionsPage; // An image with the instructions page
    private String fruitType = null; // The type of fruit in play (used for saving/loading)

    public GameCourt(JLabel score) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Create and start timer.
        timer = new Timer(INTERVAL, e -> tick());
        timer.start();

        // Try to read the instructions image file.
        try {
            instructionsPage = ImageIO.read(new File("files/instructions.jpg"));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

        // Enable keyboard focus on the court area.
        setFocusable(true);

        // This key listener allows the user to change the snake's direction with the
        // arrow keys.
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && snake.getDirection() != "RIGHT") {
                    snake.setDirection("LEFT");
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && snake.getDirection() != "LEFT") {
                    snake.setDirection("RIGHT");
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && snake.getDirection() != "UP") {
                    snake.setDirection("DOWN");
                } else if (e.getKeyCode() == KeyEvent.VK_UP && snake.getDirection() != "DOWN") {
                    snake.setDirection("UP");
                }
            }
        });

        this.score = score;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        snake = new Snake();
        // Randomize which fruit type is spawned
        if (Math.random() < 0.3333) {
            fruit = new Grape();
            fruitType = "GRAPE";
        } else if (Math.random() < 0.5) {
            fruit = new Orange();
            fruitType = "ORANGE";
        } else {
            fruit = new Apple();
            fruitType = "APPLE";
        }
        fruit.nonIntersection(snake);
        timer.setDelay(200);

        playing = true;
        points = 0;
        score.setText("Score: " + points);

        wantInstructions = false;

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    public void showInstructions() {
        wantInstructions = !wantInstructions;
        repaint();
        requestFocusInWindow();
    }
    public int getPoints() {
        return points;
    }
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
        if (playing) {
            // advance the snake and all of its body parts
            snake.move();

            // check for different types of collisions
            if (snake.selfCollision() || snake.boundaryCollision()) {
                playing = false;
                score.setText("Score: " + points);
            }
            if (snake.fruitCollision(fruit)) {
                fruit.effect(snake, timer, score, this);
                if (Math.random() < 0.3333) {
                    fruit = new Grape();
                    fruitType = "GRAPE";
                } else if (Math.random() < 0.5) {
                    fruit = new Orange();
                    fruitType = "ORANGE";
                } else {
                    fruit = new Apple();
                    fruitType = "APPLE";
                }
                fruit.nonIntersection(snake);
            }

            // update the display
            repaint();
        }
    }

    public void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("files/savedState.txt"));
            // save current points
            writer.write(Integer.toString(points));
            writer.newLine();
            // save whether game is playing
            writer.write(String.valueOf(playing));
            writer.newLine();
            // save current timer interval
            writer.write(Integer.toString(timer.getDelay()));
            writer.newLine();
            // save whether instructions page is displayed
            writer.write(String.valueOf(wantInstructions));
            writer.newLine();
            // save current fruit type
            writer.write(fruitType);
            writer.newLine();
            // save fruit x coordinate
            writer.write(Integer.toString(fruit.getX()));
            writer.newLine();
            // save fruit y coordinate
            writer.write(Integer.toString(fruit.getY()));
            writer.newLine();
            // save snake color values
            writer.write(Integer.toString(snake.getColor().getRed()));
            writer.newLine();
            writer.write(Integer.toString(snake.getColor().getGreen()));
            writer.newLine();
            writer.write(Integer.toString(snake.getColor().getBlue()));
            writer.newLine();
            // save snake direction
            writer.write(snake.getDirection());
            writer.newLine();
            // save snake body part x coordinates
            LinkedList<Integer> xvals = snake.getXvals();
            for (int i = 0; i < xvals.size(); i++) {
                writer.write(Integer.toString(xvals.get(i)));
                writer.write(",");
            }
            writer.newLine();
            // save snake body part y coordinates
            LinkedList<Integer> yvals = snake.getYvals();
            for (int i = 0; i < yvals.size(); i++) {
                writer.write(Integer.toString(yvals.get(i)));
                writer.write(",");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        requestFocusInWindow();
    }

    public void load() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("files/savedState.txt"));
            // load current points
            points = Integer.parseInt(reader.readLine());
            score.setText("Score: " + points);
            // load whether game is playing
            playing = Boolean.parseBoolean(reader.readLine());
            // load timer interval
            timer.setDelay(Integer.parseInt(reader.readLine()));
            // load correct fruit type
            wantInstructions = Boolean.parseBoolean(reader.readLine());
            String type = reader.readLine();
            if (type.equals("APPLE")) {
                fruit = new Apple();
                fruitType = "APPLE";
            }
            if (type.equals("GRAPE")) {
                fruit = new Grape();
                fruitType = "GRAPE";
            }
            if (type.equals("ORANGE")) {
                fruit = new Orange();
                fruitType = "GRAPE";
            }
            // load fruit x coordinate
            fruit.setX(Integer.parseInt(reader.readLine()));
            // load fruit y coordinate
            fruit.setY(Integer.parseInt(reader.readLine()));
            // create snake
            snake = new Snake();
            // load snake color
            snake.setColor(
                    new Color(
                            Integer.parseInt(reader.readLine()),
                            Integer.parseInt(reader.readLine()),
                            Integer.parseInt(reader.readLine())
                    )
            );
            // load snake direction
            snake.setDirection(reader.readLine());
            // load snake body part x coordinates
            LinkedList<Integer> xvals = new LinkedList<Integer>();
            for (String i : reader.readLine().split(",")) {
                xvals.add(Integer.parseInt(i));
            }
            snake.setXvals(xvals);
            // load snake body part y coordinates
            LinkedList<Integer> yvals = new LinkedList<Integer>();
            for (String i : reader.readLine().split(",")) {
                yvals.add(Integer.parseInt(i));
            }
            snake.setYvals(yvals);

            reader.close();
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        snake.draw(g);
        fruit.draw(g);
        if (wantInstructions) {
            g.drawImage(instructionsPage, 50, 0, 300, 400, null);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}