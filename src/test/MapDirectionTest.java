import org.junit.Test;
import project.MapDirection;
import project.Vector2d;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MapDirectionTest {

    MapDirection N = MapDirection.NORTH;
    MapDirection NW = MapDirection.NORTHWEST;
    MapDirection E = MapDirection.EAST;
    MapDirection SE = MapDirection.SOUTHEAST;
    MapDirection S = MapDirection.SOUTH;

    int two = 2;
    int three = 3;
    int four = 4;
    int seven = 7;

    @Test
    public void toUnitVectorTest(){
        Vector2d vector1 = new Vector2d(0, 1);
        Vector2d vector2 = new Vector2d(1, 0);
        Vector2d vector3 = new Vector2d(1,-1);

        assertEquals(N.toUnitVector(), vector1);
        assertEquals(E.toUnitVector(), vector2);
        assertEquals(SE.toUnitVector(), vector3);
        assertNotEquals(S.toUnitVector(), vector1);
        assertNotEquals(N.toUnitVector(), vector3);
        assertNotEquals(NW.toUnitVector(), vector2);
    }

    @Test
    public void nextTest(){
        assertEquals(NW.next(), N);
        assertEquals(SE.next(), S);
        assertEquals(E.next(), SE);
        assertNotEquals(NW.next(), S);
        assertNotEquals(E.next(), NW);
        assertNotEquals(S.next(), SE);
    }

    @Test
    public void rotatebyTest(){
        assertEquals(N.rotateby(three), SE);
        assertEquals(N.rotateby(four), S);
        assertEquals(NW.rotateby(three), E);
        assertNotEquals(S.rotateby(two), N);
        assertNotEquals(E.rotateby(seven), NW);
        assertNotEquals(SE.rotateby(two), E);
    }
}
