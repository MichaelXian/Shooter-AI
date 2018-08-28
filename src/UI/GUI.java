
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
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

public class GUI extends JPanel {
    private Game game;
    public GUI() {
        game = new Game(true, new AI(), new AI(), this, new Evolver());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        setupGraphics(graphics);
        drawShips(graphics);
        drawBullets(graphics);
        System.out.println("paint");
    }

    public void step() {
        game.update();
        repaint();
    }

    public void keyReleased(KeyEvent e) {
        game.keyReleased(e);
    }

    public void keyPressed(KeyEvent e) {

        game.keyPressed(e);
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
        //set color
        Paint paint = new Color(0,0,0);
        graphics.setPaint(paint);
    }

    /**
     * Draws all the ships in the game
     */
    private void drawShips(Graphics2D g) {
        for (Ship s : game.getShips()) {
            drawShip(g, s);
        }
    }

    /**
     * Draws all bullets in the game
     * @param g
     */
    private  void drawBullets(Graphics2D g) {
        for (Bullet b : game.getBullets()) {
            drawBullet(g, b);
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