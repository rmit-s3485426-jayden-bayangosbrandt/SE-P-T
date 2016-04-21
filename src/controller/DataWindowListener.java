package controller;

import model.Model;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Jayden on 17/04/2016.
 */
public class DataWindowListener implements WindowListener {

    private JFrame frame;
    private Model model = Model.getInstance();

    public DataWindowListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        if(!model.getCurrent().getOpenWindows().contains(frame)) {
            model.addOpenWindow(frame);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
