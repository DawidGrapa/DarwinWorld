package agh.cs.DarwinsGame;

import View.GameMainFrame;



public class World {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(300,300,5,10,2000,1,50);
        GameMainFrame gameMainFrame = new GameMainFrame(simulation);
    }
}

