package UI;

import javax.swing.*;
import java.awt.*;

public class ShooterAI extends JFrame{
    public static final int WIDTH = 2000;
    public static final int HEIGHT = 750;
    private static final int DELAY = 10;
    private Timer timer;
    private GameDrawer gameDrawer;
    public ShooterAI() {

        initUI();
    }

    private void initUI() {
        gameDrawer = new GameDrawer();
        add(new GameDrawer());
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