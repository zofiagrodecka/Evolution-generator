package project;

public enum MapDirection {

    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public Vector2d toUnitVector(){
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case SOUTH -> new Vector2d(0, -1);
            case WEST -> new Vector2d(-1, 0);
            case EAST -> new Vector2d(1, 0);
            case NORTHEAST -> new Vector2d(1,1);
            case NORTHWEST -> new Vector2d(-1, 1);
            case SOUTHEAST -> new Vector2d(1,-1);
            default -> new Vector2d(-1,-1);
        };
    }

    public MapDirection rotateby(int number){
        MapDirection thisDirection = this;

        for(int i=0; i<number; i++){
            thisDirection = thisDirection.next();
        }

        return thisDirection;
    }

    public MapDirection next(){
        return switch (this) {
            case NORTH -> NORTHEAST;
            case NORTHEAST -> EAST;
            case EAST -> SOUTHEAST;
            case SOUTHEAST -> SOUTH;
            case SOUTH -> SOUTHWEST;
            case SOUTHWEST -> WEST;
            case WEST -> NORTHWEST;
            default -> NORTH;
        };
    }
}
