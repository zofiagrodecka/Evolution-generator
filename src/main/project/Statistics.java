package project;

import project.visualisation.StatisticsWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Statistics {

    private final WorldMap map;
    public int frequency;

    private double animalsNumber;
    private double plantsNumber = 0;
    private Genes dominantGenotype;
    private List<Animal> dominant = new ArrayList<>();
    private double averageEnergyLevel;
    private double averageLifeLength = 0;
    private double averageChildrenNumber = 0;
    private final StatisticsWriter statisticsWriter = new StatisticsWriter();
    private String fileName;

    public double kidsNumber = 0;
    private Map<Genes, Integer> genotypesNumber = new LinkedHashMap<>();
    private List<Animal> dead = new ArrayList<>();

    private Map<Genes, Integer> dominantGenotypes = new LinkedHashMap<>();
    private Genes averageGenotype;

    private int callsNumber = 0;
    private double averageAnimalsNumber = 0;
    private double averagePlantsNumber = 0;
    private double averageGeneralEnergyLevel = 0;
    private double averageGeneralLifeLength = 0;
    private double averageGeneralChildrenNumber = 0;

    private double averageAnimalsNumberSum = 0;
    private double averagePlantsNumberSum = 0;
    private double averageGeneralEnergyLevelSum = 0;
    private double averageGeneralLifeLengthSum = 0;
    private double averageGeneralChildrenNumberSum = 0;


    public Statistics(WorldMap map, int initialAnimalsNumber, int startEnergy, int frequency, String fileName){

        this.map = map;
        this.animalsNumber = initialAnimalsNumber;
        findDominantAnimals();
        this.averageEnergyLevel = startEnergy;
        this.frequency = frequency;
        this.fileName = fileName;
    }

    public void actualize(){

        callsNumber ++;

        dominant.clear();

        animalsNumber = map.animals.size();
        plantsNumber = map.plants.size();
        findDominantAnimals();
        averageEnergyLevel = countAverageEnergy();
        averageLifeLength = countLifeLength();
        averageChildrenNumber = countAverageChildrenNumber();

        averageAnimalsNumberSum += animalsNumber;
        averagePlantsNumberSum += plantsNumber;
        averageGeneralEnergyLevelSum += averageEnergyLevel;
        averageGeneralChildrenNumberSum += averageChildrenNumber;

        if(dead.size() > 0) {
            averageGeneralLifeLengthSum += averageLifeLength;
        }

        countAverageGeneralStatistics();
        countDominantGenotypes();
        averageGenotype = findAverageDominantGenotype();
    }

    public List<Animal> getDominant() {
        return dominant;
    }

    public String toString(){
        return
                "number of animals:  " + animalsNumber+ "\n" +
                        "number of plants: " + plantsNumber+ "\n" +
                        "dominant genotype: " + dominantGenotype + " " +"\n" +
                        "average energy level: " + averageEnergyLevel+ "\n" +
                        "average animals' life length: " + averageLifeLength +"\n" +
                        "average children number: " + averageChildrenNumber;
    }

    public String toHtmlString(){
        return "<html>" + toString().replaceAll("<","&lt;").
                replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>";
    }

    public String fileData(){
        return "average number of animals: " + averageAnimalsNumber + "\n" +
                "average number of plants: " + averagePlantsNumber+ "\n" +
                "average dominant genotype: " + averageGenotype + " " +"\n" +
                "average energy level: " + averageGeneralEnergyLevel+ "\n" +
                "average animals' life length: " + averageGeneralLifeLength +"\n" +
                "average children number: " + averageGeneralChildrenNumber + "\n";
    }

    public void writeToFile() throws IOException {

        statisticsWriter.writeStatistics(fileName, fileData());
    }

    public void passDead(List<Animal> deadMapAnimals){
        dead.addAll(deadMapAnimals);
    }

    private void findDominantAnimals(){

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

        if(map.animals.size() == 0){
            return 0;
        }

        for(Animal animal : map.animals){
            energySum += animal.getEnergyLevel();
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

    private void countAverageGeneralStatistics(){
        averageAnimalsNumber = averageAnimalsNumberSum/callsNumber;
        averagePlantsNumber = averagePlantsNumberSum/callsNumber;
        averageGeneralEnergyLevel = averageGeneralEnergyLevelSum/callsNumber;
        averageGeneralLifeLength = averageGeneralLifeLengthSum/callsNumber;
        averageGeneralChildrenNumber = averageGeneralChildrenNumberSum/callsNumber;
    }

    private void countDominantGenotypes(){

        if(dominantGenotypes.get(dominantGenotype) == null){
            dominantGenotypes.put(dominantGenotype, dominant.size());
        }
        else{
            dominantGenotypes.replace(dominantGenotype, dominantGenotypes.get(dominantGenotype) + 1);
        }
    }

    private Genes findAverageDominantGenotype(){

        int maxValue = 0;
        int currentValue;
        Genes average = null;

        for (Genes genotype:dominantGenotypes.keySet()) {

            currentValue = dominantGenotypes.get(genotype);
            if(currentValue > maxValue){
                maxValue = currentValue;
                average = genotype;
            }
        }
        return average;
    }
}
