import org.junit.Test;
import project.Vector2d;

import static org.junit.Assert.*;

public class Vector2dTest {

    Vector2d v1 = new Vector2d(1, 10);
    Vector2d v2 = new Vector2d(1,10);
    Vector2d v3 = new Vector2d(2, 20);
    Vector2d v4 = new Vector2d(-1, -10);
    Vector2d v5 = new Vector2d(0,0);

    @Test
    public void equalsTest(){

        assertTrue(v1.equals(v2));
        assertTrue(v2.equals(v1));
        assertFalse(v3.equals(v4));
        assertFalse(v4.equals(v2));
    }

    @Test
    public void toStringTest(){

        assertEquals(v1.toString(), "(1,10)");
        assertEquals(v2.toString(), "(1,10)");
        assertEquals(v3.toString(), "(2,20)");
        assertEquals(v4.toString(), "(-1,-10)");
        assertNotEquals(v1.toString(), "(-1, 10)");
    }

    @Test
    public void addTest(){
        assertEquals(v1.add(v2), v3);
        assertEquals(v2.add(v1), v3);
        assertEquals(v1.add(v4), v5);
        assertEquals(v3.add(v5), v3);
        assertEquals(v2.add(v5), v1);
        assertNotEquals(v3.add(v2), v1);
        assertNotEquals(v1.add(v2), v2);
        assertNotEquals(v4.add(v5), v3);
    }

    @Test
    public void precedesTest(){

        assertTrue(v1.precedes(v2));
        assertTrue(v1.precedes(v3));
        assertTrue(v4.precedes(v3));
        assertTrue(v4.precedes(v1));
        assertFalse(v3.precedes(v2));
        assertFalse(v3.precedes(v4));
        assertFalse(v2.precedes(v4));
    }

    @Test
    public void followsTest(){

        assertTrue(v1.follows(v2));
        assertTrue(v2.follows(v4));
        assertTrue(v3.follows(v4));
        assertTrue(v3.follows(v1));
        assertFalse(v1.follows(v3));
        assertFalse(v1.follows(v3));
        assertFalse(v4.follows(v1));
    }

    @Test
    public void modTest(){

        assertEquals(v1.mod(v2), v5);
        assertEquals(v4.mod(v2.add(v3)), v3);
        assertNotEquals(v3.mod(v2), v1);
        assertNotEquals(v5.mod(v4), v3);
    }

    @Test
    public void isIncludedTest(){
        assertTrue(v5.isIncluded(v4, v1));
        assertFalse(v1.isIncluded(v4, v5));
        assertTrue(v1.isIncluded(v2, v3));
        assertFalse(v3.isIncluded(v5, v1));
    }

    @Test
    public void randomPositionPrecedesTest(){

        assertTrue(v4.randomPositionPrecedes(v5).isIncluded(v4, v5));
        assertTrue(v2.randomPositionPrecedes(v3).isIncluded(v2, v3));
        assertTrue(v4.randomPositionPrecedes(v1).isIncluded(v4, v1));
    }

    /*@Test
    public void neighboursTest(){

        project.Vector2d[] neighboursv1 = {new project.Vector2d(1, 11), new project.Vector2d(2, 11), new project.Vector2d(2, 10),
                new project.Vector2d(2, 9), new project.Vector2d(1, 9), new project.Vector2d(0, 9), new project.Vector2d(0, 10),
                new project.Vector2d(0, 11)};
        project.Vector2d[] neighboursv5 = {new project.Vector2d(0, 1), new project.Vector2d(1, 1), new project.Vector2d(1, 0),
                new project.Vector2d(1, 9), new project.Vector2d(0, 9), new project.Vector2d(1, 9), new project.Vector2d(1, 0),
                new project.Vector2d(1, 1)};

        assertArrayEquals(neighboursv1, v1.neighbours(v3));
        assertArrayEquals(neighboursv5, v5.neighbours(v2));
    }*/
}
