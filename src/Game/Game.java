package Game;

import Controllers.AI;
import Controllers.Player;
import UI.GUI;
import UI.ShooterAI;
import Utility.Vector;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public final int FRACTION = 8;
    public final Vector SHIP_SPAWN_1 = new Vector(ShooterAI.WIDTH/FRACTION, ShooterAI.HEIGHT/2);
    public final Vector SHIP_SPAWN_2 = new Vector(ShooterAI.WIDTH*(FRACTION - 1)/FRACTION, ShooterAI.HEIGHT/2);
    private GUI gui;
    private List<Bullet> bullets;
    private List<Ship> ships;

    public Game(Boolean player, AI aI1, AI aI2, GUI gui) {
        this.gui = gui;
        bullets = new ArrayList<>();
        ships = new ArrayList<>();
        if (player) {
            ships.add(new Ship(SHIP_SPAWN_1, new Player(), this));
        } else {
            ships.add(new Ship(SHIP_SPAWN_1, aI1, this));
        }
        ships.add(new Ship(SHIP_SPAWN_2, aI2, this));
    }

    // Getters

    public List<Ship> getShips() {
        return ships;
    }


    // End of Getters

    public void shoot(Vector position, Vector heading) {
        bullets.add(new Bullet(position, heading));
    }
}
