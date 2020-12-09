package View;

import agh.cs.DarwinsGame.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class GamePanel extends JPanel {
    Simulation simulation;
    GrassField map;
    int width = 700;
    int height = 700;
    int heightScale;
    int widthScale;

    public GamePanel(Simulation simulation) {
        this.simulation = simulation;
        widthScale = (int) Math.floor(width / simulation.width);
        heightScale = (int ) Math.floor(height / simulation.height);
        width=widthScale*simulation.width;
        height=heightScale*simulation.height;
        initializeVariables();
        initializeLayout();
    }

    private void initializeVariables() {
        Timer timer = new Timer(10, new GameLoop(this));
        timer.start();
    }

    private void initializeLayout() {
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.map = simulation.getMap();

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

    public void doOneLoop() {
        this.simulation.simulate();
        repaint();
    }
}
