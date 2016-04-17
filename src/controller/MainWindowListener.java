package controller;

import model.Model;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
/**
 * Created by Jayden on 14/04/2016.
 */
public class MainWindowListener implements WindowListener {

    private Model model = Model.getInstance();
    private JFrame frame;

    public MainWindowListener(JFrame frame) {
        this.frame = frame;
    }

    public MainWindowListener() {
    }

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
        System.exit(0);

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
