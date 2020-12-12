package agh.cs.DarwinsGame;

import View.GameMainFrame;



public class World {
    public static void main(String[] args) {
        for(int i=0;i<1;i++){
            GameMainFrame gameMainFrame = new GameMainFrame(new Simulation(5,5,2,30,20,1,50,100));
            gameMainFrame.startSimulation();
        }
    }
}

