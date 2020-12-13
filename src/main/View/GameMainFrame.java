package View;

import agh.cs.DarwinsGame.Simulation;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMainFrame implements ActionListener {
    public Simulation simulation;
    public JFrame frame;
    public Timer timer;
    public GamePanel gamePanel;
    public DataPanel dataPanel;

    public GameMainFrame(Simulation simulation){
        this.simulation=simulation;
        timer = new Timer(simulation.delay,this);

        frame = new JFrame("Evolution Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1400,700));
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocation(30,50);


        gamePanel = new GamePanel(this.simulation,this);
        dataPanel = new DataPanel(this.simulation,this);

        frame.setLayout(new GridLayout(1,2));
        frame.add(dataPanel);
        frame.add(gamePanel);
        frame.pack();



    }

    public void startSimulation(){
        timer.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simulation.simulate();
        dataPanel.updateData();
        gamePanel.repaint();
    }
}
