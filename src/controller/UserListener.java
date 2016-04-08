package controller;

import model.Model;
import view.WelcomeWindow;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * Created by YungYung on 8/04/2016.
 */
public class UserListener implements ListSelectionListener{

    private JList userList;
    private Model model = Model.getInstance();

    public UserListener(JList userList){
        this.userList = userList;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            System.out.println("SELECTED");
            String value = (String) userList.getSelectedValue();
            model.setCurrentUser(value);
            model.closeWelcome();
        }
    }
}
