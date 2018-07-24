package Test;

import Utility.Vector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VectorTest {
    Vector v1 = new Vector(0,1);
    Vector v2 = new Vector(1,0);

    public VectorTest() {

    }


    @Test
    public void testAdd() {
        Vector v3 = v1.add(v2);
        Vector v4 = new Vector(v1.x() + v2.x(), v1.y() + v2.y());
        assertEquals(v3, v4);
    }

    @Test
    public void testSub() {
        Vector v3 = v1.add(v2);
        Vector v4 = new Vector(v1.x() - v2.x(), v1.y() - v2.y());
        assertEquals(v3, v4);
    }


}
