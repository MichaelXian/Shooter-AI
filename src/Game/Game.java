package Game;

import Controllers.AI;
import Controllers.Player;
import Evolution.Evolver;
import UI.GUI;
import UI.ShooterAI;
import Utility.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class Game extends Observable {
    public final int FRACTION = 8; // how far from edges the ships spawn
    public final Vector SHIP_SPAWN_1 = new Vector(ShooterAI.WIDTH/FRACTION, ShooterAI.HEIGHT/2);
    public final Vector SHIP_SPAWN_2 = new Vector(ShooterAI.WIDTH*(FRACTION - 1)/FRACTION, ShooterAI.HEIGHT/2);
    private GUI gui;
    private List<Bullet> bullets;
    private List<Ship> ships;
    private Evolver evolver;
    private Map<String, Double> data;


    /**
     * Creates a game with 2 ships duking it out
     * @param player whether the first ship is player controlled or not
     * @param ai1 the ai controlling the first ship
     * @param ai2 the ai controlling the second ship
     * @param gui the gui
     */
    public Game(Boolean player, AI ai1, AI ai2, GUI gui, Evolver evolver) {
        this.evolver = evolver;
        this.gui = gui;
        bullets = new ArrayList<>();
        ships = new ArrayList<>();
        spawnShips(player, ai1, ai2);
        // for testing
        bullets.add(new Bullet(new Vector(ShooterAI.WIDTH/2, ShooterAI.HEIGHT/2), new Vector(0,1), 0d));
    }

    /**
     * Spawns the ships
     * @param player whether the first ship is player controlled or not
     */
    private void spawnShips(Boolean player, AI ai1, AI ai2) {
        // create first ship, and choose controller based on player or ai
        if (player) {
            ships.add(new Ship(SHIP_SPAWN_1, new Player(), this));
        } else {
            ships.add(new Ship(SHIP_SPAWN_1, ai1, this));
        }
        ships.add(new Ship(SHIP_SPAWN_2, ai2, this));
    }

    // Getters

    public List<Ship> getShips() {
        return ships;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }


    // End of Getters

    /**
     * Creates a bullet with given position and heading
     * @param position location of bullet
     * @param heading heading of bullet
     * @param speed speed of ship that fired it
     */
    public void shoot(Vector position, Vector heading, Double speed) {
        bullets.add(new Bullet(position, heading, speed));
    }
}
