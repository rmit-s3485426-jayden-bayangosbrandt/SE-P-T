package controller;

import model.Model;
import view.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by YungYung on 9/04/2016.
 */
public class GoListener implements ActionListener {

    private JTextField searchField;
    private JList usersField;
    private Model model = Model.getInstance();

    public GoListener(JTextField search, JList users){
        this.searchField = search;
        this.usersField = users;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(model.getCurrent() != null) {
            String user = (String) usersField.getSelectedValue();
            model.addUser(user);
            model.setCurrentUser(user);
//            model.setEnter(true);
            model.closeWelcome();

            MainWindow window = new MainWindow();
            model.setMainWindow(window);
            model.setArea();
        }
    }
}
