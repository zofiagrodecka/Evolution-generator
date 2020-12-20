package project;

import project.visualisation.StatisticsWriter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Statistics {

    private final WorldMap map;
    public int frequency;

    private int animalsNumber;
    private int plantsNumber = 0;
    private Genes dominantGenotype;
    private List<Animal> dominant = new ArrayList<>();
    private double averageEnergyLevel;
    private double averageLifeLength = 1;
    private double averageKidsNumber = 0;
    private final StatisticsWriter statisticsWriter = new StatisticsWriter();

    public double kidsNumber = 0;
    private Map<Genes, Integer> genotypesNumber = new LinkedHashMap<>();
    private List<Animal> dead = new ArrayList<>();


    public Statistics(WorldMap map, int initialAnimalsNumber, int startEnergy, int frequency){
        this.map = map;
        this.animalsNumber = initialAnimalsNumber;
        findDominantGenotypes();
        this.averageEnergyLevel = startEnergy;
        this.frequency = frequency;
    }

    public void actualize(){
        dominant.clear();

        animalsNumber = map.animals.size();
        plantsNumber = map.plants.size();
        findDominantGenotypes();
        averageEnergyLevel = countAverageEnergy();
        averageLifeLength = countLifeLength();
        averageKidsNumber = countAverageChildrenNumber();
        statisticsWriter.writeStatistics("statistics.txt", toString());
    }

    public List<Animal> getDominant() {
        return dominant;
    }

    public String toString(){
        return
                "liczba zwierząt:  " + animalsNumber+ "\n" +
                        "liczba roślin: " + plantsNumber+ "\n" +
                        "dominujący genotyp: " + dominantGenotype + " " +"\n" +
                        "średni poziom energii: " + averageEnergyLevel+ "\n" +
                        "średnia długość życia zwierząt: " + averageLifeLength +"\n" +
                        "średnia liczba dzieci: " + averageKidsNumber + "\n";
    }

    public String toHtmlString(){
        return "<html>" + toString().replaceAll("<","&lt;").
                replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>";
    }

    public void passDead(List<Animal> deadMapAnimals){
        dead.addAll(deadMapAnimals);
    }

    private void findDominantGenotypes(){

        int dominantAnimalsNumber = 0;
        int prevValue = 0;

        for(Animal animal : map.animals){
            if(genotypesNumber.get(animal.getGenes()) == null){
                genotypesNumber.put(animal.getGenes(), 1);
            }
            else{
                prevValue = genotypesNumber.get(animal.getGenes());
                genotypesNumber.replace(animal.getGenes(), prevValue + 1);
            }

            if(prevValue + 1 > dominantAnimalsNumber){
                dominantAnimalsNumber = prevValue + 1;
                dominantGenotype = animal.getGenes();
            }
        }
        for(Animal animal : map.animals){
            if(animal.getGenes().equals(dominantGenotype)){
                dominant.add(animal);
            }
        }

        genotypesNumber.clear();
    }

    private double countAverageEnergy(){

        double energySum = 0;

        for(Animal animal : map.animals){
            energySum += animal.getEnergyLevel();
        }

        if(map.animals.size() == 0){
            return 0;
        }

        return energySum/map.animals.size();
    }

    private double countLifeLength(){

        double sumAge = 0;
        for(Animal corpse : dead){
            sumAge += corpse.age;
        }

        return sumAge/dead.size();
    }

    private double countAverageChildrenNumber(){

        if(map.animals.size() == 0){
            return 0;
        }
        return kidsNumber/map.animals.size();
    }
}
