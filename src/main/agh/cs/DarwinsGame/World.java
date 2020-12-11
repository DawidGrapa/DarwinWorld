package agh.cs.DarwinsGame;

import View.GameMainFrame;



public class World {
    public static void main(String[] args) {
        for(int i=0;i<1;i++){
            GameMainFrame gameMainFrame = new GameMainFrame(new Simulation(10,10,5,30,20,1,50,10));
            gameMainFrame.startSimulation();
        }
    }
}

