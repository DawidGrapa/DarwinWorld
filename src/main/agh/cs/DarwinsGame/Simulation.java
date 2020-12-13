package agh.cs.DarwinsGame;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation {
    public int day;
    GrassField map;
    RectangularMap jungleArea;
    RectangularMap areaOfMap;
    public int grassEnergy;
    public int animalEnergy;
    public int width;
    public int height;
    public Vector2d jungleUpperRight;
    public Vector2d jungleLowerLeft;
    public int jungleWidth;
    public int jungleHeight;
    public int moveEnergyCost;
    public int howManyAnimals=0;
    private int averageEnergy = 0;
    private int howManyDeadAnimals=0;
    private int howManyDaysForDeadAnimals=0;
    private int averageChild;
    public int delay;
    public int [] dominatingGenotype  = new int[8];
    public int getHowManyAnimalsAtStart;
    public int junglePercentage;


    public Simulation(int width,int height,int howManyAnimalsAtStart,int animalEnergy,int grassEnergy,int moveEnergyCost,int junglePercentage,int delay){
        day =0;
        this.junglePercentage=junglePercentage;
        areaOfMap = new RectangularMap(new Vector2d(0,0),new Vector2d(width-1,height-1));
        jungleLowerLeft = new Vector2d( width/2-width*junglePercentage/200, height/2-height*junglePercentage/200);
        jungleUpperRight = new Vector2d(width/2+width*junglePercentage/200,height/2+height*junglePercentage/200);
        jungleArea = new RectangularMap(jungleLowerLeft,jungleUpperRight);
        map = new GrassField(areaOfMap);
        this.animalEnergy=animalEnergy;
        this.getHowManyAnimalsAtStart= howManyAnimalsAtStart;
        createAnimals(howManyAnimalsAtStart);
        this.grassEnergy=grassEnergy;
        this.width=width;
        this.height=height;
        jungleWidth=jungleUpperRight.x-jungleLowerLeft.x+1;
        jungleHeight = jungleUpperRight.y-jungleLowerLeft.y+1;
        this.moveEnergyCost=moveEnergyCost;
        this.delay=delay;
    }

    public void simulate(){
        Map<Vector2d,List<Animal>> animals = map.getAnimalsHashMap();
        Map<Vector2d,Grass> grasses = map.getGrassHashMap();
        removeDeadAnimals(animals);
        moveAnimals(animals);
        removeDeadAnimals(animals);
        animals=map.getAnimalsHashMap();
        eatGrass(animals,grasses);
        breedAnimals(animals);
        generateGrass();
        day++;
    }

    void createAnimals(int howManyAnimals){
        for(int i=0;i<howManyAnimals;i++){
            Vector2d animalPosition = map.getUnocuppiedPosition();
            if(animalPosition!=null){
                this.howManyAnimals++;
                map.addAnimal(Animal.buildAnimal().withBirthDay(day).withPosition(animalPosition).withEnergy(animalEnergy).build());
            }
        }
    }
    void removeDeadAnimals(Map<Vector2d,List<Animal>> animals){
        List<Animal> animalsAtPosition = new ArrayList<>();
        for(Map.Entry<Vector2d,List<Animal>> entry : animals.entrySet()){
            animalsAtPosition.addAll(entry.getValue());
        }
        for(Animal animal : animalsAtPosition){
            if(animal.isDead(day)){
                this.howManyAnimals--;
                this.howManyDeadAnimals++;
                this.howManyDaysForDeadAnimals+=(this.day-animal.birthDay);
                map.removeAnimal(animal.getPosition(),animal);
            }
        }
    }


    void moveAnimals(Map<Vector2d,List<Animal>> animals){
        this.averageEnergy=0;
        this.averageChild=0;
        this.dominatingGenotype= new int[8];
        List<Animal> animalsAtPosition = new ArrayList<>();
        for(Map.Entry<Vector2d,List<Animal>> entry : animals.entrySet()){
            animalsAtPosition.addAll(entry.getValue());
        }
        for(Animal animal : animalsAtPosition){
            dominatingGenotype[animal.getBestGene()]++;
            averageChild+=animal.howManyChildren();
            averageEnergy+=animal.getEnergy();
            animal.move();
            animal.decreaseEnergy(moveEnergyCost);
        }
    }

    void eatGrass(Map<Vector2d,List<Animal>> animals,Map<Vector2d,Grass> grass){
        for(Map.Entry<Vector2d,List<Animal>> entry : animals.entrySet()){
            if(grass.containsKey(entry.getKey())){
               List<Animal> animalsAtPosition = getListStrongestAnimalToFeed(animals, entry.getKey());
               for(Animal animal : animalsAtPosition){
                   animal.increaseEnergy( grassEnergy /animalsAtPosition.size());
               }
               map.removeGrass(entry.getKey());
            }
        }
    }

    void breedAnimals(Map<Vector2d,List<Animal>> animals){
        HashMap<Vector2d,List<Animal>> animals1= new HashMap<>(animals);
        for(Map.Entry<Vector2d,List<Animal>> entry : animals1.entrySet()){
            if(entry.getValue().size()>1){
                List<Animal> animalsAtPosition = getListStrongestAnimalToBreed(animals1, entry.getKey());
                int x = 0;
                int y = 1;
                if(animalsAtPosition.get(0).getEnergy()==animalsAtPosition.get(1).getEnergy()){
                    x = ThreadLocalRandom.current().nextInt(animalsAtPosition.size()-1);
                    y = ThreadLocalRandom.current().nextInt(x,animalsAtPosition.size());
                }
                Animal baby = animalsAtPosition.get(x).breed(animalsAtPosition.get(y),map.getSurroundedPosition(entry.getKey()),day);
                if(baby!=null) {

                    this.howManyAnimals++;
                    map.addAnimal(baby);
                }
            }
        }
    }

    void generateGrass(){
        Vector2d in = map.getFreePositionInArea(jungleArea);
        Vector2d notIn = map.getFreePositionNotInArea(jungleArea);
        if(!(in==null))
        map.addGrass(in,grassEnergy);
        if(!(notIn==null))
        map.addGrass(notIn,grassEnergy);
    }

    List<Animal> getListStrongestAnimalToFeed(Map<Vector2d,List<Animal>> animals,Vector2d position){
        List<Animal> animalsAtPosition = animals.get(position);
        float biggestEnergy = animalsAtPosition.get(0).getEnergy();
        for(Animal animal : animalsAtPosition){
            if(animal.getEnergy()>biggestEnergy) biggestEnergy=animal.getEnergy();
        }
        List<Animal> array = new ArrayList<>();
        for(Animal animal : animalsAtPosition){
            if(animal.getEnergy()==biggestEnergy){
                array.add(animal);
            }
        }
        return array;
    }
    List<Animal> getListStrongestAnimalToBreed(Map<Vector2d,List<Animal>> animals,Vector2d position){
        int firstMax=0;
        int secondMax=0;
        List<Animal> arrayWithMax = new ArrayList<>();
        List<Animal> arrayWithSecondMax = new ArrayList<>();
        for(Animal animal : animals.get(position)){
            if(animal.getEnergy()>firstMax) firstMax=animal.getEnergy();
        }
        for(Animal animal : animals.get(position)){
            if(animal.getEnergy()>secondMax && animal.getEnergy()<firstMax) secondMax=animal.getEnergy();
        }
        for(Animal animal : animals.get(position)){
            if(animal.getEnergy()==firstMax){
                arrayWithMax.add(animal);
            }
            if(animal.getEnergy()==secondMax){
                arrayWithSecondMax.add(animal);
            }
        }
        if (arrayWithMax.size() <= 1) {
            arrayWithMax.add(arrayWithSecondMax.get(ThreadLocalRandom.current().nextInt(arrayWithSecondMax.size())));
        }
        return arrayWithMax;

    }
    public GrassField getMap(){
        return this.map;
    }

    public int getAverageEnergy(){
        return this.averageEnergy;
    }
    public int getAverageDaysForDeadAnimals(){
        if(howManyDeadAnimals!=0)
        return howManyDaysForDeadAnimals/howManyDeadAnimals;
        else return 0;
    }
    public int getAverageChild(){
        if(howManyAnimals!=0)
        return averageChild/howManyAnimals;
        else
            return 0;
    }
    public int getDominatingGene(){
        int max = -1;
        List<Integer> gen = new ArrayList<>();
        for(int i=0;i<8;i++){
            if(this.dominatingGenotype[i]>max) max = this.dominatingGenotype[i];
        }
        for(int i=0;i<8;i++){
            if(this.dominatingGenotype[i]==max)gen.add(i);
        }
        return gen.get(ThreadLocalRandom.current().nextInt(gen.size()));
    }
}
