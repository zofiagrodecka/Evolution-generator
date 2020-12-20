package project;

import org.json.simple.parser.ParseException;
import project.input.JSONReader;

import java.io.FileReader;
import java.io.IOException;

public class World {


    public static void main(String[] args) throws IOException, ParseException, InterruptedException {


        JSONReader reader = new JSONReader();
        Parameters parameters = reader.parseParameters(new FileReader("parameters.json"));

        WorldMap map = new WorldMap(parameters.width, parameters.height, parameters.jungleWidth, parameters.jungleHeight);
        //MapVisualiser v = new MapVisualiser(map);
        /*WorldMap[] maps = new WorldMap[parameters.mapsNumber];
        for(int i=0; i< parameters.mapsNumber; i++)
            maps[i] = map;*/

        Simulation engine = new Simulation(map, parameters.initialAnimalsNumber, parameters.startEnergy, parameters.plantEnergy, parameters.moveEnergy);
        engine.run();

        /*Simulation[] simulations = new Simulation[parameters.mapsNumber];
        SimulationEngine engines[] = new SimulationEngine[parameters.mapsNumber];

        for(int i=0; i<parameters.mapsNumber; i++){
            simulations[i] = new Simulation(map, parameters.initialAnimalsNumber, parameters.startEnergy, parameters.plantEnergy, parameters.moveEnergy);
            engines[i] = new SimulationEngine(simulations[i]);
        }

        for(Simulation engine : simulations){
            engine.run();
        }*/

    }

}
