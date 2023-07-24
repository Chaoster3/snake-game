package snake;

import javax.swing.*;
import java.awt.*;

public abstract class Fruit {
    public static final int SIZE = 20;
    private int x;
    private int y;
    private Color color;

    public Fruit(Color c) {
        this.x = (int) (Math.random() * 380);
        this.y = (int) (Math.random() * 380);
        color = c;
    }

    // method to change the x and y coordinates so that the fruit does not intersect with the snake
    public void nonIntersection(Snake snake) {
        while (snake.fruitCollision(this)) {
            x = (int) (Math.random() * 380);
            y = (int) (Math.random() * 380);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public abstract void effect(Snake snake, Timer timer, JLabel score, GameCourt court);

    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(x, y, SIZE, SIZE);
    }
}