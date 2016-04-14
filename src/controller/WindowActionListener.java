package controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Jayden on 14/04/2016.
 */
public class WindowActionListener implements WindowListener {

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        //Save to disk
        JSONFileWrite file = new JSONFileWrite();
        if(file.writeFile()){
            System.out.println("Success!");
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
