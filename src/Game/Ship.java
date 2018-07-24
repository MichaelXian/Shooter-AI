package Game;

import Utility.Vector;

public class Ship {
    public static final double HEIGHT = 10;
    public static final double WIDTH = 10;
    public static final double MAX_HEALTH = 100;
    private static final double ACCELERATION = 1;
    private static final double TURNSPEED = 0.01;
    private Vector position;
    private Vector heading;
    private double rotation;
    private double health;

    Ship(Vector position) {
        this.position = position;
        heading = new Vector(0,1);
        health = MAX_HEALTH;
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
        heading = heading.rotate(-TURNSPEED);
    }

    /**
     * Turns the ship counter-clockwise
     */
    public void turnLeft() {
        rotation -= TURNSPEED;
        heading = heading.rotate(TURNSPEED);
    }

}
