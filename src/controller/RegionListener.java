package controller;

import model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jayden on 28/03/2016.
 */
public class RegionListener implements ActionListener {

    private JComboBox comboBox;
    private Model model = Model.getInstance();

    public RegionListener(JComboBox comboBox){
        this.comboBox = comboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String value = (String) comboBox.getSelectedItem();
                model.changeStation(value);
                model.setStationEnabled(true);
            }
        }).start();
    }


}
