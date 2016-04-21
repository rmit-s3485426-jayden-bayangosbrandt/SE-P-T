package controller;

import model.Model;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Created by YungYung on 13/04/2016.
 */
public class SearchListener implements DocumentListener {

    private JTextField field;
    private JList list;
    private Model model = Model.getInstance();

    public SearchListener(JTextField textField, JList localList){ this.field = textField; this.list = localList;}

    // As the search text value changes, the selected user changes accordingly
    @Override
    public void insertUpdate(DocumentEvent e) {

        list.setSelectedValue(field.getText(),true);
        String user = field.getText();

        // Search through if the user exists; if it does, set Current user
        for(int i = 0; i< model.getUserList().size(); i++) {

            if(user == model.getUserList().get(i).getUsername()){
                model.setCurrentUser(user);
            }
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}