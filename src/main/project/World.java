package project;

import org.json.simple.parser.ParseException;
import project.input.JSONReader;

import java.io.FileReader;
import java.io.IOException;

public class World {


    public static void main(String[] args) throws IOException, ParseException, InterruptedException {


        try {
            JSONReader reader = new JSONReader();
            Parameters parameters = reader.parseParameters(new FileReader("parameters.json"));

            WorldMap[] maps = new WorldMap[parameters.mapsNumber];

            Simulation[] simulations = new Simulation[parameters.mapsNumber];

            for (int i = 0; i < parameters.mapsNumber; i++) {

                maps[i] = new WorldMap(parameters.width, parameters.height, parameters.jungleWidth, parameters.jungleHeight);
                simulations[i] = new Simulation(maps[i], parameters.initialAnimalsNumber, parameters.startEnergy, parameters.plantEnergy, parameters.moveEnergy, 10 + (i * 100), 10 + (i * 100), ("Statistics" + i + ".txt"));
            }

            for(Simulation engine : simulations){

                engine.start();
            }

        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }

        while (true){

            /*After closing all animation windows following 3 threads remains active so that's my method to force program to exit
            Thread[main,5,main]
            Thread[Monitor Ctrl-Break,5,main]
            Thread[AWT-EventQueue-0,6,main]*/

            if(Thread.activeCount() < 4){
                System.exit(0);
            }

            Thread.sleep(200); // in order not to overload cpu
        }

    }

}
