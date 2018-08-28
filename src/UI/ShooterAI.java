package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ShooterAI extends JFrame implements ActionListener{
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 750;
    private static final int DELAY = 10;
    private Timer timer;
    private GUI gui;
    public ShooterAI() {

        initUI();
    }

    private void initUI() {
        gui = new GUI();
        add(new GUI());
        addKeyListener(new TAdapter());
        setSize(WIDTH, HEIGHT);
        setTitle("Shooter AI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTimer();
    }

    /**
     * Starts timer
     */
    private void setTimer() {
        timer = new Timer(DELAY, this);
        timer.start();
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ShooterAI ex = new ShooterAI();
            ex.setVisible(true);
        });
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            gui.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            gui.keyPressed(e);
        }
    }

    /**
     * Called whenever timer finishes
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        gui.step();
    }
}