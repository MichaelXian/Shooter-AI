package Utility;

import java.util.Objects;

public class Vector {
    private final double x;
    private final double y;

    Vector(double x, double y) {
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
