package UI;

import Controllers.AI;
import Evolution.Evolver;
import Game.Game;
import org.neuroph.core.NeuralNetwork;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ShooterAI extends JFrame implements Observer{
    public static final int WIDTH = 1365;
    public static final int HEIGHT = 750;
    private Evolver evolver;
    private List<NeuralNetwork> matchup;
    private GameDrawer gameDrawer;
    private Game game;
    public ShooterAI() {
        this.evolver = new Evolver();
        matchup = new ArrayList<>();
        matchup = evolver.next(game, null);
        game = new Game(false,
                new AI(matchup.get(0), true),
                new AI(matchup.get(1), false),
                this);
        initUI();

    }

    private void initUI() {
        //game = new Game(false, new AI(NeuralNetwork.createFromFile("NeuralNets/net1.nnet"), true),
        //        new AI(NeuralNetwork.createFromFile("NeuralNets/net2.nnet"), false), this);
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
        if (game.isGameEnd()) {
            matchup = evolver.next(game, matchup);
            game = new Game(false,
                    new AI(matchup.get(0), true),
                    new AI(matchup.get(1), false),
                    this);
            gameDrawer.setGame(game);
            if (evolver.isEvolved()) {
                gameDrawer.incrementGeneration();
            }
        }

    }
}