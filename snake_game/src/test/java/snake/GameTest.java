package snake;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testSnakeSpawn() {
        Snake snake = new Snake();
        assertEquals(snake.getDirection(), "LEFT");
        LinkedList<Integer> xvals = snake.getXvals();
        LinkedList<Integer> yvals = snake.getYvals();
        assertEquals(3, xvals.size());
        assertEquals(3, yvals.size());
        assertEquals(200, xvals.get(0));
        assertEquals(220, xvals.get(1));
        assertEquals(240, xvals.get(2));
        assertEquals(200, yvals.get(0));
        assertEquals(200, yvals.get(1));
        assertEquals(200, yvals.get(2));
    }

    @Test
    public void testSnakeMove() {
        Snake snake = new Snake();
        snake.setDirection("UP");
        snake.move();
        LinkedList<Integer> xvals = snake.getXvals();
        LinkedList<Integer> yvals = snake.getYvals();
        assertEquals(3, xvals.size());
        assertEquals(3, yvals.size());
        assertEquals(200, xvals.get(0));
        assertEquals(200, xvals.get(1));
        assertEquals(220, xvals.get(2));
        assertEquals(179, yvals.get(0));
        assertEquals(200, yvals.get(1));
        assertEquals(200, yvals.get(2));
    }

    @Test
    public void testSnakeLengthen() {
        Snake snake = new Snake();
        snake.setDirection("DOWN");
        snake.lengthen();
        snake.lengthen();
        LinkedList<Integer> xvals = snake.getXvals();
        LinkedList<Integer> yvals = snake.getYvals();
        assertEquals(5, xvals.size());
        assertEquals(5, yvals.size());
        assertEquals(200, xvals.get(0));
        assertEquals(220, xvals.get(1));
        assertEquals(240, xvals.get(2));
        assertEquals(240, xvals.get(3));
        assertEquals(240, xvals.get(4));
        assertEquals(200, yvals.get(0));
        assertEquals(200, yvals.get(1));
        assertEquals(200, yvals.get(2));
        assertEquals(200, yvals.get(3));
        assertEquals(200, yvals.get(4));
    }

    @Test
    public void testSnakeSelfCollision() {
        Snake snake = new Snake();
        snake.setDirection("RIGHT");
        LinkedList<Integer> xvals = new LinkedList<Integer>();
        LinkedList<Integer> yvals = new LinkedList<Integer>();
        xvals.add(179);
        xvals.add(179);
        xvals.add(200);
        xvals.add(200);
        xvals.add(200);
        yvals.add(179);
        yvals.add(200);
        yvals.add(200);
        yvals.add(179);
        yvals.add(168);
        snake.setXvals(xvals);
        snake.setYvals(yvals);
        assertFalse(snake.selfCollision());
        snake.move();
        assertTrue(snake.selfCollision());
    }

    @Test
    public void testSnakeFruitCollision() {
        Fruit fruit = new Apple();
        fruit.setX(158);
        fruit.setY(179);
        Snake snake = new Snake();
        snake.setDirection("LEFT");
        LinkedList<Integer> xvals = new LinkedList<Integer>();
        LinkedList<Integer> yvals = new LinkedList<Integer>();
        xvals.add(179);
        xvals.add(179);
        xvals.add(200);
        xvals.add(200);
        xvals.add(200);
        yvals.add(179);
        yvals.add(200);
        yvals.add(200);
        yvals.add(179);
        yvals.add(168);
        snake.setXvals(xvals);
        snake.setYvals(yvals);
        assertFalse(snake.fruitCollision(fruit));
        snake.move();
        assertTrue(snake.fruitCollision(fruit));
    }

    @Test
    public void testSnakeBoundaryCollisionLeft() {
        Snake snake = new Snake();
        snake.setDirection("LEFT");
        LinkedList<Integer> xvals = new LinkedList<Integer>();
        LinkedList<Integer> yvals = new LinkedList<Integer>();
        xvals.add(1);
        xvals.add(22);
        xvals.add(43);
        yvals.add(200);
        yvals.add(200);
        yvals.add(200);
        snake.setXvals(xvals);
        snake.setYvals(yvals);
        assertFalse(snake.boundaryCollision());
        snake.move();
        assertTrue(snake.boundaryCollision());
    }

    @Test
    public void testSnakeBoundaryCollisionRight() {
        Snake snake = new Snake();
        snake.setDirection("RIGHT");
        LinkedList<Integer> xvals = new LinkedList<Integer>();
        LinkedList<Integer> yvals = new LinkedList<Integer>();
        xvals.add(379);
        xvals.add(358);
        xvals.add(337);
        yvals.add(200);
        yvals.add(200);
        yvals.add(200);
        snake.setXvals(xvals);
        snake.setYvals(yvals);
        assertFalse(snake.boundaryCollision());
        snake.move();
        assertTrue(snake.boundaryCollision());
    }

    @Test
    public void testSnakeBoundaryCollisionTop() {
        Snake snake = new Snake();
        snake.setDirection("UP");
        LinkedList<Integer> xvals = new LinkedList<Integer>();
        LinkedList<Integer> yvals = new LinkedList<Integer>();
        xvals.add(200);
        xvals.add(200);
        xvals.add(200);
        yvals.add(21);
        yvals.add(42);
        yvals.add(63);
        snake.setXvals(xvals);
        snake.setYvals(yvals);
        assertFalse(snake.boundaryCollision());
        snake.move();
        assertTrue(snake.boundaryCollision());
    }

    @Test
    public void testSnakeBoundaryCollisionBottom() {
        Snake snake = new Snake();
        snake.setDirection("DOWN");
        LinkedList<Integer> xvals = new LinkedList<Integer>();
        LinkedList<Integer> yvals = new LinkedList<Integer>();
        xvals.add(200);
        xvals.add(200);
        xvals.add(200);
        yvals.add(379);
        yvals.add(358);
        yvals.add(337);
        snake.setXvals(xvals);
        snake.setYvals(yvals);
        assertFalse(snake.boundaryCollision());
        snake.move();
        assertTrue(snake.boundaryCollision());
    }

    @Test
    public void testFruitNonIntersection() {
        Fruit fruit = new Orange();
        fruit.setX(220);
        fruit.setY(200);
        Snake snake = new Snake();
        snake.setDirection("DOWN");
        LinkedList<Integer> xvals = new LinkedList<Integer>();
        LinkedList<Integer> yvals = new LinkedList<Integer>();
        xvals.add(200);
        xvals.add(220);
        xvals.add(240);
        yvals.add(200);
        yvals.add(200);
        yvals.add(200);
        snake.setXvals(xvals);
        snake.setYvals(yvals);
        assertTrue(snake.fruitCollision(fruit));
        fruit.nonIntersection(snake);
        assertFalse(snake.boundaryCollision());
    }

    @Test
    public void testAppleEffect() {
        GameCourt court = new GameCourt(null);
        Timer timer = new Timer(100, null);
        JLabel label = new JLabel();
        Fruit fruit = new Apple();
        Snake snake = new Snake();
        snake.setColor(Color.BLACK);
        fruit.effect(snake, timer, label, court);
        LinkedList<Integer> xvals = snake.getXvals();
        LinkedList<Integer> yvals = snake.getYvals();
        assertEquals(Color.GREEN, snake.getColor());
        assertEquals(4, xvals.size());
        assertEquals(4, yvals.size());
        assertEquals(200, timer.getDelay());
        assertEquals(1, court.getPoints());
        assertEquals("Points: 1", label.getText());
    }

    @Test
    public void testGrapeEffect() {
        GameCourt court = new GameCourt(null);
        Timer timer = new Timer(200, null);
        JLabel label = new JLabel();
        Fruit fruit = new Grape();
        Snake snake = new Snake();
        snake.setColor(Color.BLACK);
        fruit.effect(snake, timer, label, court);
        LinkedList<Integer> xvals = snake.getXvals();
        LinkedList<Integer> yvals = snake.getYvals();
        assertEquals(Color.BLUE, snake.getColor());
        assertEquals(5, xvals.size());
        assertEquals(5, yvals.size());
        assertEquals(125, timer.getDelay());
        assertEquals(2, court.getPoints());
        assertEquals("Points: 2", label.getText());
    }

    @Test
    public void testOrangeEffect() {
        GameCourt court = new GameCourt(null);
        Timer timer = new Timer(200, null);
        JLabel label = new JLabel();
        Fruit fruit = new Orange();
        Snake snake = new Snake();
        snake.setColor(Color.GREEN);
        fruit.effect(snake, timer, label, court);
        LinkedList<Integer> xvals = snake.getXvals();
        LinkedList<Integer> yvals = snake.getYvals();
        assertEquals(Color.BLACK, snake.getColor());
        assertEquals(4, xvals.size());
        assertEquals(4, yvals.size());
        assertEquals(75, timer.getDelay());
        assertEquals(3, court.getPoints());
        assertEquals("Points: 3", label.getText());
    }
}
