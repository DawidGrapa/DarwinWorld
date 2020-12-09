package agh.cs.DarwinsGame;

public class Vector2d {
     final public int x;
     final public int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return "("+this.x+","+this.y+")";
    }
    public boolean precedes(Vector2d other){
        if(other.x>=this.x && other.y>=this.y){
            return true;
        }
        else
            return false;
    }
    public boolean follows(Vector2d other){
        if(other.x<=this.x && other.y<=this.y){
            return true;
        }
        else
            return false;
    }
    public Vector2d upperRight(Vector2d other){
        Vector2d newVector = new Vector2d(Math.max(this.x,other.x),Math.max(this.y,other.y));
        return newVector;
    }
    public Vector2d lowerLeft(Vector2d other){
        Vector2d newVector = new Vector2d(Math.min(this.x,other.x),Math.min(this.y,other.y));
        return newVector;
    }
    public Vector2d add(Vector2d other){
        Vector2d newVector = new Vector2d((this.x+other.x),(this.y+other.y));
        return newVector;
    }
    public Vector2d subtract(Vector2d other){
        Vector2d newVector = new Vector2d((this.x-other.x),(this.y-other.y));
        return newVector;
    }
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d wektor = (Vector2d) other;
        if(this.x == wektor.x && this.y == wektor.y)
            return true;
        return false;
    }
    public Vector2d opposite(){
        Vector2d newVector = new Vector2d(-this.x,-this.y);
        return newVector;
    }

    @Override
    public int hashCode() {
        int hash=13;
        hash+=this.x*31;
        hash+=this.x*17;
        return hash;
    }
}




