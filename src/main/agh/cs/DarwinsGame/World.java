package agh.cs.DarwinsGame;

import View.GameMainFrame;



public class World {
    public static void main(String[] args) {
        for(int i=0;i<2;i++){
            GameMainFrame gameMainFrame = new GameMainFrame(new Simulation(10,10,6,10,20,1,50));
            gameMainFrame.startSimulation();
        }
    }
}

