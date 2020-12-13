package View;

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
        if(gameMainFrame.timer.isRunning()) {
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
        else {
            int gen = simulation.getDominatingGene();
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

            java.util.List<Animal> animalsAtPosition = new ArrayList<>();
            for (Map.Entry<Vector2d, java.util.List<Animal>> entry : map.getAnimalsHashMap().entrySet()) {
                animalsAtPosition.addAll(entry.getValue());
            }

            for (Animal a : animalsAtPosition) {
                if(a.getBestGene().contains(gen)){
                    g.setColor(new Color(255, 230, 0));
                    int y = a.getPosition().y * heightScale;
                    int x = a.getPosition().x * widthScale;
                    g.fillOval(x, y, widthScale, heightScale);
                }
                else{
                g.setColor(a.toColor(simulation.animalEnergy));
                int y = a.getPosition().y * heightScale;
                int x = a.getPosition().x * widthScale;
                g.fillOval(x, y, widthScale, heightScale);
            }}

        }

    }

}