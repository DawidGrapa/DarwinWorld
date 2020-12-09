package agh.cs.DarwinsGame;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap {

    private Vector2d lowerLeft;
    private Vector2d upperRight;
    List<Vector2d> positions = new ArrayList<>();

    public RectangularMap(Vector2d lowerLeft, Vector2d upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        for (int i = lowerLeft.x; i < getWidth(); i++) {
            for (int j = lowerLeft.y; j < getHeight(); j++) {
                this.positions.add(new Vector2d(i, j));
            }
        }
    }

    public int getWidth() {
        return this.upperRight.x - this.lowerLeft.x + 1;
    }

    public int getHeight() {
        return this.upperRight.y - this.lowerLeft.y + 1;
    }

    public List<Vector2d> getPositions() {
        return this.positions;
    }


    public Vector2d normalisePosition(Vector2d position) {
        int x, y;
        if (position.x >= 0) {
            x = position.x % (upperRight.x + 1);
        } else {
            x = (upperRight.x + (1 + position.x) % (upperRight.x + 1));
        }
        if (position.y >= 0) {
            y = position.y % (upperRight.y + 1);
        } else {
            y = (upperRight.y + (1 + position.y) % (upperRight.y + 1));
        }
        return new Vector2d(x, y);
    }

    public boolean contains(Vector2d position){
        return position.follows(lowerLeft) && position.precedes(upperRight);
    }
}
