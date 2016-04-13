package controller;

import model.Model;
import model.WeatherStation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by YungYung on 9/04/2016.
 */
public class RegisterListener implements ActionListener {

    private JTextField field;
    private Model model = Model.getInstance();

    public RegisterListener(JTextField textField){ this.field = textField;}

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(field, "Registered!");
        String user = field.getText();
        model.addUser(user);
        model.setCurrentUser(user);
        model.setEnter(true);
        model.closeWelcome();
    }
}
