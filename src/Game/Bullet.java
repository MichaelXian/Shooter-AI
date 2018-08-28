package Game;


import Utility.Vector;

public class Bullet {
    public static final double DAMAGE = 25;
    public static final double RADIUS = 5;
    public static final double SPEED = 1;
    private Vector position;
    private Vector velocity;

    /**
     * Constructs a new Bullet with given position and velocity
     * @param position
     * @param heading
     */
    public Bullet(Vector position, Vector heading, Double speed) {
        this.position = position;
        this.velocity = heading.scale(SPEED + speed);
    }

    // Getters

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

    // End of getters

    /**
     * Sets the new position with current velocity
     */
    public void next() {
        position = position.add(velocity);
    }
}

