package View;

import agh.cs.DarwinsGame.Animal;
import agh.cs.DarwinsGame.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ButtonPanel extends JPanel implements ActionListener {
    private GameMainFrame gameMainFrame;
    private JButton start;
    private JButton stop;
    private JButton save;
    private boolean isPaused = false;
    private boolean firstTime = false;


    public ButtonPanel(GameMainFrame gameMainFrame){
        this.gameMainFrame = gameMainFrame;
        setPreferredSize(new Dimension(700,0));
        setLayout(new FlowLayout(FlowLayout.CENTER,50,50));
        this.start = new JButton("Start");
        this.stop = new JButton("Stop");
        this.start.addActionListener(this);
        this.stop.addActionListener(this);
        this.save = new JButton("SAVE");
        this.save.addActionListener(this);
        add(this.start);
        add(this.stop);
        add(this.save);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == this.start && isPaused){
            this.gameMainFrame.timer.start();
            this.isPaused=false;
        }
        else if(source == this.stop && !isPaused){
            this.gameMainFrame.timer.stop();
            if(!this.firstTime)
            gameMainFrame.frame.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(isPaused){
                        int x=e.getX();
                        if(x>gameMainFrame.frame.getWidth()/2)
                            x=x-gameMainFrame.frame.getWidth()/2;
                        else return;
                        int y=e.getY();
                        x=x/ gameMainFrame.gamePanel.widthScale;
                        y=(y-28)/ gameMainFrame.gamePanel.heightScale;
                        List<Animal> animals = gameMainFrame.simulation.getMap().getAnimalsHashMap().get(new Vector2d(x,y));
                        if(animals!=null) {
                            Animal animal = animals.get(0);
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
            this.isPaused=true;
        }
        else if(source == this.save && isPaused){
            try {

                FileWriter myWriter = new FileWriter("Zapis.txt");
                myWriter.write("Day: "+this.gameMainFrame.simulation.day+"\n"+"Animals: "+this.gameMainFrame.simulation.howManyAnimals+
                        "\n"+"Grasses: "+this.gameMainFrame.simulation.getMap().getGrassHashMap().size()+"\n"+
                        "Average energy for alive animals: "+gameMainFrame.simulation.getAverageEnergy()+"\n"+"Average age for dead animals: "
                        +gameMainFrame.simulation.getAverageDaysForDeadAnimals()+"\n"+
                        "Average number of childs for alive animals: "+gameMainFrame.simulation.getAverageChild());
                myWriter.close();
                JOptionPane.showMessageDialog(null,"File saved succesfully...");

            } catch (IOException a) {
                JOptionPane.showMessageDialog(null,"Something went wrong..");
                a.printStackTrace();
            }
            }
    }
}
