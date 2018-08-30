package Utility;

import java.util.Objects;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.sin;
import static java.lang.Math.cos;

public class Vector {
    private final double x;
    private final double y;

    /**
     * Constructs a vector with given x and y co-ordnates
     * @param x
     * @param y
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Add a vector to this one, and return the resultant
     * @param vector the vector to add
     * @return a new vector which is this vector plus the given vector
     */
    public Vector add(Vector vector) {
        return new Vector(x + vector.x(), y + vector.y());
    }

    /**
     * Subtract a vector from this one, and return the resultant
     * @param vector vector to subract
     * @return a new vector which is this vector minus the given vector
     */
    public Vector sub(Vector vector) {
        return new Vector(x - vector.x(), y - vector.y());
    }

    /**
     * Returns length of vector
     * @return length of vector
     */
    public double length() {
        return sqrt(pow(x,2) + pow(y,2));
    }

    /**
     * Returns the distance from this vector and the given vector
     * @param vector vector to measure distance between
     * @return the distance
     */
    public double distance(Vector vector) {
        return this.sub(vector).length();
    }

    /**
     * Returns a normalized vector. If the length was 0, returns original vector
     *
     * @return a new normalized vector
     */
    public Vector normalize() {
        double newX = 0d;
        double newY = 0d;
        if (length() != 0) {
            newX = x / length();
            newY = y / length();
        }
        return new Vector(newX, newY);
    }

    /**
     * Returns a new vector with given length
     * @param factor the length to get the vector to
     * @return a new vector with set length
     */
    public Vector scaleTo(double factor) {
        Vector normalized = this.normalize();
        double newX = normalized.x() * factor;
        double newY = normalized.y() * factor;
        return new Vector(newX, newY);
    }

    /**
     * Return a new vector scaled by the factor
     * @param factor the factor to multiply the length by
     * @return a new vector with it's length multiplied by the factor
     */
    public Vector scaleBy(double factor) {
        double newX = x * factor;
        double newY = y * factor;
        return new Vector(newX, newY);
    }

    /**
     * Returns a rotated vector
     * @param rotation amount to rotate by
     * @return a new vector that's been rotated
     */
    public Vector rotate(double rotation) {
        double newX = cos(-rotation) * x - sin(-rotation) * y;
        double newY = sin(-rotation) * x + cos(-rotation) * y;
        return new Vector(newX, newY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.x, x) == 0 &&
                Double.compare(vector.y, y) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }

    // Getters

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    // End of getters


}
