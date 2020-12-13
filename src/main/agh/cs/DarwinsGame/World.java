package agh.cs.DarwinsGame;

import Config.Config;
import View.GameMainFrame;



public class World {
    public static void main(String[] args) {
        try{
            for(int i=0;i<Config.getInstance().getHowManyMaps();i++){
            GameMainFrame gameMainFrame = new GameMainFrame(new Simulation(Config.getInstance().getMapWidth(), Config.getInstance().getMapHeight(), Config.getInstance().getAnimalsAtStart(), Config.getInstance().getAnimalEnergy(), Config.getInstance().getGrassEnergy(), Config.getInstance().getMoveEnergyCost(), Config.getInstance().getJunglePercentage(), Config.getInstance().getDelayTime()));
            gameMainFrame.startSimulation();
        }
        } catch (Exception e) {
            System.out.println("Zakonczono z powodu: "+e);
        }

    }
}

