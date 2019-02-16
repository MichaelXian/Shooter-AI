package Utility.Test;

import Utility.Vector;
import org.junit.jupiter.api.Test;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.sqrt;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorTest {
    Vector v0 = new Vector(1,2);
    Vector v1 = new Vector(3,4);
    Vector v2 = new Vector(5,6);
    Vector v3;
    Vector v4;


    @Test
    public void testAdd() {
        v3 = v1.add(v2);
        v4 = new Vector(v1.x() + v2.x(), v1.y() + v2.y());
        assertEquals(v3, v4);
    }

    @Test
    public void testSub() {
        v3 = v1.sub(v2);
        v4 = new Vector(v1.x() - v2.x(), v1.y() - v2.y());
        assertEquals(v3, v4);
    }

    @Test
    public void testNormalize() {
        v3 = v0.normalize();
        double x = v0.x()/(sqrt(pow(v0.x(),2) + pow(v0.y(),2)));
        double y = v0.y()/(sqrt(pow(v0.x(),2) + pow(v0.y(),2)));
        v4 = new Vector( x, y);
        assertEquals(v3, v4);
    }

    @Test
    public void testRotate() {
        v3 = v0.rotate(1);
        double x = cos(-1) * v0.x() - sin(-1) * v0.y();
        double y = sin(-1) * v0.x() + cos(-1) * v0.y();
        v4 = new Vector(x,y);
        assertEquals(v3, v4);
    }

    @Test
    public void testScale() {
        v3 = v0.scaleTo(100);
        double x = v0.normalize().x() * 100;
        double y = v0.normalize().y() * 100;
        v4 = new Vector(x,y);
        assertEquals(v3, v4);
    }

    @Test
    public void testScaleBy() {
        v3 = v0.scaleBy(100);
        double x = v0.x() * 100;
        double y = v0.y() * 100;
        v4 = new Vector(x,y);
        assertEquals(v3, v4);
    }

    @Test
    public void testLength() {
        double x = v0.x();
        double y = v0.y();
        assertEquals(v0.length(), sqrt(pow(x,2)+pow(y,2)));
    }

}
