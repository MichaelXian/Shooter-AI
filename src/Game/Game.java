package Game;

import Controllers.AI;
import Controllers.Player;
import Evolution.Evolver;
import UI.GUI;
import UI.ShooterAI;
import Utility.Geometry;
import Utility.Vector;

import java.util.*;

public class Game extends Observable {
    public final int FRACTION = 8; // how far from edges the ships spawn
    public final Vector SHIP_SPAWN_1 = new Vector(ShooterAI.WIDTH/FRACTION, ShooterAI.HEIGHT/2);
    public final Vector SHIP_SPAWN_2 = new Vector(ShooterAI.WIDTH*(FRACTION - 1)/FRACTION, ShooterAI.HEIGHT/2);
    private GUI gui;
    private List<Bullet> bullets;
    private Bullet bullet1;
    private Bullet bullet2;
    private List<Ship> ships;
    private Ship ship1;
    private Ship ship2;
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
        ship1 = ships.get(0);
        ship2 = ships.get(1);
        // for testing
        bullets.add(new Bullet(new Vector(ShooterAI.WIDTH/2, ShooterAI.HEIGHT/2), new Vector(0,1), 0d));
        data = new HashMap<>();
        updateData();
    }

    /**
     * Updates the data
     */
    private void updateData() {
        List<Entity> bulletsList = (List<Entity>)(List<?>) bullets;
        bullet1 = (Bullet) Geometry.closestTo(bulletsList, ship1.getPosition());
        bullet2 = (Bullet) Geometry.closestTo(bulletsList, ship2.getPosition());
        data.put("ship1X", ship1.getPosition().x());
        data.put("ship1Y", ship1.getPosition().y());
        data.put("ship1VelX", ship1.getPosition().x());
        data.put("ship1VelY", ship1.getPosition().y());
        data.put("ship2X", ship2.getPosition().x());
        data.put("ship2Y", ship2.getPosition().y());
        data.put("ship2VelX", ship2.getPosition().x());
        data.put("ship2VelY", ship2.getPosition().y());
        data.put("bul1X", bullet1.getX());
        data.put("bul1Y", bullet1.getY());
        data.put("bul1VelX", bullet1.getVelX());
        data.put("bul1VelY", bullet1.getVelY());
        data.put("bul2X", bullet2.getX());
        data.put("bul2Y", bullet2.getY());
        data.put("bul2VelX", bullet2.getVelX());
        data.put("bul2VelY", bullet2.getVelY());
        data.put("key", 0d);
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

    /**
     * Creates a bullet with given position and heading
     * @param position location of bullet
     * @param heading heading of bullet
     * @param speed speed of ship that fired it
     */
    public void shoot(Vector position, Vector heading, Double speed) {
        bullets.add(new Bullet(position, heading, speed));
    }

    /**
     * Updates the state of the game
     */
    public void update() {
        for (Bullet b: bullets) {
            b.update();
        }
    }

    // Getters

    public List<Ship> getShips() {
        return ships;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }


    // End of Getters


}
