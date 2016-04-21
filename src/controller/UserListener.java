package controller;

import model.Model;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * Created by YungYung on 8/04/2016.
 */
public class UserListener implements ListSelectionListener{

    private JList userList;
    private JTextField searchField;
    private Model model = Model.getInstance();

    public UserListener(JList userList, JTextField search){
        this.userList = userList;
        this.searchField = search;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {

            String value = (String) userList.getSelectedValue();
            model.setCurrentUser(value);
        }
    }
}
