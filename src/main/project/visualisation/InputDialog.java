package project.visualisation;

import javax.swing.*;

public class InputDialog {

    JFrame inputFrame = new JFrame();

    public String showWindow(String message){

        String n = JOptionPane.showInputDialog(
                inputFrame,
                message,
                "Animal ststistics question",
                JOptionPane.QUESTION_MESSAGE
        );

        return n;
    }
}
