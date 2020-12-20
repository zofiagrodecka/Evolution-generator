package project;

import java.util.*;

public class Vector2d {

    private final int NEIGHBOURS_NUMBER = 8;

    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString(){

        return "(" + this.x + "," + this.y + ")";
    }

    @Override
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;

        return that.x == this.x && that.y == this.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(x + other.x, y + other.y);
    }

    public Vector2d mod(Vector2d other){
        Vector2d result = this.add(other);
        return new Vector2d(result.x % other.x, result.y % other.y);
    }

    public boolean isIncluded(Vector2d lowLeft, Vector2d upRight){
        return this.follows(lowLeft) && this.precedes(upRight);
    }

    public Vector2d randomPositionPrecedes(Vector2d other){
        Random random = new Random();
        int positionX = random.nextInt(other.x - x +1) + x;
        int positionY = random.nextInt(other.y - y +1) + y;
        return new Vector2d(positionX, positionY);
    }

   public Vector2d[] neighbours(Vector2d upperRightWorld){

        Vector2d[] result = new Vector2d[NEIGHBOURS_NUMBER];
        int i = 0;

        for(MapDirection direction : MapDirection.values()) {

            result[i] = this.add(direction.toUnitVector()).mod(upperRightWorld);
            i++;
        }
        return result;
   }

}
