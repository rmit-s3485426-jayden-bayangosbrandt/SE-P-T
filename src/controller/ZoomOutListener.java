package controller;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Logger;

/**
 * Created by YungYung on 28/05/2016.
 */

public class ZoomOutListener implements ItemListener {

    private final static Logger LOGGER = Logger.getLogger(ZoomOutListener.class.getName());

    @Override
    public void itemStateChanged(ItemEvent e) {

        JCheckBox zoomOut = (JCheckBox) e.getItem();

        if (zoomOut.isSelected()) {
            LOGGER.info(zoomOut.getText()+ " Selected");
        }
    }
}


