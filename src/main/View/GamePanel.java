package View;

import agh.cs.DarwinsGame.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class GamePanel extends JPanel {
    Simulation simulation;
    GameMainFrame gameMainFrame;
    GrassField map;


    public GamePanel(Simulation simulation,GameMainFrame gameMainFrame) {
        this.simulation = simulation;
        this.gameMainFrame=gameMainFrame;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.map = simulation.getMap();

        this.setSize((int) (gameMainFrame.frame.getWidth() * 0.7), gameMainFrame.frame.getHeight());
        this.setLocation((int) (0.3 * 1000), 0);
        int width = this.getWidth();
        int height = this.getHeight();
        int widthScale =(int) Math.floor(width / simulation.width);
        int heightScale =(int) Math.floor(height / simulation.height);
        width= simulation.width*widthScale;
        height= simulation.height*heightScale;
        gameMainFrame.frame.setSize(300+width,height);
        g.setColor(new Color(171, 210, 156));
        g.fillRect(0, 0, width, height);

        g.setColor(new Color(0, 160, 7));
        g.fillRect(simulation.jungleLowerLeft.x * widthScale,
                simulation.jungleLowerLeft.y * heightScale,
                simulation.jungleWidth * widthScale,
                simulation.jungleHeight * heightScale);

        java.util.List<Animal> animalsAtPosition = new ArrayList<>();
        for (Map.Entry<Vector2d, java.util.List<Animal>> entry : map.getAnimalsHashMap().entrySet()) {
            animalsAtPosition.addAll(entry.getValue());
        }

        for (Animal a : animalsAtPosition) {
            g.setColor(a.toColor(simulation.animalEnergy));
            int y = a.getPosition().y * heightScale;
            int x = a.getPosition().x * widthScale;
            g.fillOval(x, y, widthScale, heightScale);
        }

        for (Grass grass : map.getGrassHashMap().values()) {
            g.setColor(grass.toColor());
            int x = grass.getPosition().x * widthScale;
            int y = grass.getPosition().y * heightScale;
            g.fillRect(x, y, widthScale, heightScale);
        }


    }

}