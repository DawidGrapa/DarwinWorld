package agh.cs.DarwinsGame;

import java.awt.*;

public class Grass{
    private Vector2d position;
    public int energy;

    public Grass(Vector2d vector2d,int energy) {
        this.position=vector2d;
        this.energy=energy;
    }


    public Vector2d getPosition(){
        return this.position;
    }

    public String toString(){
        return "*";
    }

    public Color toColor() {
        return new Color(31, 222, 63);
    }
}
