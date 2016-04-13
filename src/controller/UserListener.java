package controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * Created by YungYung on 8/04/2016.
 */
public class UserListener implements ListSelectionListener{

    private JList userList;
    private JTextField searchField;

    public UserListener(JList userList, JTextField search){
        this.userList = userList;
        this.searchField = search;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {

            // ** CHANGES SEARCH TEXT WHEN USER IS CHOSEN FROM LIST **
            // Really want to implement this but keeps getting "Attempting to mute notification" error
            // searchField.setText((String) userList.getSelectedValue());
            /**
            String value = (String) userList.getSelectedValue();
            model.setCurrentUser(value);
            model.closeWelcome();
             **/
        }
    }
}
