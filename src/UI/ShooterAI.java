package UI;

import Controllers.AI;
import Evolution.Evolver;
import Evolution.GamePlayer;
import Game.Game;
import Utility.FileManager;
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
    private int killNum = 0;

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
        FileManager.getGeneration();
        while (true) {
            // Check safe quit (so we don't quit during reading/writing files)
            if (FileManager.getSafeQuit() != 0) {
                break;
            }
            gameNum++;
            if (game.isKilled()) {
                killNum ++;
            }
            //int totalMatchups = evolver.getNumMatchups();
            //System.out.print("Matchup " + gameNum + "/" + totalMatchups + ": ");
            matchup = evolver.next(game, matchup);
            game = new Game(false,
                    new AI(matchup.get(0), true),
                    new AI(matchup.get(1), false)
            );
            if (evolver.isEvolved()) {
                System.out.println("Number of kills: " +  killNum);
                System.out.println("Percentage kills: " + killNum * 100.0 / gameNum * 1.0 + "%");
                killNum = 0;
                gameNum = 0;
                FileManager.incrementGeneration();
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