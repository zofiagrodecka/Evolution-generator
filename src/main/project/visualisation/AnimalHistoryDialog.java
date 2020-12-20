package project.visualisation;

import javax.swing.*;

public class AnimalHistoryDialog {

    JFrame frame = new JFrame();

    public void showWindow(String message){
        JOptionPane.showMessageDialog(frame, message,
                "Animal statistics", JOptionPane.PLAIN_MESSAGE);

    }
}
