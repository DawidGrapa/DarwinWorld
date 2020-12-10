package View;

import agh.cs.DarwinsGame.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMainFrame implements ActionListener {
    Simulation simulation;
    public JFrame frame;
    public Timer timer;
    public GamePanel gamePanel;
    public DataPanel dataPanel;

    public GameMainFrame(Simulation simulation){
        this.simulation=simulation;
        timer = new Timer(100,this);

        frame = new JFrame("Evolution Simulator");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel = new GamePanel(this.simulation,this);
        gamePanel.setSize(new Dimension(1, 1));
        dataPanel = new DataPanel(this.simulation,this);
        dataPanel.setSize(1,1);
        frame.add(gamePanel);
        frame.add(dataPanel);

    }

    public void startSimulation(){
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dataPanel.repaint();
        gamePanel.repaint();
        simulation.simulate();
    }
}
