package project;

import project.visualisation.*;

import java.util.ArrayList;
import java.util.LinkedList;

public class Simulation extends Thread{

    private final int EACH_PLANT_PER_DAY = 1;
    private final int DISPLAY_TIME = 200;

    // tu mozna zmienic co ile epok maja sie aktualizowac statystyki ogolne mapy
    // dodalam rowniez okno w zakomentowanym fragmencie w konstruktorze tej klasy, gdzie mozna wpisac to przed uruchomeniem programu
    private int statisticsFrequency = 1;

    private final WorldMap map;
    private final int plantEnergy;
    private final int moveEnergy;
    private final Statistics statistics;
    private AnimalHistory animalHistory;

    private boolean paused = false;
    private boolean animalClicked = false;
    private boolean endSimulation = false;

    public int era = 0;

    private final SimulationPanel panel;
    private final Visualisation visualisation;
    private final StatisticsPanel statisticsPanel;
    public final StatisticsPanel animalInfoPanel = new StatisticsPanel(650, 80,"");

    public Simulation(WorldMap map, int initialAnimalsNumber, int startEnergy, int plantEnergy, int moveEnergy, int windowX, int windowY) {

        this.map = map;
        this.plantEnergy = plantEnergy;
        this.moveEnergy = moveEnergy;
        this.panel = new SimulationPanel(map, this);

        Animal animal;

        for (int i = 0; i < initialAnimalsNumber; i++) {
            animal = new Animal(startEnergy, map);
            map.place(animal);
        }

        // dialog do ustawienia statisticsFrequency

        /*InputDialog dialogWindow = new InputDialog();
        String frequency = dialogWindow.showWindow("After how many eras would you like to see the statistics?");
        statisticsFrequency = Integer.parseInt(frequency);*/

        this.statistics = new Statistics(map, initialAnimalsNumber, startEnergy, statisticsFrequency);
        this.statisticsPanel = new StatisticsPanel(650, 150, statistics.toHtmlString());
        this.visualisation = new Visualisation(panel, this, statisticsPanel, animalInfoPanel);
        visualisation.prepareFrame(windowX, windowY);
    }


    public void run(){

        panel.repaint();

        while(! endSimulation) {

            if(map.animals.size() == 0){
                endSimulation = true;
            }

            if( ! paused) {
                era++;

                map.removeDead();

                move();

                eat();
                reproduce();

                for(int i = 0; i<EACH_PLANT_PER_DAY; i++) {
                    map.addJunglePlant();
                    map.addStepPlant();
                }

                panel.repaint();

                statistics.passDead(map.dead);

                if (era % statistics.frequency == 0) {

                    statistics.actualize();
                    statisticsPanel.updateText(statistics.toHtmlString());
                }

                if(animalClicked && era <= animalHistory.beginningEra + era ){
                    animalHistory.countHistory();
                    if(era == animalHistory.endEra()){
                        endCountingHistory();
                    }
                }

                try {
                    Thread.sleep(DISPLAY_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            else{
                while(paused){
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    public void pauseSimulation(){
        paused = true;
    }

    public void resumeSimulation(){
        paused = false;
    }

    public void setShowDominant(boolean state){
        panel.showDominant = state;
        panel.repaint();
    }

    public void initAnimalHistory(AnimalHistory history){
        animalClicked = true;
        this.animalHistory = history;
    }

    public void endCountingHistory(){
        animalClicked = false;
        AnimalHistoryDialog historyWindow = new AnimalHistoryDialog();
        historyWindow.showWindow(animalHistory.toString());

        for(Animal animal : map.animals){
            animal.children.clear();
        }
    }

    public void end(){
        endSimulation = true;
    }


    public Statistics getStatistics() {
        return statistics;
    }

    private void move(){
        for (Animal animal : map.animals) {
            animal.move(moveEnergy, animal.randomDirection());
            map.addDead(animal);
        }
    }


    private void eat(){

        LinkedList<Plant> toBeRemoved = new LinkedList<>();

        for(Plant plant : map.plants.values()){
            MapCell cell = map.occupiedPositions.get(plant.getPosition());
            if(cell != null){
                ArrayList<Animal> eatingAnimals = cell.theStrongestAnimals();
                int energyUnit = plantEnergy / eatingAnimals.size();
                for (Animal animal : eatingAnimals) {
                    if (!animal.isDead()) {
                        animal.gainEnergy(energyUnit);
                    }
                }
                toBeRemoved.add(plant);
            }
        }

        for (Plant eaten : toBeRemoved) {
            map.plants.remove(eaten.getPosition());
        }

        toBeRemoved.clear();

    }

    private void reproduce(){

        for (MapCell cell : map.occupiedPositions.values()) {
            if (cell.animalsNumber() >= 2) {

                Animal[] parents = cell.firstTwo();
                Animal father = parents[0];
                Animal mother = parents[1];

                if (father.readyForKids() && mother.readyForKids()) {

                    int fatherEnergy = father.getEnergyLevel() / 4;
                    int motherEnergy = mother.getEnergyLevel() / 4;
                    father.loseEnergy(fatherEnergy);
                    mother.loseEnergy(motherEnergy);

                    statistics.kidsNumber++;

                    map.addDead(father);
                    map.addDead(mother);

                    Animal child = new Animal(map.randomFreeNeighbour(cell.coordinates), mother.startEnergy,
                            fatherEnergy + motherEnergy, mother.getGenes().inherit(father.getGenes()), map);

                    if(animalClicked) {
                        father.addChild(child);
                        mother.addChild(child);
                    }
                    map.addConceived(child);
                }
            }
        }
        map.placeChildren();
    }
}

