package snake;

import javax.swing.*;
import java.awt.*;

public class Orange extends Fruit {
    public Orange() {
        super(Color.ORANGE);
    }

    public void effect(Snake snake, Timer timer, JLabel score, GameCourt court) {
        snake.setColor(Color.BLACK);
        snake.lengthen();
        timer.setDelay(75);
        court.addPoints(3);
        score.setText("Points: " + court.getPoints());
    }
}
