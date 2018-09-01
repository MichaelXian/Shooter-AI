
package UI;

import Controllers.NeuralNetwork.Visualization.Graphic;
import Controllers.NeuralNetwork.Visualization.NeuralNetworkVisual;
import Game.Bullet;
import Game.Game;
import Game.Ship;
import Utility.Circle;
import Utility.Triangle;
import Utility.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class GameDrawer extends JPanel implements ActionListener {
    private static final int DELAY = 1000/200;
    public static final int HEIGHT = ShooterAI.HEIGHT;
    public static final int WIDTH = 750;
    private ShooterAI shooterAI;
    private final int LABEL_FONT = 10;
    private final int GENERATION_FONT = 30;
    private final int GAME_OVER_FONT = 100;
    private final Double LABEL_DISTANCE = 80/3d;
    private int generation = 1;
    private boolean first = true;
    private boolean highlight = false;



    private Game game;
    private Timer timer;
    public GameDrawer(Game game, ShooterAI shooterAI) {
        this.shooterAI = shooterAI;
        this.game = game;
        setFocusable(true);
        addKeyListener(new TAdapter());
        setTimer();
    }

    /**
     * Starts timer
     */
    private void setTimer() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        setupGraphics(graphics);
        drawShips(graphics);
        drawNeuralNetworks(graphics);
        drawBullets(graphics);
        drawDivider(graphics);
        drawWinner(graphics);
        drawGeneration(graphics);
        graphics.setPaint(Color.BLACK);
        // Load the font
        if (first) {
            first = false;
            graphics.drawString("", 0, 0);
        }
    }

    /**
     * Adds 1 to the generation
     */
    public void incrementGeneration() {
        generation++;
        System.out.println("Generation " + generation);
    }




    /**
     * Called whenever timer finishes
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        game.update();
        if (shooterAI.isWatching() && highlight) {
            repaint();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            game.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            game.keyPressed(e);
        }
    }

    /**
     * sets up anti-aliasing/color
     */
    private void setupGraphics(Graphics2D graphics) {
        // Do anti-aliasing and stuff
        RenderingHints rh
                = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        graphics.setRenderingHints(rh);
    }


    // Drawers

    /**
     * Draws the neural network graphics
     * @param graphics
     */
    private void drawNeuralNetworks(Graphics2D graphics) {
        for (Ship ship: game.getShips()) {
            NeuralNetworkVisual neuralNetworkVisual = ship.getController().getNeuralNetworkVisual();
            if (neuralNetworkVisual != null) {
                List<Graphic> graphicsList = neuralNetworkVisual.getGraphics();
                for (Graphic graphic: graphicsList) {
                    drawGraphic(graphics, graphic);
                }
                Font font = new Font("SansSerif", Font.PLAIN, LABEL_FONT);
                graphics.setFont(font);
                graphics.setPaint(Color.BLACK);
                labelInputs(graphics, neuralNetworkVisual);
                labelOutputs(graphics, neuralNetworkVisual);
            }
        }
    }

    /**
     * Labels inputs of network
     * @param graphics
     * @param neuralNetworkVisual the network
     */
    private void labelInputs(Graphics2D graphics, NeuralNetworkVisual neuralNetworkVisual) {
        List<Vector> inputPositions = neuralNetworkVisual.getInputPositions();
        List<String> labels = new ArrayList<>();
        labels.add("x position");
        labels.add("y position");
        labels.add("angle");
        labels.add("x distance");
        labels.add("y distance");
        labels.add("x bullet");
        labels.add("y bullet");
        for (int i = 0; i < inputPositions.size(); i++) {
            Vector position = inputPositions.get(i);
            graphics.drawString(labels.get(i),
                    new Float(position.x() - 3 * LABEL_DISTANCE),
                    new Float(position.y() + LABEL_FONT/2));
        }
    }

    /**
     * Labels outputs of network
     * @param graphics
     * @param neuralNetworkVisual the network
     */
    private void labelOutputs(Graphics2D graphics, NeuralNetworkVisual neuralNetworkVisual) {
        List<Vector> outputPositions = neuralNetworkVisual.getOutputPositions();
        List<String> labels = new ArrayList<>();
        labels.add("forward");
        labels.add("back");
        labels.add("left");
        labels.add("right");
        labels.add("shoot");
        for (int i = 0; i < outputPositions.size(); i++) {
            Vector position = outputPositions.get(i);
            graphics.drawString(labels.get(i),
                    new Float(position.x() + LABEL_DISTANCE),
                    new Float(position.y() + LABEL_FONT/2));
        }
    }

    /**
     * Draws the given graphic
     * @param graphic
     */
    private void drawGraphic(Graphics2D graphics, Graphic graphic) {
        graphics.setPaint(Color.BLACK);
        graphics.draw(graphic.getShape());
        graphics.setPaint(graphic.getColor());
        graphics.fill(graphic.getShape());
    }


    /**
     * Draws which ship won
     * @param graphics
     */
    private void drawWinner(Graphics2D graphics) {
        String string = game.getWinner();
        if (string != null) {
            Font font = new Font("SansSerif", Font.PLAIN, GAME_OVER_FONT);
            graphics.setFont(font);
            graphics.drawString(string, WIDTH/6, HEIGHT/2);
        }
    }

    /**
     * Draws which generation it is
     * @param graphics
     */
    private void drawGeneration(Graphics2D graphics) {
        Font font = new Font("SansSerif", Font.PLAIN, GENERATION_FONT);
        graphics.setFont(font);
        graphics.drawString("GEN " + generation,
                new Float(WIDTH/2 - GENERATION_FONT * (2 + Math.log(generation + 0.01))/2),
                HEIGHT/10);
    }

    /**
     * Draws divider between game and neural net visualization
     * @param graphics
     */
    private void drawDivider(Graphics2D graphics) {
        Paint paint = new Color(0,0,0);
        graphics.setPaint(paint);
        Rectangle2D divider = new Rectangle2D.Double(WIDTH, 0, 1, HEIGHT);
        graphics.fill(divider);
    }


    /**
     * Draws all the ships in the game
     * @param graphics
     */
    private void drawShips(Graphics2D graphics) {
        graphics.setPaint(Color.RED);
        Ship ship1 = game.getShips().get(0);
        drawShip(graphics, ship1);
        graphics.setPaint(Color.BLUE);
        Ship ship2 = game.getShips().get(1);
        drawShip(graphics, ship2);
    }

    /**
     * Draws all bullets in the game
     * @param graphics
     */
    private  void drawBullets(Graphics2D graphics) {
        graphics.setPaint(Color.RED);
        for (Bullet b : game.getBullets1()) {
            drawBullet(graphics, b);
        }
        graphics.setPaint(Color.BLUE);
        for (Bullet b : game.getBullets2()) {
            drawBullet(graphics, b);
        }
    }


    /**
     * Draws the given ship on the screen
     * @param graphics
     * @param ship
     */
    private void drawShip (Graphics2D graphics, Ship ship) {
        Path2D path = Triangle.toPath(ship.getPosition(), Ship.WIDTH, Ship.HEIGHT, ship.getRotation());
        graphics.fill(path);
    }


    /**
     * Draws the given bullet
     * @param graphics
     * @param bullet
     */

    private void drawBullet(Graphics2D graphics, Bullet bullet) {
        Ellipse2D circle = Circle.toCircle(bullet.getPosition(), bullet.RADIUS);
        graphics.fill(circle);
    }


    // End of drawers


    public void setGame(Game game) {
        this.game = game;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(Integer generation) {
        this.generation = generation;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

}