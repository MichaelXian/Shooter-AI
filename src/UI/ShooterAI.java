package UI;

import Controllers.AI;
import Evolution.Evolver;
import Game.Game;
import org.neuroph.core.NeuralNetwork;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class ShooterAI extends JFrame implements Observer{
    public static final int WIDTH = 1365;
    public static final int HEIGHT = 750;
    public static String GENERATION_FILE_PATH = "Generation/generation.txt";
    private boolean watching;
    private boolean watchAll;
    private Evolver evolver;
    private List<NeuralNetwork> matchup;
    private GameDrawer gameDrawer;
    private Game game;
    private File generationFile;


    public ShooterAI(String watching, String watchAll) {
        this.watching = true;
        if (watching == "false") {
            this.watching = false;
        }
        this.watchAll = true;
        if (watchAll == "false") {
            this.watchAll = false;
        }

        generationFile = new File(GENERATION_FILE_PATH);
        //AI ai1 = new AI(NeuralNetwork.createFromFile("NeuralNets/net0.nnet"), true);
        //AI ai2 = new AI(NeuralNetwork.createFromFile("NeuralNets/net0.nnet"), false);
        this.evolver = new Evolver(this);
        matchup = evolver.next(game, null);
        /*game = new Game(false,
                ai1,
                ai2,
                this);
        */
        game = new Game(false,
                new AI(matchup.get(0), true),
                new AI(matchup.get(1), false),
                this);
        initUI();

    }

    public boolean isWatching() {
        return watching;
    }

    public boolean isWatchAll() {
        return watchAll;
    }

    private void initUI() {
        gameDrawer = new GameDrawer(game, this);

        setGeneration();
        add(gameDrawer);
        setSize(WIDTH, HEIGHT);
        setTitle("Shooter AI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Sets the generation of GameDrawer to the one found in the file storing the generation
     */
    private void setGeneration() {
        try {
            Scanner scanner = new Scanner(generationFile);
            gameDrawer.setGeneration(scanner.nextInt());
        } catch (FileNotFoundException e) {
            throw new NullPointerException("File not found");
        }
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            String arg1 = null;
            String arg2 = null;
            if (args.length == 2) {
                arg1 = args[0];
                arg2 = args[1];
            }
            ShooterAI ex = new ShooterAI(arg1, arg2);
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
                saveGeneration(gameDrawer.getGeneration());
            }
        }
    }

    public void setHighlight(boolean highlight) {
        gameDrawer.setHighlight(highlight);
    }

    private void saveGeneration(int generation) {
        try {
            PrintWriter writer = new PrintWriter(generationFile);
            writer.print(generation);
            writer.close();
        } catch (FileNotFoundException e) {
            throw new NullPointerException("File not found");
        }
    }


}