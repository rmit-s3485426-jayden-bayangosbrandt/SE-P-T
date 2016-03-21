package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jayden on 11/03/2016.
 */
public class main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 3));

        frame.setContentPane(mainPanel);
//        frame.add(new J)
        mainPanel.add(new JLabel("Test"));
        frame.setSize(500, 500);
        frame.setResizable(false);
//        frame.pack();
        frame.setVisible(true);
    }
}
