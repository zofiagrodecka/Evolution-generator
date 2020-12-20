package project.visualisation;

import project.Simulation;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyFrame extends JFrame {

    private Simulation simulation;

    public MyFrame(String text, Simulation simulation){
        super(text);
        this.simulation = simulation;
        this.addWindowListener(new WindowAdapter() {

            @Override

            public void windowClosing(WindowEvent e) {

                //System.exit(0);
                simulation.end();
                simulation.setWindowClosed();
            }
        });
    }
}
