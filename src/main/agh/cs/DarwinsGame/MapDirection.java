package agh.cs.DarwinsGame;

import java.util.concurrent.ThreadLocalRandom;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST
    ;
    @Override
    public String toString() {
        return switch (this) {
            case EAST -> "\u21d2";
            case NORTHEAST -> "\u21d7";
            case WEST -> "\u21d0";
            case SOUTHEAST -> "\u21d8";
            case NORTH -> "\u21d1";
            case NORTHWEST -> "\u21d6";
            case SOUTH -> "\u21d3";
            case SOUTHWEST -> "\u21d9";
            default -> null;
        };
    }

    public MapDirection next() {
        return MapDirection.values()[(this.ordinal() + 1) % MapDirection.values().length];
    }

    public MapDirection previous() {
        return MapDirection.values()[(this.ordinal() + MapDirection.values().length - 1) % MapDirection.values().length];
    }




    public Vector2d toUnit() {
        switch (this) {
            case NORTH:
                return new Vector2d(0, 1);
            case NORTHEAST:
                return new Vector2d(1, 1);
            case EAST:
                return new Vector2d(1, 0);
            case SOUTHEAST:
                return new Vector2d(1, -1);
            case SOUTH:
                return new Vector2d(0, -1);
            case SOUTHWEST:
                return new Vector2d(-1, -1);
            case WEST:
                return new Vector2d(-1, 0);
            case NORTHWEST:
                return new Vector2d(-1, 1);
        }
        return new Vector2d(0, 0);
    }
    public static MapDirection getRandom() {
        return MapDirection.values()[ThreadLocalRandom.current().nextInt(8)];
    }
}


