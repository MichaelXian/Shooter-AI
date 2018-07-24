package Utility;

import java.util.Objects;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.sin;
import static java.lang.Math.cos;

public class Vector {
    private final double x;
    private final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public Vector add(Vector vector) {
        return new Vector(x + vector.x(), y + vector.y());
    }

    public Vector sub(Vector vector) {
        return new Vector(x - vector.x(), y - vector.y());
    }

    public Vector normalize() {
        double newX = x / (sqrt(pow(x,2) + pow(y,2)));
        double newY = y / (sqrt(pow(x,2) + pow(y,2)));
        return new Vector(newX, newY);
    }

    public Vector scale(double factor) {
        Vector normalized = this.normalize();
        double newX = normalized.x() * factor;
        double newY = normalized.y() * factor;
        return new Vector(newX, newY);
    }

    public Vector rotate(double rotation) {
        double newX = cos(rotation) * x - sin(rotation) * y;
        double newY = sin(rotation) * x + cos(rotation) * y;
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
}
