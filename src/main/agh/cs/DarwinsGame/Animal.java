package agh.cs.DarwinsGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Animal {
    private MapDirection direction;
    public ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    private Genotype genotype;
    int birthDay;
    int deathDay = 0;
    Set<Animal> children = new HashSet<>();
    private int energy;
    private Vector2d position;


    public void setPosition(Vector2d position) {
        this.position = position;
    }

    @Override
    public String toString(){
        return this.direction.toString();
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public MapDirection getOrientation(){
        return this.direction;
    }

    public static buildNewAnimal buildAnimal(){
        return new buildNewAnimal();
    }

    public void move(){
        int howMany = genotype.getHowManyTimesItShouldTurns();
        for(int i=0;i<howMany;i++){
            this.direction = this.direction.next();
        }
        Vector2d ruch = this.direction.toUnit();
        Vector2d oldPosition = this.position;
        this.position=this.position.add(ruch);
        this.positionChanged(oldPosition,this.position,this);
    }

    public boolean isDead(int day) {
        if (energy <= 0) {
            deathDay = day;
            return true;
        }
        return false;
    }

    public Genotype getGenotype(){
        return this.genotype;
    }
    public int howManyChildren() {
        return children.size();
    }
    public int getEnergy(){
        return this.energy;
    }
    public void increaseEnergy(int energy){
        this.energy+=energy;
    }
    public void decreaseEnergy(int energy){
        this.energy-=energy;
    }

    public Animal breed(Animal secondParent,Vector2d position,int birthDay){
        Animal parent1 = this;
        Animal parent2 = secondParent;
        Animal baby = null;
        if(parent1.energy>10 && parent2.energy>10){
            int energy =  parent1.energy / 4 + parent2.energy / 4;
            parent1.decreaseEnergy(parent1.energy/4);
            parent2.decreaseEnergy(parent2.energy/4);
            Genotype genotype = new Genotype(parent1,parent2);
            baby = Animal.buildAnimal().withPosition(position).withBirthDay(birthDay).withEnergy(energy).withGenotype(genotype).build();
            parent1.children.add(baby);
            parent2.children.add(baby);
        }
        return baby;

    }

    public static class buildNewAnimal{             //here i build new animal with some parameters or without
        Genotype genotype = new Genotype();
        private int energy = 10;
        private Vector2d position;
        private int birthDay;
        private MapDirection direction = MapDirection.getRandom();

        public buildNewAnimal withEnergy(int energy){
            this.energy = energy;
            return this;
        }
        public buildNewAnimal withBirthDay(int birthDay){
            this.birthDay=birthDay;
            return this;
        }
        public buildNewAnimal withPosition(Vector2d position){
            this.position = position;
            return this;
        }
        public buildNewAnimal withGenotype(Genotype genotype){
            this.genotype=genotype;
            return this;
        }
        public Animal build(){
            Animal animal = new Animal();
            animal.genotype=genotype;
            animal.energy=energy;
            animal.position=position;
            animal.birthDay=birthDay;
            animal.direction=direction;
            return animal;
        }
    }
    public Color toColor(int starting) {
        if (energy == 0) return new Color(222, 221, 224);
        else if (energy <   starting/5) return new Color(224, 179, 173);
        else if (energy <  starting*4/10) return new Color(224, 142, 127);
        else if (energy < starting*6/10) return new Color(201, 124, 110);
        else if (energy < starting*8/10) return new Color(182, 105, 91);
        else if (energy < starting) return new Color(164, 92, 82);
        else if (energy < 2 * starting) return new Color(146, 82, 73);
        else if (energy < 4 * starting) return new Color(128, 72, 64);
        else if (energy < 8 * starting) return new Color(88, 50, 44);
        else if (energy < 10 * starting) return new Color(74, 42, 37);
        return new Color(55, 31, 027);
    }

    
    void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }
    void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }
    void positionChanged(Vector2d oldPosition,Vector2d newPosition,Animal o){
        for(IPositionChangeObserver observer : observers){
            observer.positionChanged(oldPosition,newPosition,o);
        }
    }

}

