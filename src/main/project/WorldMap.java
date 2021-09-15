package project;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class WorldMap implements IPositionChangeObserver{

    private final int MAX_TRIES = 20;

    public final int width;
    public final int height;
    public final Vector2d lowerLeftWorld;
    public final Vector2d upperRightWorld;

    public int jungleWidth;
    public int jungleHeight;
    public final Vector2d lowerLeftJungle;
    public final Vector2d upperRightJungle;

    public Map<Vector2d, Plant> plants = new ConcurrentHashMap<>();
    public List<Animal> animals = new CopyOnWriteArrayList<>();
    public Map<Vector2d, MapCell> occupiedPositions = new ConcurrentHashMap<>();
    public List<Animal> dead = new ArrayList<>();
    private List<Animal> unbornChildren = new ArrayList<>();

    public WorldMap(int width, int height, int jungleWidth, int jungleHeight){
        this.height = height;
        this.width = width;
        this.lowerLeftWorld = new Vector2d(0,0);
        this.upperRightWorld = new Vector2d(width-1, height-1);

        this.jungleWidth = jungleWidth;
        this.jungleHeight = jungleHeight;
        this.lowerLeftJungle = new Vector2d((width-jungleWidth+1)/2, (height-jungleHeight+1)/2);
        this.upperRightJungle = new Vector2d(lowerLeftJungle.x+jungleWidth-1, lowerLeftJungle.y+jungleHeight-1);
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public Object objectAt(Vector2d position){

        if(occupiedPositions.get(position) != null) {
            return occupiedPositions.get(position);
        }
        return plants.get(position);
    }

    public void place(Animal animal) {

        animals.add(animal);
        insertAnimal(animal);
        animal.addObserver(this);
    }

    public void placeChildren(){

        for(Animal child : unbornChildren){
            this.place(child);
        }

        unbornChildren.clear();
    }

    public void positionChanged(Vector2d oldPosition, Animal animal){

        occupiedPositions.get(oldPosition).remove(animal);

        if(!occupiedPositions.get(oldPosition).hasAnimals()){
            occupiedPositions.remove(oldPosition);
        }

        insertAnimal(animal);
    }

    private void insertAnimal(Animal animal){

        if(occupiedPositions.get(animal.getPosition()) == null){
            occupiedPositions.put(animal.getPosition(), new MapCell(animal.getPosition(), this, animal));
        }
        else
        {
            occupiedPositions.get(animal.getPosition()).addAnimal(animal);
        }
    }

    public void addConceived(Animal animal){

        unbornChildren.add(animal);
    }

    public void addDead(Animal animal){

        if(animal.isDead())
            dead.add(animal);
    }

    public void removeDead(){

        for(Animal animal : dead){
            removeAnimal(animal);
            animal.removeObserver(this);
        }

        animals.removeAll(dead);
        dead.clear();
    }

    private void removeAnimal(Animal animal){

        occupiedPositions.get(animal.getPosition()).remove(animal);

        if(!occupiedPositions.get(animal.getPosition()).hasAnimals()){
            occupiedPositions.remove(animal.getPosition());
        }
    }

    public void addStepPlant(){

        Random random = new Random();
        int x;
        int y;
        Vector2d plantPosition;
        int tries = 0;

        do {
            if (jungleHeight == height) {
                x = random.nextInt(width - jungleWidth);
                if (x >= lowerLeftJungle.x) {
                    x += jungleWidth;
                }
            }
            else {
                x = random.nextInt(width);
            }

            if (x < lowerLeftJungle.x || x > upperRightJungle.x) {
                y = random.nextInt(height);
            } else {
                y = random.nextInt(height - jungleHeight);
                if (y >= lowerLeftJungle.y) {
                    y += jungleHeight;
                }
            }

            plantPosition = new Vector2d(x,y);

            tries ++;

        } while(isOccupied(plantPosition) && tries < MAX_TRIES);

        if(tries < MAX_TRIES)
            insertPlant(new Plant(plantPosition, this));
    }

    public void addJunglePlant(){

        Vector2d plantPosition = lowerLeftJungle.randomPositionPrecedes(upperRightJungle);
        int tries = 0;

        while(isOccupied(plantPosition) && tries < MAX_TRIES){
            plantPosition = lowerLeftJungle.randomPositionPrecedes(upperRightJungle);
            tries++;
        }

        if(tries < MAX_TRIES)
            insertPlant(new Plant(plantPosition, this));

    }

    private void insertPlant(Plant plant){

        if(! isOccupied(plant.getPosition())){
            plants.put(plant.getPosition(), plant);
        }
    }

    public Vector2d randomFreePosition(Vector2d lowerLeftCorner, Vector2d upperRightCorner){

        Vector2d potentialPosition = lowerLeftCorner.randomPositionPrecedes(upperRightCorner);

        while(isOccupied(potentialPosition)){
            potentialPosition = lowerLeftCorner.randomPositionPrecedes(upperRightCorner);
        }
        return potentialPosition;
    }

    public Vector2d randomFreeNeighbour(Vector2d position){

        Vector2d[] neighbours = position.neighbours(new Vector2d(width, height));
        Random random = new Random();
        int index = random.nextInt(neighbours.length);
        boolean[] checked = new boolean[neighbours.length];
        checked[index] = true;

        while(!allChecked(checked) && !checked[index] && isOccupied(neighbours[index])){
            index = random.nextInt(neighbours.length);
            checked[index] = true;
        }
        return neighbours[index];
    }

    private boolean allChecked(boolean[] checked){
        for(boolean element : checked){
            if(!element){
                return false;
            }
        }
        return true;
    }
}
