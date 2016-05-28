package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.logging.Logger;

/**
 * Created by YungYung on 28/05/2016.
 */

public class ZoomInListener implements ActionListener {

    private final static Logger LOGGER = Logger.getLogger(ZoomInListener.class.getName());

    @Override
    public void actionPerformed(ActionEvent e) {

            LOGGER.info("Zoom In Selected");
    }
}


