package View;

import agh.cs.DarwinsGame.GrassField;
import agh.cs.DarwinsGame.Simulation;

import javax.swing.*;
import java.awt.*;

public class DataPanel extends JPanel {
    Simulation simulation;
    GrassField map;
    GameMainFrame gameMainFrame;
    public DataPanel(Simulation simulation,GameMainFrame gameMainFrame){
        this.simulation=simulation;
        this.gameMainFrame=gameMainFrame;
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        map=simulation.getMap();
        this.setSize((int) (gameMainFrame.frame.getWidth() * 0.3), gameMainFrame.frame.getHeight());
        this.setLocation(0, 0);
        g.drawString("Total days: " + simulation.day, 10, 20 );
        g.drawString("Grasses: "+simulation.getMap().getGrassHashMap().size(),10,40);
        g.drawString("Animals: "+simulation.howManyAnimals,10,60);
    }
}
