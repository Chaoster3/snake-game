package snake;

import javax.swing.*;
import java.awt.*;

public class Apple extends Fruit {
    public Apple() {
        super(Color.RED);
    }

    public void effect(Snake snake, Timer timer, JLabel score, GameCourt court) {
        snake.setColor(Color.GREEN);
        snake.lengthen();
        timer.setDelay(200);
        court.addPoints(1);
        score.setText("Points: " + court.getPoints());
    }
}
