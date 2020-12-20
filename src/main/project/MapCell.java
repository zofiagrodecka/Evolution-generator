package project;

import java.util.*;

public class MapCell {

    public Vector2d coordinates;
    private final Comparator<Animal> animalComparatorDesc = new AnimalComparatorDesc();
    private final List<Animal> animals = new ArrayList<>();
    private final WorldMap map;

    public MapCell(Vector2d cell, WorldMap map, Animal animal){
        this.coordinates = cell;
        this.map = map;
        animals.add(animal);
    }

    public void addAnimal(Animal animal){
        animals.add(animal);
    }

    public void remove(Animal animal){

        animals.remove(animal);
    }

    public int animalsNumber(){
       return animals.size();
    }

    public boolean hasAnimals(){
        return animals.size() > 0;
    }

    public ArrayList<Animal> theStrongestAnimals(){

        animals.sort(animalComparatorDesc);
        int theHighestEnergy = animals.get(0).getEnergyLevel();
        ArrayList<Animal> theStrongest = new ArrayList<>();

        for(Animal animal : animals) {
            if (animal.getEnergyLevel() == theHighestEnergy) {
                theStrongest.add(animal);
            } else {
                break;
            }
        }
        return theStrongest;
    }

    public Animal[] firstTwo(){
       animals.sort(animalComparatorDesc);
       Animal[] twoStrongest = new Animal[animals.size()];
       twoStrongest = animals.toArray(twoStrongest);
       return Arrays.copyOfRange(twoStrongest, 0, 3);
    }

    public Animal first(){
        animals.sort(animalComparatorDesc);
        return animals.get(0);
    }
}
