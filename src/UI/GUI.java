
package UI;

import Controllers.AI;
import Game.Game;
import Game.Ship;
import Utility.Triangle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class GUI extends JPanel {
    private Game game;
    public GUI() {
        game = new Game(true, new AI(), new AI(), this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawTriangle(g);
    }

    private void drawTriangle (Graphics g) {
        Graphics2D graphics = (Graphics2D) g;

        RenderingHints rh
                = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        graphics.setRenderingHints(rh);

        Ship ship = game.getShips().get(0);
        Path2D path = Triangle.toPath(ship.getPosition(), Ship.WIDTH, Ship.HEIGHT, ship.getRotation());
        Paint paint = new Color(0,0,0);
        graphics.setPaint(paint);
        graphics.fill(path);
    }

}