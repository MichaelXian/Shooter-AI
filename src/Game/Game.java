package Game;

import Controllers.AI;
import Controllers.Player;
import UI.ShooterAI;
import Utility.*;
import Utility.Vector;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class Game extends Observable {
    public final static String DRAW = "No Winner";
    public final static String FIRST_WIN = "Ship 1 Won";
    public final static String SECOND_WIN = "Ship 2 Won";
    public final static int WIDTH = 750;
    public final static int HEIGHT = ShooterAI.HEIGHT;
    private final int DELAY = 10;
    private final int FRACTION = 8; // how far from edges the ships spawn
    private final Vector SHIP_SPAWN_1 = new Vector(WIDTH/FRACTION, HEIGHT/2);
    private final Vector SHIP_SPAWN_2 = new Vector(WIDTH*(FRACTION - 1)/FRACTION, HEIGHT/2);
    private final int MAX_TICKS = 100*60;
    private ShooterAI shooterAI;
    private List<Bullet> bullets1;
    private List<Bullet> bullets2;
    private List<Ship> ships;
    private Ship ship1;
    private Ship ship2;
    private Map<String, Double> data;
    private String winner;
    private Integer ticks;
    private Double endTime;
    private boolean gameEnd;
    private boolean firstEnd;


    /**
     * Creates a game with 2 ships duking it out
     * @param player whether the first ship is player controlled or not
     * @param ai1 the ai controlling the first ship
     * @param ai2 the ai controlling the second ship
     * @param shooterAI the shooterAI
     */
    public Game(Boolean player, AI ai1, AI ai2, ShooterAI shooterAI) {
        firstEnd = false;
        endTime = new Double(MAX_TICKS);
        this.ticks = 0;
        this.shooterAI = shooterAI;
        bullets1 = new ArrayList<>();
        bullets2 = new ArrayList<>();
        ships = new ArrayList<>();
        spawnShips(player, ai1, ai2);
        ship1 = ships.get(0);
        ship2 = ships.get(1);
        initialBullets();
        data = new HashMap<>();
        updateData();
        addObservers();
    }

    // Start of initialization methods

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
     * Spawns the ships
     * @param player whether the first ship is player controlled or not
     */
    private void spawnShips(Boolean player, AI ai1, AI ai2) {
        // create first ship, and choose controller based on player or ai
        if (player) {
            ships.add(new Ship(SHIP_SPAWN_1, new Player(), this, true));
        } else {
            ships.add(new Ship(SHIP_SPAWN_1, ai1, this, true));
        }
        ships.add(new Ship(SHIP_SPAWN_2, ai2, this, false));
    }

    // End of initialization methods

    /**
     * Makes the surviving ship the winner
     */
    private void killShip(Ship ship) {
        if (ship == ship1) {
            winner = SECOND_WIN;
        } else {
            winner = FIRST_WIN;
        }
    }

    /**
     * Makes the game a draw, where neither ship wins
     */
    private void drawGame() {
        winner = DRAW;
    }



    /**
     * Updates the data
     */
    private void updateData() {
        Double winnerDouble;
        if (winner != null) {
            winnerDouble = 1d;
        } else {
            winnerDouble = 0d;
        }
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
     * Creates a bullet with given position and heading
     * @param position location of bullet
     * @param heading heading of bullet
     * @param speed speed of ship that fired it
     * @param owner the ship that fired it
     */
    public void shoot(Vector position, Vector heading, Double speed, Ship owner) {
        Bullet bullet = new Bullet(position, heading, speed);
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
        gameEndTimer();
        if (new Double(ticks) - endTime > DELAY) {
            gameEnd = true;
        }
        ticks ++;
        updateData();
        setChanged();
        notifyObservers(data); //updates ships, if game is not over
        if (ticks >= MAX_TICKS) {
            drawGame();
        } else if (winner == null) {
            checkCollisions();
            checkBoundary();
            for (Bullet b : bullets1) {
                b.update();
            }
            for (Bullet b : bullets2) {
                b.update();
            }
        }
    }

    /**
     * Counts down from gameOver to endTime, so the winner text displays
     */
    private void gameEndTimer() {
        if (firstEnd) {
            firstEnd = false;
            endTime = new Double(ticks);
        }
    }

    /**
     * Checks if the centre of a ship is within the boundary
     */
    private void checkBoundary() {
        for (Ship ship: ships) {
            Double x = ship.getPosition().x();
            Double y = ship.getPosition().y() + Ship.HEIGHT/2;
            if (x < 0 || x > WIDTH || y < 0 || y > HEIGHT) {
                killShip(ship);
            }
        }
    }

    /**
     * Checks for collisions of ships and enemy bullets
     */
    private void checkCollisions() {
        // check for ship1
        Shape shipShape = Triangle.toPath(ship1.getPosition(), Ship.WIDTH, Ship.HEIGHT, ship1.getRotation());
        for (Bullet bullet: bullets2) {
            Shape bulletShape = Circle.toCircle(bullet.getPosition(), Bullet.RADIUS);
            if (Collision.collided(shipShape, bulletShape)) {
                killShip(ship1);
            }
        }
        // check for ship2
        shipShape = Triangle.toPath(ship2.getPosition(), Ship.WIDTH, Ship.HEIGHT, ship2.getRotation());
        for (Bullet bullet: bullets1) {
            Shape bulletShape = Circle.toCircle(bullet.getPosition(), Bullet.RADIUS);
            if (Collision.collided(shipShape, bulletShape)) {
                killShip(ship2);
            }
        }
    }


    public void keyReleased(KeyEvent e) {
        ship1.getController().keyReleased(e);
    }

    public void keyPressed(KeyEvent e) {
        ship1.getController().keyPressed(e);
    }


    // Getters

    public Map<String, Double> getData() {
        return data;
    }

    public boolean getGameEnd() {
        return gameEnd;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public List<Bullet> getBullets1() {
        return bullets1;
    }

    public List<Bullet> getBullets2() {
        return bullets2;
    }

    public String getWinner() {
        return winner;
    }

    // End of Getters


}
