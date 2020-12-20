package project.visualisation;

import javax.swing.*;
import java.awt.*;

public class StatisticsPanel extends JPanel {

    public final int height;
    public final int width;
    private final JLabel statisticsLabel = new JLabel();

    public StatisticsPanel(int width, int height, String text){

        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        statisticsLabel.setText(text);
        statisticsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statisticsLabel.setOpaque(true);
        statisticsLabel.setBackground(new Color(255, 255, 255));
        this.add(statisticsLabel);
    }

    public void updateText(String text){
        statisticsLabel.setText(text);
        statisticsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    }

}
