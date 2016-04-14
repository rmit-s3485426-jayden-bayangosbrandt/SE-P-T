package controller;

import model.Model;
import model.WeatherObject;
import model.WeatherStation;
import view.ChartWindow;
import view.MainWindow;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

/**
 * Created by Jayden on 7/04/2016.
 */
public class FavouriteListener implements ListSelectionListener {

    private JList list;
    private JFrame frame;
    private Model model = Model.getInstance();
    private boolean clearSelection = false;

    public FavouriteListener(JList list, JFrame frame){
        this.list = list;
        this.frame = frame;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting() && !clearSelection) {
            System.out.println("Selected index: " + list.getSelectedIndex());

            // Feed to chart object
//            model.getCurrent().getFavourite(list.getSelectedIndex());
            new ChartWindow(model.getTable());
            // Ensure event isn't fired for clearing selection
            clearSelection = true;
            list.clearSelection();

        }
        clearSelection = false;

    }

    private ArrayList<WeatherObject> sampleData(){
        ArrayList<WeatherObject> samples = new ArrayList<>();
        String test = "Test";
        int i = 0;
        while (i<30) {
            samples.add(new WeatherObject(String.valueOf(i), test, test,
                    test, test, test,
                    test, test, test,
                    test, test, test,
                    test, test));
            i++;
        }
        return samples;
    }
}
