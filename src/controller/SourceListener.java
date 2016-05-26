package controller;

import model.Model;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jayden on 26/05/2016.
 */
public class SourceListener implements ItemListener {

    private Model model = Model.getInstance();
    private final static Logger LOGGER = Logger.getLogger(ColumnCheckedListener.class.getName());

    @Override
    public void itemStateChanged(ItemEvent e) {
        JRadioButton radioButton = (JRadioButton) e.getSource();
        if (radioButton.isSelected()) {
            LOGGER.info(radioButton.getText() + " Selected");

            model.setForcastSource(radioButton.getText());
        }
    }
}
