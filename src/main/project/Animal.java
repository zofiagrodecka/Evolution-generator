package project;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Animal {

    private Vector2d position;
    private int energyLevel;
    public final int startEnergy;
    private MapDirection direction = MapDirection.NORTH;
    private final Genes genes;
    private final WorldMap map;
    public int age = 0;
    public final List<Animal> children = new ArrayList<>();

    private List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(int startEnergy, WorldMap map){
        this.position = map.randomFreePosition(map.lowerLeftWorld, map.upperRightWorld);
        this.startEnergy = startEnergy;
        this.energyLevel = startEnergy;
        this.map = map;
        this.genes = new Genes();
    }

    public Animal(Vector2d position, int startEnergy, int energyLevel, Genes genes, WorldMap map){
        this.position = position;
        this.startEnergy = startEnergy;
        this.energyLevel = energyLevel;
        this.genes = genes;
        this.map = map;
    }

    public Color toColor(){

        if (energyLevel <= 0) return new Color(0, 0, 0);
        else if (energyLevel <= 0.2 * startEnergy) return new Color(128, 128, 128);
        else if (energyLevel <= 0.4 * startEnergy && energyLevel > 0.2 * startEnergy) return new Color(192, 192, 192);
        else if (energyLevel <= 0.6 * startEnergy && energyLevel > 0.4 * startEnergy) return new Color(248, 248, 248);
        else if (energyLevel <= 0.8 * startEnergy && energyLevel > 0.6 * startEnergy) return new Color(239, 150, 155);
        else if (energyLevel <= startEnergy && energyLevel > 0.8 * startEnergy) return new Color(248, 82, 98);
        else return new Color(255, 0, 23);
    }

    public Vector2d getPosition(){
        return position;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public Genes getGenes() {
        return genes;
    }

    public void loseEnergy(int lostEnergyUnits) {
        this.energyLevel -= lostEnergyUnits;
    }

    public void gainEnergy(int addedEnergyUnits){
        this.energyLevel += addedEnergyUnits;
    }

    public String toString(){

        return switch (direction) {
            case NORTH -> "N "+energyLevel;
            case SOUTH -> "S "+energyLevel;
            case EAST -> "E "+energyLevel;
            case WEST -> "W "+energyLevel;
            case NORTHEAST -> "NE "+energyLevel;
            case NORTHWEST -> "NW "+energyLevel;
            case SOUTHEAST -> "SE "+energyLevel;
            case SOUTHWEST -> "SW "+energyLevel;
        };

    }

    public boolean isDead(){
        return energyLevel <= 0;
    }

    public boolean readyForKids(){
        return energyLevel >= startEnergy/2;
    }

    public int randomDirection(){

        int randomNumber = genes.randomGene(0, genes.GENES_NUMBER);
        return genes.getGene(randomNumber);
    }

    public void move(int moveEnergy, int newDirection){

        Vector2d oldPosition = getPosition();

       direction = direction.rotateby(newDirection);

        position = position.add(direction.toUnitVector()).mod(new Vector2d(map.width, map.height));
        this.loseEnergy(moveEnergy);

        age++;

        notify(oldPosition);
    }

    public void addChild(Animal child){
        children.add(child);
    }

    public int descendantsNumber(){

        int result = children.size();

        for(Animal child : children){
            result += child.descendantsNumber();
        }
        return result;
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    public void notify(Vector2d oldPosition){

        for(IPositionChangeObserver observer:observers) {
            observer.positionChanged(oldPosition, this);
        }
    }
}
