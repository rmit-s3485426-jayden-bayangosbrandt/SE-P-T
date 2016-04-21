package controller;

import model.Model;
import view.ChartWindow;
import view.GraphWindow;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Alex on 21/04/2016.
 */
public class ChartGraphListener implements WindowListener {

    private Model model = Model.getInstance();
    private JFrame frame;

    public ChartGraphListener(JFrame frame) {
        this.frame = frame;
    }

    public ChartGraphListener() {
    }

    @Override
    public void windowOpened(WindowEvent e) {
        if(frame instanceof ChartWindow)
            ( (ChartWindow) frame).setOpen(true);
        else
            ((GraphWindow) frame).setOpen(true);

    }

    @Override
    public void windowClosing(WindowEvent e) {
        if(frame instanceof ChartWindow)
        {
            ( (ChartWindow) frame).setOpen(false);
            ( (ChartWindow) frame).setLocationValue(
                    (int)(Math.round(frame.getLocation().getX())),
                    (int)(Math.round(frame.getLocation().getY())));
        }

        else {
            ((GraphWindow) frame).setOpen(false);
            ( (GraphWindow) frame).setLocationValue(
                    (int)(Math.round(frame.getLocation().getX())),
                    (int)(Math.round(frame.getLocation().getY())));
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
