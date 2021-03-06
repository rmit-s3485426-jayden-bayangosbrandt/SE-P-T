package controller;

import model.Model;
import model.WeatherObject;
import view.ChartWindow;
import view.GraphWindow;

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
            if(model.checkWindow(list.getSelectedValue().toString())) {
                for(JFrame window: model.getCurrent().findWindowsSet(list.getSelectedValue().toString())){
                    if(window instanceof ChartWindow){
                        ((ChartWindow) window).updateTable();
                        ((ChartWindow) window).relaunch();
                    }
                    if(window instanceof GraphWindow){
                        ((GraphWindow) window).updateGraph();
                        ((GraphWindow) window).relaunch();
                    }
                }
            }
            else{
                new ChartWindow(list.getSelectedValue().toString());
                new GraphWindow(list.getSelectedValue().toString());
            }

            model.getMainWindow().reposition();

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
