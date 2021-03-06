package agh.cs.DarwinsGame;

import java.awt.*;
import java.util.*;
import java.util.List;


import Config.Config;

public class Animal {
    private MapDirection direction;
    public ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    private Genotype genotype;
    public int birthDay;
    public int deathDay = 0;
    Set<Animal> children = new HashSet<>();
    private int energy;
    private Vector2d position;
    public int howManyAncestors=0;
    public boolean pinned = false;


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


    public static buildNewAnimal buildAnimal(){
        return new buildNewAnimal();
    }

    public void move(){
        int howMany = genotype.getHowManyTimesItShouldTurns();
        for(int i=0;i<howMany;i++){
            this.direction = this.direction.next();
        }
        Vector2d oldPosition = this.position;
        this.position=this.position.add(this.direction.toUnit());
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
        Animal baby = null;
        if(parent1.energy> Config.getInstance().getAnimalEnergy()/2 && secondParent.energy>Config.getInstance().getAnimalEnergy()/2){
            int energy =  parent1.energy / 4 + secondParent.energy / 4;
            parent1.decreaseEnergy(parent1.energy/4);
            secondParent.decreaseEnergy(secondParent.energy/4);
            Genotype genotype = new Genotype(parent1, secondParent);
            baby = Animal.buildAnimal().withPosition(position).withBirthDay(birthDay).withEnergy(energy).withGenotype(genotype).build();
            parent1.children.add(baby);
            secondParent.children.add(baby);
            if(parent1.pinned){ baby.pinned = true; Config.getInstance().springoff+=1;}
            if(secondParent.pinned) {baby.pinned=true; Config.getInstance().springoff+=1;}
            baby.howManyAncestors= parent1.howManyAncestors+ secondParent.howManyAncestors+2;
        }
        return baby;

    }

    public List<Integer> getBestGene(){
        int max = -1;
        List<Integer> gen = new ArrayList<>();
        for(int i=0;i<8;i++){
            if(this.genotype.isThereEveryGene[i]>max) max = this.genotype.isThereEveryGene[i];
        }
        for(int i=0;i<8;i++){
            if(this.genotype.isThereEveryGene[i]==max) {
                gen.add(i);
            }
        }
        return gen;
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
    public boolean dead(){
        return deathDay!=0;
    }

}

