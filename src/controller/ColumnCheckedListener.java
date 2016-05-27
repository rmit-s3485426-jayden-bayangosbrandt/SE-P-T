package controller;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Logger;

/**
 * Created by Jayden on 26/05/2016.
 */
public class ColumnCheckedListener implements ItemListener {

    private final static Logger LOGGER = Logger.getLogger(ColumnCheckedListener.class.getName());


    @Override
    public void itemStateChanged(ItemEvent e) {
        JCheckBox checkBox = (JCheckBox) e.getItem();

        if (checkBox.isSelected()) {
            LOGGER.info(checkBox.getText()+ " Selected");
        }
    }
}