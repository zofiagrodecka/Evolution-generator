package project.visualisation;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class StatisticsWriter {

    private boolean append = false;

    public StatisticsWriter(){

    }

    public void writeStatistics(String filename, String text) throws IOException {

        try (PrintWriter file = new PrintWriter(new FileWriter(filename, append))) {

            file.println(text);
            append = true;
        }
    }
}
