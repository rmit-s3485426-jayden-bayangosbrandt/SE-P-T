package controller;

import model.Model;
import model.WeatherStation;
import view.MainWindow;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by Jayden on 7/04/2016.
 */
public class FavouriteListener implements ListSelectionListener {

    private JList list;
    private JFrame frame;
    private Model model = Model.getInstance();

    public FavouriteListener(JList list, JFrame frame){
        this.list = list;
        this.frame = frame;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(e.getValueIsAdjusting() == false){
            System.out.println(list.getSelectedIndex());

            JOptionPane.showMessageDialog(frame, "Fuck trees!!!");
            // Feed to chart object
            model.getCurrent().getFavourite(e.getFirstIndex());

        }
    }
}
