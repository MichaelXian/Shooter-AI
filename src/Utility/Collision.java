package Utility;

import java.awt.*;
import java.awt.geom.Area;

public class Collision {

    public static boolean collided(Shape shapeA, Shape shapeB) {
        Area areaA = new Area(shapeA);
        areaA.intersect(new Area(shapeB));
        return !areaA.isEmpty();
    }

}
