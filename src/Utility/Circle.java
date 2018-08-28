package Utility;

import java.awt.geom.Ellipse2D;

public class Circle {

    /**
     * Creates a circle with given coordinates and radius
     * @param position centre of circle
     * @param radius
     * @return
     */
    public static Ellipse2D toCircle(Vector position, Double radius) {
        Double x = position.x();
        Double y = position.y();
        Double leftX = x - radius;
        Double topY = y - radius;
        Double diameter = 2 * radius;
        return new Ellipse2D.Double(leftX, topY, diameter, diameter);
    }

}
