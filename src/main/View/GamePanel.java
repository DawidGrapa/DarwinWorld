package View;

import Config.Config;
import agh.cs.DarwinsGame.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GamePanel extends JPanel {
    Simulation simulation;
    GameMainFrame gameMainFrame;
    GrassField map;
    int width=700;
    int height=700;
    public int widthScale;
    int heightScale;


    public GamePanel(Simulation simulation,GameMainFrame gameMainFrame) {
        this.simulation = simulation;
        this.gameMainFrame=gameMainFrame;
        widthScale=(int) Math.floor(width/this.simulation.width);
        heightScale=(int) Math.floor(height/this.simulation.height);
        this.width=widthScale*this.simulation.width;
        this.height=heightScale*this.simulation.height;
        setSize(new Dimension(this.width,this.height));
        gameMainFrame.frame.setPreferredSize(new Dimension(700+width,height+28)); //tutaj prosze wpisac wysokosc paska narzedzi, inaczej sie zepsuje :))
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.map = simulation.getMap();
        int gen = simulation.getDominatingGene();
        if(gameMainFrame.timer.isRunning() || !Config.getInstance().show) {
            g.setColor(new Color(171, 210, 156));
            g.fillRect(0, 0, width, height);

            g.setColor(new Color(0, 160, 7));
            g.fillRect(simulation.jungleLowerLeft.x * widthScale,
                    simulation.jungleLowerLeft.y * heightScale,
                    simulation.jungleWidth * widthScale,
                    simulation.jungleHeight * heightScale);


            for (Map.Entry<Vector2d, java.util.List<Animal>> entry : map.getAnimalsHashMap().entrySet()) {
                if(entry.getValue().contains(gameMainFrame.dataPanel.buttonPanel.animal)){
                    g.setColor(new Color(255,0,0));
                }
                else
                    g.setColor(entry.getValue().get(0).toColor(simulation.animalEnergy));
                int y = entry.getValue().get(0).getPosition().y * heightScale;
                int x = entry.getValue().get(0).getPosition().x * widthScale;
                g.fillOval(x, y, widthScale, heightScale);
            }


            for (Grass grass : map.getGrassHashMap().values()) {
                g.setColor(grass.toColor());
                int x = grass.getPosition().x * widthScale;
                int y = grass.getPosition().y * heightScale;
                g.fillRect(x, y, widthScale, heightScale);
            }

        }
        else if(!gameMainFrame.timer.isRunning() && Config.getInstance().show){
            g.setColor(new Color(171, 210, 156));
            g.fillRect(0, 0, width, height);

            g.setColor(new Color(0, 160, 7));
            g.fillRect(simulation.jungleLowerLeft.x * widthScale,
                    simulation.jungleLowerLeft.y * heightScale,
                    simulation.jungleWidth * widthScale,
                    simulation.jungleHeight * heightScale);

            for (Grass grass : map.getGrassHashMap().values()) {
                g.setColor(grass.toColor());
                int x = grass.getPosition().x * widthScale;
                int y = grass.getPosition().y * heightScale;
                g.fillRect(x, y, widthScale, heightScale);
            }

            for (Map.Entry<Vector2d, java.util.List<Animal>> entry : map.getAnimalsHashMap().entrySet()) {
                for(Animal animal: entry.getValue()){
                if(animal.getBestGene().contains(gen)){
                    g.setColor(new Color(255, 230, 0));
                    int y = animal.getPosition().y * heightScale;
                    int x = animal.getPosition().x * widthScale;
                    g.fillOval(x, y, widthScale, heightScale);
                    break;
                }
                else{
                g.setColor(entry.getValue().get(0).toColor(simulation.animalEnergy));
                int y = entry.getValue().get(0).getPosition().y * heightScale;
                int x = entry.getValue().get(0).getPosition().x * widthScale;
                g.fillOval(x, y, widthScale, heightScale);
                break;
            }}}
            Config.getInstance().show=false;
        }

    }

}