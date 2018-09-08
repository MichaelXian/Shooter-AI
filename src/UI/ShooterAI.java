package UI;

import Controllers.AI;
import Evolution.Evolver;
import Game.Game;
import org.neuroph.core.NeuralNetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class ShooterAI implements Observer, ActionListener {
    public static final int WIDTH = 1365;
    public static final int HEIGHT = 750;
    private static final int DELAY = 1000/200;
    public static final String GENERATION_FILE_PATH = "Generation/generation.txt";
    private Evolver evolver;
    private List<NeuralNetwork> matchup;
    private Game game;
    private File generationFile;
    private Timer timer;
    private int generation;


    /**
     * Starts timer
     */
    private void setTimer() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public ShooterAI() {
        generationFile = new File(GENERATION_FILE_PATH);
        this.evolver = new Evolver(this);
        matchup = evolver.next(game, null);
        game = new Game(false,
                new AI(matchup.get(0), true),
                new AI(matchup.get(1), false),
                this);
    }


    public void start() {
        setGeneration();
        setTimer();
    }



    /**
     * Sets the generation to the one found in the file storing the generation
     */
    private void setGeneration() {
        try {
            Scanner scanner = new Scanner(generationFile);
            generation = scanner.nextInt();
        } catch (FileNotFoundException e) {
            throw new NullPointerException("File not found");
        }
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ShooterAI ex = new ShooterAI();
            ex.start();
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
            if (evolver.isEvolved()) {
                generation ++;
                saveGeneration(generation);
            }
        }
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

    @Override
    public void actionPerformed(ActionEvent e) {
        game.update();
    }
}