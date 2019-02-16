package UI;

import Controllers.AI;
import Evolution.Evolver;
import Evolution.GamePlayer;
import Game.Game;
import Utility.Generation;
import org.neuroph.core.NeuralNetwork;

import java.awt.*;
import java.util.List;

public class ShooterAI {
    public static final int WIDTH = 1365;
    public static final int HEIGHT = 750;
    private Evolver evolver;
    private List<NeuralNetwork> matchup;
    private GameDrawer gameDrawer;
    private Game game;
    private int gameNum = 0;


    public ShooterAI() {
        // Create a new evolver
        this.evolver = new Evolver(this);
        // Get initial matchup
        matchup = evolver.next(game, null);
        // Get initial game
        game = new Game(false,
                new AI(matchup.get(0), true),
                new AI(matchup.get(1), false)
        );
    }


    public void start() {
        while (true) {
            gameNum++;
            //int totalMatchups = evolver.getNumMatchups();
            //System.out.print("Matchup " + gameNum + "/" + totalMatchups + ": ");
            matchup = evolver.next(game, matchup);
            game = new Game(false,
                    new AI(matchup.get(0), true),
                    new AI(matchup.get(1), false)
            );
            if (evolver.isEvolved()) {
                gameNum = 0;
                Generation.incrementGeneration();
            }
            GamePlayer.playGame(game);
        }
    }




    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ShooterAI ex = new ShooterAI();
            ex.start();
        });
    }



}