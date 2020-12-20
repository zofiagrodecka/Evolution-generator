package project;

public class Parameters {

    public final int width;
    public final int height;
    public final int jungleHeight;
    public final int jungleWidth;
    public final int initialAnimalsNumber;
    public final int startEnergy;
    public final int moveEnergy;
    public final int plantEnergy;
    public final int mapsNumber;

    public Parameters(int width, int height, int jungleWidth, int jungleHeight, int startEnergy, int moveEnergy, int plantEnergy, int initialAnimalsNumber, int mapsNumber){

        this.width = width;
        this.height = height;
        this.jungleHeight = jungleHeight;
        this.jungleWidth = jungleWidth;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.initialAnimalsNumber = initialAnimalsNumber;
        this.mapsNumber = mapsNumber;
    }
}
