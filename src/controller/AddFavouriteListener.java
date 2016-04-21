package controller;

import model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jayden on 8/04/2016.
 */
public class AddFavouriteListener implements ActionListener {

    private JFrame frame;
    private Model model = Model.getInstance();

    public AddFavouriteListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "Added to Favourites!");
        final String selected = model.getStationUrl();
        model.setStationUrl(selected);
        // Get current user and add favourite to list
        model.addCurrentFavorite(selected);
        System.out.println("Fave count: "+ model.getCurrent().getFavorites().size());

    }
}
