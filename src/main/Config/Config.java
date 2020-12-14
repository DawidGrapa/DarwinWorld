package Config;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Config {
    private static Config instance = null;
    private int mapWidth;
    private int mapHeight;
    private int animalsAtStart;
    private int animalEnergy;
    private int grassEnergy;
    private int moveEnergyCost;
    private int junglePercentage;
    private int delayTime;
    private int howManyMaps;
    public boolean show = false;

    public static Config getInstance() {
        if (instance == null) {
            Gson gson = new Gson();
            String path = "parameters.json";
            try {
                JsonReader reader = new JsonReader(new FileReader(path));
                instance = gson.fromJson(reader, Config.class);
            } catch (FileNotFoundException e) {
                instance = new Config();
                e.printStackTrace();
            }
        }
        return instance;
    }
    public int getMapWidth(){
        if(mapWidth<0) throw new IllegalArgumentException("Zla szerokosc mapy..");
        return this.mapWidth;
    }
    public int getMapHeight(){
        if(mapHeight<0) throw new IllegalArgumentException("Zla wysokosc mapy..");
        return this.mapHeight;
    }
    public int getAnimalsAtStart(){
        if(animalsAtStart<0) throw new IllegalArgumentException("Zla ilosc poczatkowa zwierzat..");
        return this.animalsAtStart;
    }
    public int getGrassEnergy(){
        if(grassEnergy<0) throw new IllegalArgumentException("Zla energia trawy..");
        return this.grassEnergy;
    }
    public int getMoveEnergyCost(){
        if(moveEnergyCost<0) throw new IllegalArgumentException("Zly koszt ruchu dla jednego dnia..");
        return this.moveEnergyCost;
    }
    public int getJunglePercentage() {
        if(junglePercentage>100||junglePercentage<0) throw new IllegalArgumentException("Zly rozmiar jungli..(Podaj jej rozmiar w % - np. 50)");
        return this.junglePercentage;
    }
    public int getDelayTime(){
        if(delayTime<0) throw new IllegalArgumentException("Bledny delay..");
        return this.delayTime;
    }
    public int getHowManyMaps(){
        if(howManyMaps>2||howManyMaps<0){
            throw new IllegalArgumentException("Max 2 mapy..");
        }
        return this.howManyMaps;
    }
    public int getAnimalEnergy() {
        if(animalEnergy<0) throw new IllegalArgumentException("Zla energia poczatkowa zwierzaka..");
        return this.animalEnergy;
    }
    public void swapShow(){
        this.show = true;
    }
}
