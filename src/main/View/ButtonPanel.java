package View;

import Config.Config;
import agh.cs.DarwinsGame.Animal;
import agh.cs.DarwinsGame.Simulation;
import agh.cs.DarwinsGame.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ButtonPanel extends JPanel implements ActionListener {
    private GameMainFrame gameMainFrame;
    private JButton start;
    private JButton stop;
    private JButton save;
    private JButton show;
    private JButton pin;
    private JButton restart;
    private boolean isPaused = false;
    private boolean firstTime = false;
    private boolean firstPin = false;
    public Animal animal;
    public String result;
    int i=0;


    public ButtonPanel(GameMainFrame gameMainFrame){
        this.gameMainFrame = gameMainFrame;
        setPreferredSize(new Dimension(700,0));
        setLayout(new FlowLayout(FlowLayout.CENTER,20,20));

        this.start = new JButton("Start");
        this.stop = new JButton("Stop");
        this.start.addActionListener(this);
        this.stop.addActionListener(this);
        this.save = new JButton("SAVE");
        this.save.addActionListener(this);
        this.show = new JButton("Show animals with dominating gene");
        this.show.addActionListener(this);
        this.pin = new JButton("Pin animal");
        this.pin.addActionListener(this);
        this.restart = new JButton("RESTART SIMULATION");
        this.restart.addActionListener(this);

        add(this.start);
        add(this.stop);
        add(this.save);
        add(this.show);
        add(this.pin);
        add(this.restart);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == this.start && isPaused){
            this.gameMainFrame.timer.start();
            this.isPaused=false;
            Config.getInstance().pinning = false;
        }

        else if(source == this.stop && !isPaused){
            this.gameMainFrame.timer.stop();
            this.isPaused=true;
            if(!this.firstTime)
            gameMainFrame.frame.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(isPaused && !Config.getInstance().pinning){
                        int x=e.getX();
                        if(x>gameMainFrame.frame.getWidth()/2)
                            x=x-gameMainFrame.frame.getWidth()/2;
                        else return;
                        int y=e.getY();
                        x=x/ gameMainFrame.gamePanel.widthScale;
                        y=(y-Config.getInstance().getToolbarScale())/ gameMainFrame.gamePanel.heightScale;
                        List<Animal> animals = gameMainFrame.simulation.getMap().getAnimalsHashMap().get(new Vector2d(x,y));
                        if(animals!=null) {
                            Animal animal = animals.get(ThreadLocalRandom.current().nextInt(animals.size()));
                            JOptionPane.showMessageDialog(null,"Genes: "+
                                    animal.getGenotype().genes.toString()+"\n\nThis animal has had "
                                    +animal.howManyChildren()+" children"+"\n\nThis animal has had "
                                    +animal.howManyAncestors+" ancestors");

                        }}
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            this.firstTime = true;
        }

        else if(source == this.save && isPaused){
            try {

                FileWriter myWriter = new FileWriter(i+"Zapis.txt"); i++;
                myWriter.write("Day: "+this.gameMainFrame.simulation.day+"\n"+"Animals: "+this.gameMainFrame.simulation.howManyAnimals+
                        "\n"+"Grasses: "+this.gameMainFrame.simulation.getMap().getGrassHashMap().size()+"\n"+
                        "Average energy for alive animals: "+gameMainFrame.simulation.getAverageEnergy()+"\n"+"Dominating gene: "+gameMainFrame.simulation.getDominatingGene()+"\n"+"Average age for dead animals: "
                        +gameMainFrame.simulation.getAverageDaysForDeadAnimals()+"\n"+
                        "Average number of childs for alive animals: "+gameMainFrame.simulation.getAverageChild());
                myWriter.close();
                JOptionPane.showMessageDialog(null,"File saved succesfully...");

            } catch (IOException a) {
                JOptionPane.showMessageDialog(null,"Something went wrong..");
                a.printStackTrace();
            }
            }

        else if(source==this.show && isPaused){
            Config.getInstance().show=true;
            gameMainFrame.gamePanel.repaint();
        }

        else if(source == this.pin && isPaused){
            Config.getInstance().pinning = true;
            if(!this.firstPin) {
                gameMainFrame.frame.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (isPaused && animal == null && Config.getInstance().pinning) {
                            int x = e.getX();
                            if (x > gameMainFrame.frame.getWidth() / 2)
                                x = x - gameMainFrame.frame.getWidth() / 2;
                            else return;
                            int y = e.getY();
                            x = x / gameMainFrame.gamePanel.widthScale;
                            y = (y - Config.getInstance().getToolbarScale()) / gameMainFrame.gamePanel.heightScale;
                            List<Animal> animals = gameMainFrame.simulation.getMap().getAnimalsHashMap().get(new Vector2d(x, y));
                            if (animals != null) {
                                while (true) {
                                    result = (String) JOptionPane.showInputDialog(
                                            null,
                                            "Type name for your pinned animal",
                                            "Naming animal",
                                            JOptionPane.PLAIN_MESSAGE,
                                            null,
                                            null,
                                            ""
                                    );
                                    if (result != null && result.length() > 0) {
                                        animal = animals.get(0);
                                        animal.pinned = true;
                                        Config.getInstance().springoff=animal.howManyChildren();
                                        gameMainFrame.dataPanel.updateData();
                                        break;
                                    }
                                    if (result == null) return;
                                }
                            }
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
            }
            this.firstPin = true;
            if(animal!=null){
                JOptionPane.showMessageDialog(null,"You have already pinned!");
            }

        }

        else if(source==this.restart){
            gameMainFrame.frame.dispose();
            gameMainFrame = new GameMainFrame(new Simulation(gameMainFrame.simulation.width,gameMainFrame.simulation.height,gameMainFrame.simulation.getHowManyAnimalsAtStart,gameMainFrame.simulation.animalEnergy,gameMainFrame.simulation.grassEnergy,gameMainFrame.simulation.moveEnergyCost,gameMainFrame.simulation.junglePercentage,gameMainFrame.simulation.delay));
            gameMainFrame.startSimulation();
        }
    }
}
