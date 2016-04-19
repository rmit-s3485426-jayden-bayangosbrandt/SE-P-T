package controller;

import model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alex on 31/03/2016.
 */
public class AreaListener implements ActionListener {

    private JComboBox comboBox;
    private Model model = Model.getInstance();

    public AreaListener(JComboBox comboBox){
    this.comboBox = comboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    String value = (String) comboBox.getSelectedItem();
        System.out.println(value);
        model.changeRegionUrl(value);
        model.setRegionEnabled(true);
//    model.searchRegionWeatherStation(value);
//    System.out.println(value);
    }
}
