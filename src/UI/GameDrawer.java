
package UI;

import Controllers.AI;
import Evolution.Evolver;
import Game.Bullet;
import Game.Game;
import Game.Ship;
import Utility.Circle;
import Utility.Triangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class GameDrawer extends JPanel implements ActionListener {
    private static final int DELAY = 1000/30;
    public static final int HEIGHT = ShooterAI.HEIGHT;
    public static final int WIDTH = 750;
    private Boolean first = true;
    private Game game;
    private Timer timer;
    public GameDrawer() {
        game = new Game(true, new AI(), new AI(), this, new Evolver());
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
        drawBullets(graphics);
        drawDivider(graphics);
        drawWinner(graphics);
        // Load the font
        if (first) {
            first = false;
            graphics.drawString("", 0, 0);
        }
    }


    /**
     * Draws which ship won
     * @param graphics
     */
    private void drawWinner(Graphics2D graphics) {
        String string = game.getWinner();

        if (string != null) {
            graphics.drawString(string, WIDTH/6, HEIGHT/2);
        }
    }



    /**
     * Called whenever timer finishes
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        game.update();
        repaint();
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
        // Set the font
        Font font = new Font("SansSerif", Font.PLAIN, 100);
        graphics.setFont(font);
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


}