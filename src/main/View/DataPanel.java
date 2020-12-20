package View;

import Config.Config;
import agh.cs.DarwinsGame.Simulation;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


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
    private JLabel pinnedChilds = new JLabel();
    private JLabel pinnedDead = new JLabel();
    private JLabel pinnedAncestors = new JLabel();
    private JLabel pinnedEnergy = new JLabel();
    private JLabel pinnedSpringoff = new JLabel();
    public ButtonPanel buttonPanel;
    private JLabel pinned = new JLabel();


    public DataPanel(Simulation simulation,GameMainFrame gameMainFrame){
        this.simulation=simulation;
        this.gameMainFrame=gameMainFrame;
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setSize(350,300);
        setBackground(new Color(220, 171, 119, 255));
        EmptyBorder border1 = new EmptyBorder(40, 0, 10, 0);
        EmptyBorder border2 = new EmptyBorder(0, 0, 20, 0);
        this.days.setText("Day: "+this.simulation.day);
        this.days.setBorder(border1);
        add(this.days);
        this.animals.setText("Animals: "+this.simulation.howManyAnimals);
        add(this.animals);
        this.grasses.setText("Grasses: "+this.simulation.getMap().getGrassHashMap().size());
        add(this.grasses);
        this.dominatingGenotype.setText("Dominating gene: "+this.simulation.getDominatingGene());
        add(this.dominatingGenotype);
        this.averageAlive.setText("Average energy for alive animals: "+simulation.getAverageEnergy());
        add(this.averageAlive);
        this.averageDead.setText("Average age for dead animals: "+simulation.getAverageDaysForDeadAnimals());
        add(this.averageDead);
        this.childs.setText("Average number of childs for alive animals: "+simulation.getAverageChild());
        add(this.childs);
        EmptyBorder border = new EmptyBorder(40, 0, 10, 0);
        this.pinned.setBorder(border);
        add(this.pinned);
        add(this.pinnedEnergy);
        add(this.pinnedChilds);
        add(this.pinnedSpringoff);
        add(this.pinnedAncestors);
        add(this.pinnedDead);
        pinnedDead.setBorder(border2);
        this.buttonPanel = new ButtonPanel(this.gameMainFrame);
        add(this.buttonPanel);
    }
    public void updateData(){
        setFocusable(true);
        setBackground(new Color(220, 171, 119, 255));
        this.days.setText("Day: "+this.simulation.day);
        this.animals.setText("Animals: "+this.simulation.howManyAnimals);
        this.grasses.setText("Grasses: "+this.simulation.getMap().getGrassHashMap().size());
        this.averageAlive.setText("Average energy for alive animals: "+simulation.getAverageEnergy());
        this.averageDead.setText("Average age for dead animals: "+simulation.getAverageDaysForDeadAnimals());
        this.childs.setText("Average number of childs for alive animals: "+simulation.getAverageChild());
        this.dominatingGenotype.setText("Dominating gene: "+this.simulation.getDominatingGene());
        if(buttonPanel.result!=null){
            this.pinnedEnergy.setText(buttonPanel.result+"'s energy: "+buttonPanel.animal.getEnergy());
        this.pinned.setText("Pinned Animal: ----  "+buttonPanel.result+"  ----  It is red RN");
        this.pinnedChilds.setText(buttonPanel.result+" has had "+buttonPanel.animal.howManyChildren()+" children");
        this.pinnedSpringoff.setText(buttonPanel.result+" has had "+Config.getInstance().springoff+" springoff");
        this.pinnedAncestors.setText(buttonPanel.result+" has had "+buttonPanel.animal.howManyAncestors+" ancestors");
        if(buttonPanel.animal.dead())
            this.pinnedDead.setText(buttonPanel.result+" has died on day "+buttonPanel.animal.deathDay+" [*]");
        else this.pinnedDead.setText("Still alive :))");
        }}

}
