package View;

import agh.cs.DarwinsGame.Simulation;

import javax.swing.*;

public class GameMainFrame extends JFrame {
    Simulation simulation;
    public GameMainFrame(Simulation simulation){
        this.simulation = simulation;
        initializeLayout();
    }



    public void initializeLayout() {
        add(new GamePanel(simulation));
        setTitle("Darwin World");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
    }

}
