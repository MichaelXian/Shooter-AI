package Game;

import Controllers.Controller;
import Utility.DataToDouble;
import Utility.Vector;

import java.util.*;

public class Ship implements Observer, Entity {
    public static final double HEIGHT = 50;
    public static final double WIDTH = 25;
    public static final double MAX_HEALTH = 100;
    // public static final double MASS = 1;
    private static final double ACCELERATION = 0.3;
    private static final double TURNSPEED = 0.45;
    private static final double SPEED = 15;
    private static final int DELAY = 5;
    private static final double FULL_ROTATION = 2*Math.PI;
    private Vector position;
    private Vector velocity;
    private Vector heading;
    private double rotation;
    private double health;
    private Controller controller;
    private Game game;
    private int ticksSinceLastFired;
    private String name;

    private boolean first;

    /**
     * Constructs a new ship with given parameters
     * @param position
     * @param controller what's controlling the ship's actions
     * @param game the game this is in
     * @param first whether this is the first ship or not
     */
    Ship(Vector position, Controller controller, Game game, boolean first) {
        rotation = 0;
        this.position = position;
        this.controller = controller;
        this.game = game;
        this.name = controller.getName();
        this.first = first;
        heading = new Vector(0,1);
        health = MAX_HEALTH;
        velocity = new Vector(0,0);
        ticksSinceLastFired = 0;
    }


    /**
     * Turns the ship clockwise
     */
    public void turnRight() {
        rotation -= TURNSPEED;
        rotation = (rotation + FULL_ROTATION) % FULL_ROTATION;
        heading = heading.rotate(-TURNSPEED);
    }

    /**
     * Turns the ship counter-clockwise
     */
    public void turnLeft() {
        rotation += TURNSPEED;
        rotation = (rotation + FULL_ROTATION) % FULL_ROTATION;
        heading = heading.rotate(TURNSPEED);
    }

    /**
     * Moves forward by SPEED
     */
    private void moveBackward() {
        velocity = heading.scaleTo(SPEED);
        move();
    }

    /**
     * Moves backward by SPEED
     */
    private void moveForward() {
        velocity = heading.scaleTo(SPEED);
        move();
    }


    /**
     * accelerates the ship towards it's heading
     */
    public void accelerate() {
        Vector impulse = heading.scaleTo(ACCELERATION);
        velocity = velocity.add(impulse);
    }

    /**
     * accelerates the ship in reverse relative to it's heading
     */
    public void decelerate() {
        Vector impulse = heading.scaleTo(ACCELERATION);
        velocity = velocity.sub(impulse);
    }

    /**
     * moves the ship according to it's velocity
     */
    private void move() {
        position = position.add(velocity);
        velocity = new Vector(0,0);
    }


    /**
     * Asks controller what to do, then does it
     * @param observable
     * @param object
     */
    @Override
    public void update(Observable observable, Object object) {
        ticksSinceLastFired ++;
        Map<String, Double> data = ((Game) observable).getData();
        if (((Game) observable).getWinner() == null) {
            List<Double> neuronInput = DataToDouble.toDouble(data, this);
            ArrayList<Boolean> result = controller.update(neuronInput);
            if (result.get(0)) {
                //accelerate();
                moveForward();
            }
            if (result.get(1)) {
                moveBackward();
            }
            if (result.get(2)) {
                turnLeft();
            }
            if (result.get(3)) {
                turnRight();
            }
            if (result.get(4)) {
                shoot();
            }
            move();
        }
    }


    /**
     * Fires a bullet, if enough time has passed
     */
    private void shoot() {
        if (ticksSinceLastFired > DELAY) {
            ticksSinceLastFired = 0;
            game.shoot(position, heading, this.velocity.length(), this);
        }
    }



    // Getters

    public double getHealth() {
        return health;
    }

    public double getRotation() {
        return rotation;
    }
    @Override
    public Vector getPosition() {
        return position;
    }

    @Override
    public Vector getVelocity() {
        return velocity;
    }

    public Vector getHeading() {
        return heading;
    }

    public Controller getController() {
        return controller;
    }

    public String getName() {
        return name;
    }

    public boolean isFirst() {
        return first;
    }

    // End of getters

}
