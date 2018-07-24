package Game;

import Utility.Vector;

public class Ship {
    private final double TURNSPEED = 0.01;
    private final double MAX_HEALTH = 100;
    private final double ACCELERATION = 1;
    private final double HEIGHT = 10;
    private final double WIDTH = 10;
    private Vector position;
    private Vector heading;
    private double rotation;
    private double health;

    Ship(Vector position) {
        this.position = position;
        heading = new Vector(0,1);
    }

    public double getHEIGHT() {
        return HEIGHT;
    }

    public double getWIDTH() {
        return WIDTH;
    }

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

    public void turnRight() {
        rotation += TURNSPEED;
        heading = heading.rotate(-TURNSPEED);
    }

    public void turnLeft() {
        rotation -= TURNSPEED;
        heading = heading.rotate(TURNSPEED);
    }

}
