package agh.cs.DarwinsGame;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GrassField extends AbstractWorldMap{


    public GrassField(RectangularMap area) {
        super.area=area;
    }

    public Vector2d getUnocuppiedPosition(){
        List<Vector2d> position = new ArrayList<>();
        for(Vector2d place : area.getPositions()){
            if(!grassHashMap.containsKey(place) && !animalsHashMap.containsKey(place))
                position.add(place);
        }
        Vector2d where = null;
        if(!position.isEmpty()){
            where = position.get(ThreadLocalRandom.current().nextInt(position.size()));
        }
        return where;
    }

    public Vector2d getFreePositionInArea(RectangularMap area){
        List<Vector2d> freePositions = new ArrayList<>();
        for(Vector2d position : this.area.getPositions()){
            if(!grassHashMap.containsKey(position) && !animalsHashMap.containsKey(position)&&area.contains(position)){
                freePositions.add(position);
            }
        }
        if(!freePositions.isEmpty()) return freePositions.get(ThreadLocalRandom.current().nextInt(freePositions.size()));
        return null;
    }

    public Vector2d getFreePositionNotInArea(RectangularMap area){
        List<Vector2d> freePositions = new ArrayList<>();
        for(Vector2d position : this.area.getPositions()){
            if(!area.contains(position)&&!grassHashMap.containsKey(position)&&!animalsHashMap.containsKey(position)){
                freePositions.add(position);
            }
        }
        if(!freePositions.isEmpty()) return freePositions.get(ThreadLocalRandom.current().nextInt(freePositions.size()));
        return null;
    }

    public void removeAnimal(Vector2d position,Animal animal){
        position = area.normalisePosition(position);
        List<Animal> animalList = animalsHashMap.get(position);
        animal.removeObserver(this);
        animalList.remove(animal);
        if(animalList.isEmpty()){
            animalsHashMap.remove(position);
        }
    }

    public void removeGrass(Vector2d position){
        grassHashMap.remove(position);
    }

    public void addAnimal(Animal animal){
        List<Animal> anml1 = animalsHashMap.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>());
        anml1.add(animal);
        animal.addObserver(this);
    }
    public void addGrass(Vector2d position,int withEnergy){
        grassHashMap.put(position,new Grass(position,withEnergy));
    }
    public Map<Vector2d,List<Animal>> getAnimalsHashMap(){
        return super.animalsHashMap;
    }
    public Map<Vector2d,Grass> getGrassHashMap(){
        return super.grassHashMap;
    }
    public Vector2d getSurroundedPosition(Vector2d position){
        Vector2d newPosition;
        List<Vector2d> newAnimalPosition = new ArrayList<>();
        for(MapDirection direction : MapDirection.values()){
            newPosition=position.add(direction.toUnit());
            newPosition= area.normalisePosition(newPosition);
            if(!animalsHashMap.containsKey(newPosition)&&!grassHashMap.containsKey(newPosition)){
                newAnimalPosition.add(newPosition);
            }
        }
        if(!newAnimalPosition.isEmpty()){
            return newAnimalPosition.get(ThreadLocalRandom.current().nextInt(newAnimalPosition.size()));
        }
        else{
            position = position.add(MapDirection.values()[ThreadLocalRandom.current().nextInt(8)].toUnit());
            position = area.normalisePosition(position);
            return position;
        }
    }
}
