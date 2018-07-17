package Game;

import Utility.Vector;

public class Ship {
    private final double TURNSPEED 0.01;
    private Vector position;
    private double rotation;
    private double health;

    public double getRotation() {
        return rotation;
    }

    public Vector getPosition() {
        return position;
    }

    public void turnRight() {
        rotation += TURNSPEED;
    }

    public void turnLeft() {
        rotation -= TURNSPEED;
    }

}
