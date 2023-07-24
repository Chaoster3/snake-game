package snake;

import javax.swing.*;
import java.awt.*;

public class Grape extends Fruit {

    public Grape() {
        super(Color.MAGENTA);
    }

    public void effect(Snake snake, Timer timer, JLabel score, GameCourt court) {
        snake.setColor(Color.BLUE);
        snake.lengthen();
        snake.lengthen();
        timer.setDelay(125);
        court.addPoints(2);
        score.setText("Points: " + court.getPoints());
    }
}
