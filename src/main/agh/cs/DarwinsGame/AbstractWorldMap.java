package agh.cs.DarwinsGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements IPositionChangeObserver{
    protected Map<Vector2d,List<Animal>> animalsHashMap = new HashMap<>();
    protected Map<Vector2d,Grass> grassHashMap = new HashMap<>();
    RectangularMap area;

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition,Animal o) {
        oldPosition=area.normalisePosition(oldPosition);
        newPosition = area.normalisePosition(newPosition);

        animalsHashMap.get(oldPosition).remove(o);
        List<Animal> anml1 = animalsHashMap.computeIfAbsent(newPosition, k -> new ArrayList<>());
        o.setPosition(newPosition);
        anml1.add(o);
    List<Animal> list = animalsHashMap.get(oldPosition);
    if(list.isEmpty()){
        animalsHashMap.remove(oldPosition);
    }
    }


}
