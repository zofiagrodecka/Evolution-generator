import org.junit.Test;
import project.Animal;
import project.Genes;
import project.Vector2d;
import project.WorldMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class AnimalTest {

    Vector2d position = new Vector2d(3,3);
    int[] g = {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7};
    Genes genes = new Genes(g);
    WorldMap map = new WorldMap(10, 6, 5, 2);
    Animal animal = new Animal(position, 10, 10, genes, map);
    Animal animal2 = new Animal(0, map);
    Animal animal3 = new Animal(new Vector2d(0, 4), 10, 10, genes, map);
    Animal animal4 = new Animal(new Vector2d(3, 0), 10, 10, genes, map);
    Animal animal5 = new Animal(new Vector2d(2,5), 10, 10, genes, map);
    Animal animal6 = new Animal(new Vector2d(9,3), 10, 10, genes, map);
    Animal animal7 = new Animal(new Vector2d(0,0), 10, 10, genes, map);

    @Test
    public void isDeadTest(){
        assertTrue(animal2.isDead());
        assertFalse(animal.isDead());
    }

    @Test
    public void readyForKidsTest(){
        assertTrue(animal.readyForKids());
        animal.loseEnergy(2);
        assertTrue(animal.readyForKids());
        animal.loseEnergy(3);
        assertTrue(animal.readyForKids());
        animal.loseEnergy(1);
        assertFalse(animal.readyForKids());
        animal.loseEnergy(7);
        assertFalse(animal.readyForKids());
        animal.gainEnergy(13);
    }

    @Test
    public void randomDirectionTest(){

        List<Integer> possibleResult = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        int result = animal.randomDirection();
        assertTrue(possibleResult.contains(result));
    }

    @Test
    public void moveTest(){

        animal.move(2, 1);
        assertEquals(new Vector2d(4,4), animal.getPosition());

        animal3.move(2, 6);
        assertEquals(new Vector2d(9,4), animal3.getPosition());

        animal4.move(2, 4);
        assertEquals(new Vector2d(3,5), animal4.getPosition());

        animal5.move(2, 0);
        assertEquals(new Vector2d(2,0), animal5.getPosition());

        animal6.move(2, 2);
        assertEquals(new Vector2d(0,3), animal6.getPosition());

        animal7.move(2, 5);
        assertEquals(new Vector2d(9,5), animal7.getPosition());
    }
}
