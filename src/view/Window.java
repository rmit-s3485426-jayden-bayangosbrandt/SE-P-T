package view;

import javax.swing.*;
import java.awt.*;


public class Window extends JFrame {

    public Window() throws HeadlessException {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 3));

        this.setContentPane(mainPanel);
        mainPanel.add(new JLabel("Test"));
        this.setSize(500, 500);
        this.setResizable(false);
        this.setVisible(true);
    }
}
