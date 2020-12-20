package project.visualisation;

import project.Simulation;

import javax.swing.*;
import java.awt.*;


public class Visualisation {

    private final Simulation simulation;
    private final SimulationPanel simulationPanel;

    public final StatisticsPanel statisticsPanel;
    public final StatisticsPanel animalInfoPanel;

    public Visualisation(SimulationPanel panel, Simulation simulation, StatisticsPanel statisticsPanel, StatisticsPanel animalInfoPanel){
        this.simulationPanel = panel;
        this.simulation = simulation;
        this.statisticsPanel = statisticsPanel;
        this.animalInfoPanel = animalInfoPanel;
    }

    public void prepareFrame(){

        JFrame frame = new JFrame("Evolution simulation");
        frame.setLocation(600,30); // approximately in the middle of my screen
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Quit the application when the JFrame is closed

        GridBagLayout layout = new GridBagLayout();
        frame.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
        layout.setConstraints(simulationPanel, constraints);
        frame.add(simulationPanel);

        ButtonPanel buttonsPanel = new ButtonPanel(simulation);
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE;
        layout.setConstraints(buttonsPanel, constraints);
        frame.add(buttonsPanel);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        layout.setConstraints(statisticsPanel, constraints);
        frame.add(statisticsPanel);


        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        layout.setConstraints(animalInfoPanel, constraints);
        frame.add(animalInfoPanel);

        frame.pack();
        frame.setVisible(true);
    }
}
