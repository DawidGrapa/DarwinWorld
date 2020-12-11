package View;

import agh.cs.DarwinsGame.GrassField;
import agh.cs.DarwinsGame.Simulation;


import javax.swing.*;


public class DataPanel extends JPanel {
    Simulation simulation;
    GameMainFrame gameMainFrame;
    private JLabel days = new JLabel();
    private JLabel animals = new JLabel();
    private JLabel grasses = new JLabel();
    private JLabel dominatingGenotype = new JLabel();
    private JLabel averageAlive = new JLabel();
    private JLabel averageDead = new JLabel();
    private JLabel childs = new JLabel();
    private ButtonPanel buttonPanel;


    public DataPanel(Simulation simulation,GameMainFrame gameMainFrame){
        this.simulation=simulation;
        this.gameMainFrame=gameMainFrame;

        setSize(700,500);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        this.days.setText("Day: "+this.simulation.day);
        add(this.days);
        this.animals.setText("Animals: "+this.simulation.howManyAnimals);
        add(this.animals);
        this.grasses.setText("Grasses: "+this.simulation.getMap().getGrassHashMap().size());
        add(this.grasses);
        this.dominatingGenotype.setText("Dominating Genotype: "+0);
        add(this.dominatingGenotype);
        this.averageAlive.setText("Average energy for alive animals: "+simulation.getAverageEnergy());
        add(this.averageAlive);
        this.averageDead.setText("Average age for dead animals: "+simulation.getAverageDaysForDeadAnimals());
        add(this.averageDead);
        this.childs.setText("Average number of childs for alive animals: "+simulation.getAverageChild());
        add(this.childs);
        this.buttonPanel = new ButtonPanel(this.gameMainFrame);
        add(this.buttonPanel);
    }
    public void updateData(){
        this.days.setText("Day: "+this.simulation.day);
        this.animals.setText("Animals: "+this.simulation.howManyAnimals);
        this.grasses.setText("Grasses: "+this.simulation.getMap().getGrassHashMap().size());
        this.averageAlive.setText("Average energy for alive animals: "+simulation.getAverageEnergy());
        this.averageDead.setText("Average age for dead animals: "+simulation.getAverageDaysForDeadAnimals());
        this.childs.setText("Average number of childs for alive animals: "+simulation.getAverageChild());
    }

}
