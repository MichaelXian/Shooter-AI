package UI;

import javax.swing.*;
import java.awt.*;

public class ShooterAI extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 750;
    public ShooterAI() {

        initUI();
    }

    private void initUI() {

        add(new GUI());

        setSize(WIDTH, HEIGHT);

        setTitle("Shooter AI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }



    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ShooterAI ex = new ShooterAI();
            ex.setVisible(true);
        });
    }
}