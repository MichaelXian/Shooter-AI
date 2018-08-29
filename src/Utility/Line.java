package Utility;

import java.awt.*;
import java.awt.geom.Path2D;

public class Line {

    /**
     * Creates a line with specified width between two points, as a path
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param width
     * @return
     */
    public static Shape lineToRect(Double x1, Double y1, Double x2, Double y2, Double width) {
        Vector point1 = new Vector(x1, y1);
        Vector point2 = new Vector(x2, y2);
        Double slope = (y2 - y1) / (x2 - x1);
        Double perpendicularSlope = -1/slope;
        Vector displacementFromPoint = new Vector(1, perpendicularSlope);
        displacementFromPoint = displacementFromPoint.scaleTo(width/2);
        Vector path1 = point1.add(displacementFromPoint);
        Vector path2 = point1.sub(displacementFromPoint);
        Vector path3 = point2.sub(displacementFromPoint);
        Vector path4 = point2.add(displacementFromPoint);
        Path2D path = new Path2D.Double();
        path.moveTo(path1.x(), path1.y());
        path.lineTo(path2.x(), path2.y());
        path.lineTo(path3.x(), path3.y());
        path.lineTo(path4.x(), path4.y());
        path.closePath();
        return path;
    }

}
