package project.input;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import project.Parameters;
import java.io.FileReader;
import java.io.IOException;

public class JSONReader {

    public Parameters parseParameters(FileReader fileReader) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        Object object = parser.parse(fileReader);
        JSONObject jsonObject = (JSONObject) object;

        int width = (int) (long) jsonObject.get("width");
        int height = (int) (long)  jsonObject.get("height");
        int jungleHeight = (int) (long) jsonObject.get("jungleHeight");
        int jungleWidth = (int) (long) jsonObject.get("jungleWidth");
        int animalsNumber = (int) (long) jsonObject.get("initialAnimalsNumber");
        int startEnergy = (int) (long) jsonObject.get("startEnergy");
        int moveEnergy = (int) (long) jsonObject.get("moveEnergy");
        int plantEnergy = (int) (long) jsonObject.get("plantEnergy");
        int mapsNumber = (int) (long) jsonObject.get("mapsNumber");

        return new Parameters(width, height, jungleWidth, jungleHeight, startEnergy, moveEnergy, plantEnergy, animalsNumber, mapsNumber);
    }
}
