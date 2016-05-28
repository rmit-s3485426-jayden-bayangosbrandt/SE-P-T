package controller;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Logger;

/**
 * Created by YungYung on 28/05/2016.
 */

public class ZoomInListener implements ItemListener {

    private final static Logger LOGGER = Logger.getLogger(ZoomInListener.class.getName());

    @Override
    public void itemStateChanged(ItemEvent e) {

        JCheckBox zoomIn = (JCheckBox) e.getItem();

        if (zoomIn.isSelected()) {
            LOGGER.info(zoomIn.getText()+ " Selected");
        }
    }
}


