package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by YungYung on 16/04/2016.
 */
public class GraphWindow extends JFrame {

    public GraphWindow(){

        JPanel graphWindow = new JPanel();
        setTitle("Temperature Graph");
        setContentPane(graphWindow);
        setSize(new Dimension(500, 200));
        setVisible(true);
        setLocationRelativeTo(null);
        setLocation(getX()+200, 450);

    }
}
