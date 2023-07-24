package snake;

// imports necessary libraries for Java swing

import javax.swing.*;
import java.awt.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunSnake implements Runnable {
    public void run() {

        // Top-level frame in which game components live.
        final JFrame frame = new JFrame("Snake");
        frame.setLocation(300, 300);

        // Score panel
        final JPanel score_panel = new JPanel();
        frame.add(score_panel, BorderLayout.SOUTH);
        final JLabel score = new JLabel();
        score_panel.add(score);

        // Main playing area
        final GameCourt court = new GameCourt(score);
        frame.add(court, BorderLayout.CENTER);

        score.setText("Points: " + court.getPoints());

        // Control panel
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Reset button
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> court.reset());
        control_panel.add(reset);

        // Instructions button
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(e -> court.showInstructions());
        control_panel.add(instructions);

        // Save button
        final JButton save = new JButton("Save");
        save.addActionListener(e -> court.save());
        control_panel.add(save);

        // Load button
        final JButton load = new JButton("Load");
        load.addActionListener(e -> court.load());
        control_panel.add(load);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }
}