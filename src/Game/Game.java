package Game;

import Controllers.AI;
import Controllers.Player;
import Evolution.Evolver;
import UI.GameDrawer;
import UI.ShooterAI;
import Utility.Geometry;
import Utility.Vector;

import java.awt.event.KeyEvent;
import java.util.*;

public class Game extends Observable {
    public final int FRACTION = 8; // how far from edges the ships spawn
    public final int WIDTH = 750;
    public final int HEIGHT = ShooterAI.HEIGHT;
    public final Vector SHIP_SPAWN_1 = new Vector(WIDTH/FRACTION, HEIGHT/2);
    public final Vector SHIP_SPAWN_2 = new Vector(WIDTH*(FRACTION - 1)/FRACTION, HEIGHT/2);
    private GameDrawer gameDrawer;
    private List<Bullet> bullets;
    private List<Bullet> bullets1;
    private List<Bullet> bullets2;
    private List<Ship> ships;
    private Ship ship1;
    private Ship ship2;
    private Evolver evolver;
    private Map<String, Double> data;
    private String winner;


    /**
     * Creates a game with 2 ships duking it out
     * @param player whether the first ship is player controlled or not
     * @param ai1 the ai controlling the first ship
     * @param ai2 the ai controlling the second ship
     * @param gameDrawer the gameDrawer
     */
    public Game(Boolean player, AI ai1, AI ai2, GameDrawer gameDrawer, Evolver evolver) {
        this.winner = "No Winner";
        this.evolver = evolver;
        this.gameDrawer = gameDrawer;
        bullets = new ArrayList<>();
        bullets1 = new ArrayList<>();
        bullets2 = new ArrayList<>();
        ships = new ArrayList<>();
        spawnShips(player, ai1, ai2);
        ship1 = ships.get(0);
        ship2 = ships.get(1);
        // so that nearest bullet is never null
        initialBullets();
        //bullets.add(new Bullet(new Vector(-10, -10), new Vector(0,0), 0d, ship1));
        data = new HashMap<>();
        updateData();
        addObservers();
    }


    /**
     * Makes the surviving ship the winner
     */
    private void killShip(Ship ship) {
        if (ship == ship1) {
            this.winner = ship1.getName() + " won";
        } else {
            this.winner = ship2.getName() + " won";
        }
    }


    /**
     * Creates first bullets so that there is always a nearest bullet
     */
    private void initialBullets() {
        Vector pos = new Vector(-10,-10);
        Vector vel = new Vector(0,0);
        shoot(pos, vel, 0d, ship1);
        shoot(pos, vel, 0d, ship2);
    }

    /**
     * Adds ships as observers
     */
    private void addObservers() {
        addObserver(ship1);
        addObserver(ship2);
    }

    /**
     * Updates the data
     */
    private void updateData() {
        List<Entity> bulletsList1 = (List<Entity>)(List<?>) bullets1;
        List<Entity> bulletsList2 = (List<Entity>)(List<?>) bullets2;
        Bullet bullet1 = (Bullet) Geometry.closestTo(bulletsList2, ship1.getPosition());
        Bullet bullet2 = (Bullet) Geometry.closestTo(bulletsList1, ship2.getPosition());
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
     * @param owner the ship that fired it
     */
    public void shoot(Vector position, Vector heading, Double speed, Ship owner) {
        Bullet bullet = new Bullet(position, heading, speed);
        bullets.add(bullet);
        if (owner == ship1) {
            bullets1.add(bullet);
        } else {
            bullets2.add(bullet);
        }
    }

    /**
     * Updates the state of the game
     */
    public void update() {
        updateData();
        setChanged();
        notifyObservers(data); //updates ships
        for (Bullet b: bullets) {
            b.update();
        }
    }

    public void keyReleased(KeyEvent e) {
        ship1.getController().keyReleased(e);
    }

    public void keyPressed(KeyEvent e) {
        ship1.getController().keyPressed(e);
    }


    // Getters

    public List<Ship> getShips() {
        return ships;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public String getWinner() {
        return winner;
    }

    // End of Getters


}
