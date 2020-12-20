package project.visualisation;

import project.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel implements ActionListener {

    public final int HEIGHT = 100;
    public final int WIDTH = 200;
    private JButton pauseButton;
    private JButton continueButton;
    private JButton showDominantButton;
    private Simulation simulation;

    public ButtonPanel(Simulation simulation) {

        this.simulation = simulation;

        pauseButton = new JButton("pause");
        continueButton = new JButton("resume");
        showDominantButton = new JButton("show dominant animals");

        pauseButton.addActionListener(this);
        continueButton.addActionListener(this);
        showDominantButton.addActionListener(this);

        setLayout(new GridLayout(3,1));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(pauseButton);
        add(continueButton);
        add(showDominantButton);
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        Object source = event.getSource();

        if(source == pauseButton)
            simulation.pauseSimulation();
        else if(source == continueButton) {
            simulation.setShowDominant(false);
            simulation.animalInfoPanel.updateText("");
            simulation.resumeSimulation();
        }
        else if(source == showDominantButton)
            simulation.setShowDominant(true);
    }
}
