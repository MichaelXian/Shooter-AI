package Game;

import Controllers.Controller;
import Utility.Vector;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Ship implements Observer {
    public static final double HEIGHT = 50;
    public static final double WIDTH = 25;
    public static final double MAX_HEALTH = 100;
    // public static final double MASS = 1;
    private static final double ACCELERATION = 1;
    private static final double TURNSPEED = 0.01;
    private Vector position;
    private Vector velocity;
    private Vector heading;
    private double rotation;
    private double health;
    private Controller controller;
    private Game game;

    Ship(Vector position, Controller controller, Game game) {
        rotation = 0;
        this.position = position;
        this.controller = controller;
        this.game = game;
        heading = new Vector(0,1);
        health = MAX_HEALTH;
        velocity = new Vector(0,0);
    }

    // Getters

    public double getHealth() {
        return health;
    }

    public double getRotation() {
        return rotation;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getHeading() {
        return heading;
    }

    // End of getters

    /**
     * Turns the ship clockwise
     */
    public void turnRight() {
        rotation += TURNSPEED;
        heading = heading.rotate(TURNSPEED);
    }

    /**
     * Turns the ship counter-clockwise
     */
    public void turnLeft() {
        rotation -= TURNSPEED;
        heading = heading.rotate(-TURNSPEED);
    }

    /**
     * accelerates the ship towards it's heading
     */
    public void accelerate() {
        Vector impulse = heading.scale(ACCELERATION);
        velocity.add(impulse);

    }

    /**
     * accelerates the ship in reverse relative to it's heading
     */
    public void decelerate() {
        Vector impulse = heading.scale(ACCELERATION);
        velocity.sub(impulse);
    }


    @Override
    public void update(Observable observable, Object object) {
        ArrayList<Double> data = (ArrayList<Double>) object;
        ArrayList<Boolean>result = controller.update(data);
        if (result.get(0)) {
            accelerate();
        }
        if (result.get(1)) {
            decelerate();
        }
        if (result.get(2)) {
            turnLeft();
        }
        if (result.get(3)) {
            turnRight();
        }
        if (result.get(4)) {
            this.game.shoot(position, heading);
        }
    }
}
