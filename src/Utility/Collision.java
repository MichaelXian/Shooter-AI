package Utility;

import java.awt.*;
import java.awt.geom.Area;

public class Collision {

    /**
     * Checks if the two shapes intersect
     * @param shapeA
     * @param shapeB
     * @return
     */
    public static boolean collided(Shape shapeA, Shape shapeB) {
        Area areaA = new Area(shapeA);
        areaA.intersect(new Area(shapeB));
        return !areaA.isEmpty();
    }

}
