package project.visualisation;

import project.Animal;
import project.Simulation;

public class AnimalHistory {

    private final Animal animal;
    private final int erasNumber;
    private final Simulation simulation;
    public final int beginningEra;
    private int kidsNumber = 0;
    private int descendantsNumber = 0;
    private int deathEra = 0;

    public AnimalHistory(Animal animal, int n, int beginningEra, Simulation simulation){
        this.animal = animal;
        this.erasNumber = n;
        this.beginningEra = beginningEra;
        this.simulation = simulation;
    }

    public void countHistory(){

        if (animal.isDead()) {
            deathEra = simulation.era;
        }

        kidsNumber = animal.children.size();
        descendantsNumber = animal.descendantsNumber();
    }

    public String toString(){

        if(deathEra <= 0) {
            return "number of children: " + kidsNumber + "\n" +
                    "number of descendants: " + descendantsNumber + "\n" +
                    "this animal didn't die during this time.";
        }

        return "number of children: " + kidsNumber + "\n" +
                "number of descendants: " +  descendantsNumber + "\n" +
                "animal died in the era: " + deathEra;
    }

    public int endEra() {
        return beginningEra + erasNumber;
    }
}
