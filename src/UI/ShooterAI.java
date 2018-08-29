package UI;

import Controllers.AI;
import Game.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ShooterAI extends JFrame implements Observer{
    public static final int WIDTH = 2000;
    public static final int HEIGHT = 750;
    private GameDrawer gameDrawer;
    private Game game;
    public ShooterAI() {

        initUI();
    }

    private void initUI() {
        game = new Game(true, new AI(), new AI(), this);
        gameDrawer = new GameDrawer(game);
        add(gameDrawer);
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


    @Override
    public void update(Observable o, Object arg) {

    }
}