package Test;

import Game.Bullet;
import Utility.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BulletTest {


    public BulletTest() {

    }

    @Test
    public void testNext() {
        Vector position = new Vector(0,0);
        Vector velocity = new Vector(1,1);
        Bullet bullet = new Bullet(position, velocity);
        bullet.next();
        assertEquals(position.add(velocity), bullet.getPosition());
        bullet.next();
        assertEquals(position.add(velocity).add(velocity), bullet.getPosition());
    }


}
