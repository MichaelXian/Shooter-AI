package Utility;

import java.awt.geom.Path2D;

public class Triangle {


    /**
     * Turns the parameters into a path representing a triangle
     * @param position  the centre of the triangle
     * @param width
     * @param height
     * @param rotation  in radians, CCW
     * @return
     */
    public static Path2D toPath(Vector position, Double width, Double height, Double rotation) {
        Vector top;
        Vector left;
        Vector right;
        Path2D path;
        // Create triangle centred at 0,0
        top = new Vector(0, height/2);
        left = new Vector(-width/2, -height/2);
        right = new Vector(width/2, -height/2);
        // Apply Rotation
        top = top.rotate(rotation);
        left = left.rotate(rotation);
        right = right.rotate(rotation);
        // Translate
        top = top.add(position);
        left = left.add(position);
        right = right.add(position);
        // Turn points into path
        path = new Path2D.Double();
        path.moveTo(top.x(), top.y());
        path.lineTo(left.x(), left.y());
        path.lineTo(right.x(), right.y());
        path.closePath();
        return path;
    }

}
