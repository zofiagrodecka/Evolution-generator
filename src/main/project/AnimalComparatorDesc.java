package project;

import java.util.Comparator;

public class AnimalComparatorDesc implements Comparator<Animal> {

    @Override
    public int compare(Animal animal1, Animal animal2) {

        if(animal1.getEnergyLevel() < animal2.getEnergyLevel()){
            return 1;
        }
        else if(animal1.getEnergyLevel() > animal2.getEnergyLevel()){
            return -1;
        }
        return 0;
    }
}
