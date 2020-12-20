package project;

import java.awt.*;

public class Plant {

    private final Vector2d position;
    private final WorldMap map;

    public Plant(Vector2d position, WorldMap map){
        this.position = position;
        this.map = map;
    }

    public Vector2d getPosition() {
        return position;
    }

    public String toString(){
        return "*";
    }

    public Color toColor(){
        return new Color(6, 95, 6);
    }
}
