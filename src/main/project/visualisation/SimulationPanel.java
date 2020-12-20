package project.visualisation;

import project.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.lang.Math.min;

public class SimulationPanel extends JPanel implements MouseListener {

    private final int BEGIN_COORDINATES = 0;
    private final int MAX_SCREEN_WIDTH = 1300;
    private final int MAX_SCREEN_HEIGHT = 600;
    private final int MIN_CELL = 3;

    private final WorldMap map;
    private final int width;
    private final int height;
    private final int cellWidth;
    private final int cellHeight;
    private final int cell;

    public boolean showDominant = false;
    private final Simulation simulation;

    public SimulationPanel(WorldMap map, Simulation simulation) {

        this.map = map;
        this.simulation = simulation;
        this.cellWidth = MAX_SCREEN_WIDTH/map.width;
        if(cellWidth < MIN_CELL){
            throw new IllegalArgumentException("Too big map width entered: " + map.width);
        }
        this.cellHeight = MAX_SCREEN_HEIGHT/map.height;
        if(cellHeight < MIN_CELL){
            throw new IllegalArgumentException("Too big map height entered: " + map.height);
        }

        this.cell = min(cellWidth, cellHeight);

        this.width = cell * map.width;
        this.height = cell * map.height;

        setPreferredSize(new Dimension(width, height));

        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;

        g.setPaint(new Color(190, 241, 111));

        g.fillRect(BEGIN_COORDINATES, BEGIN_COORDINATES, map.width * cell, map.height * cell);

        g.setPaint(new Color(84, 172, 84));
        g.fillRect(map.lowerLeftJungle.x * cell + BEGIN_COORDINATES, map.lowerLeftJungle.y * cell + BEGIN_COORDINATES,
                map.jungleWidth * cell, map.jungleHeight * cell);

        for(Plant plant : map.plants.values()){
            g.setColor(plant.toColor());
            g.fillRect(BEGIN_COORDINATES + (plant.getPosition().x) * cell, BEGIN_COORDINATES + (plant.getPosition().y) * cell,
                    cell, cell);
        }

        for (Animal animal : map.animals) {
            g.setColor(animal.toColor());
            g.fillRoundRect(BEGIN_COORDINATES + (animal.getPosition().x) * cell, BEGIN_COORDINATES + (animal.getPosition().y) * cell,
                    cell, cell, 20, 20);
        }

        if(showDominant){
            for(Animal dominant:simulation.getStatistics().getDominant()){
                g.setColor(new Color(2, 215, 248));
                g.fillRoundRect(BEGIN_COORDINATES + (dominant.getPosition().x) * cell, BEGIN_COORDINATES + (dominant.getPosition().y) * cell,
                        cell, cell, 20, 20);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();
        Vector2d mapPosition = new Vector2d(x/cell, y/ cell);
        if(map.objectAt(mapPosition) instanceof MapCell) {
            Animal animal = ((MapCell) map.objectAt(mapPosition)).first();
            simulation.animalInfoPanel.updateText("This animal's genotype: " + animal.getGenes().toString());
            InputDialog dialogWindow = new InputDialog();
            String erasNumber = dialogWindow.showWindow("How many eras would you like to track history?");
            simulation.initAnimalHistory(new AnimalHistory(animal, Integer.parseInt(erasNumber), simulation.era, simulation));
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
}
