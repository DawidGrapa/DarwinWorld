package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel implements ActionListener {
    private GameMainFrame gameMainFrame;
    private JButton start;
    private JButton stop;
    private JButton save;
    private boolean isPaused = false;

    public ButtonPanel(GameMainFrame gameMainFrame){
        this.gameMainFrame = gameMainFrame;
        setPreferredSize(new Dimension(700,0));
        setLayout(new FlowLayout(FlowLayout.CENTER,30,30));
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
            this.isPaused=true;
        }
        else if(source == this.save && isPaused){
            JOptionPane.showMessageDialog(null,"Pomyślnie zapisano w folderze projektu..");
        }
    }
}
