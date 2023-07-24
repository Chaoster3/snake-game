package snake;

import java.awt.*;
import java.util.LinkedList;

public class Snake {
    public static final int SIZE = 20;
    private String direction;
    private Color color;
    private LinkedList<Integer> xvals;
    private LinkedList<Integer> yvals;

    public Snake() {

        direction = "LEFT";
        color = Color.GREEN;
        xvals = new LinkedList<>();
        xvals.add(200);
        xvals.add(220);
        xvals.add(240);
        yvals = new LinkedList<>();
        yvals.add(200);
        yvals.add(200);
        yvals.add(200);
    }

    public void move() {
        for (int i = xvals.size() - 1; i >= 1; i--) {
            xvals.set(i, xvals.get(i - 1));
            yvals.set(i, yvals.get(i - 1));
        }
        if (direction.equals("UP")) {
            yvals.set(0, yvals.get(0) - 21);
        } else if (direction.equals("DOWN")) {
            yvals.set(0, yvals.get(0) + 21);
        } else if (direction.equals("RIGHT")) {
            xvals.set(0, xvals.get(0) + 21);
        } else if (direction.equals("LEFT")) {
            xvals.set(0, xvals.get(0) - 21);
        }
    }

    public LinkedList<Integer> getXvals() {
        return (LinkedList<Integer>) xvals.clone();
    }

    public LinkedList<Integer> getYvals() {
        return (LinkedList<Integer>) yvals.clone();
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String d) {
        direction = d;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color c) {
        color = c;
    }

    public void setXvals(LinkedList<Integer> vals) {
        xvals = (LinkedList<Integer>) vals.clone();
    }

    public void setYvals(LinkedList<Integer> vals) {
        yvals = (LinkedList<Integer>) vals.clone();
    }

    public void lengthen() {
        xvals.add(xvals.getLast());
        yvals.add(yvals.getLast());
    }

    public boolean selfCollision() {
        boolean collision = false;
        for (int i = 1; i < xvals.size(); i++) {
            collision = (collision || (xvals.getFirst() + SIZE >= xvals.get(i)
                    && yvals.getFirst() + SIZE >= yvals.get(i)
                    && xvals.get(i) + SIZE >= xvals.getFirst()
                    && yvals.get(i) + SIZE >= yvals.getFirst()));
        }
        return collision;
    }

    public boolean fruitCollision(Fruit fruit) {
        boolean collision = false;
        for (int i = 0; i < xvals.size(); i++) {
            collision = (collision || (xvals.get(i) + SIZE >= fruit.getX()
                    && yvals.get(i) + SIZE >= fruit.getY()
                    && fruit.getX() + SIZE >= xvals.get(i)
                    && fruit.getY() + SIZE >= yvals.get(i)));
        }
        return collision;
    }

    public boolean boundaryCollision() {
        return (xvals.getFirst() + SIZE >= GameCourt.COURT_WIDTH
                || xvals.getFirst() <= 0
                || yvals.getFirst() + SIZE >= GameCourt.COURT_HEIGHT
                || yvals.getFirst() <= 0);
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
        for (int i = 0; i < this.xvals.size(); i++) {
            g.fillOval(this.xvals.get(i), this.yvals.get(i), SIZE, SIZE);
        }
    }
}