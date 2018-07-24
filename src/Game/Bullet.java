package Game;


import Utility.Vector;

public class Bullet {
    private double radius;
    private Vector position;
    private Vector velocity;

    public Bullet(Vector position, Vector velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public double getRadius() {
        return radius;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public double getX() {
        return position.x();
    }

    public double getY() {
        return position.y();
    }

    public double getVelX() {
        return velocity.x();
    }

    public double getVelY() {
        return velocity.y();
    }

    public void next() {
        position = position.add(velocity);
    }
}

