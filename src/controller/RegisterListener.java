package controller;

import model.Model;
import model.WeatherStation;
import view.MainWindow;

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

        String user = field.getText();
        boolean successRegister = true;
        boolean alexYoda = false;
        boolean jaydenYoda = false;

        if(user.compareTo("Jayden") == 0){
            jaydenYoda = true;
            user = "Master Jayden Yoda";
        }
        else if(user.compareTo("Alex") == 0){
            alexYoda = true;
            user = "Master Alex Yoda";
        }

        // Check username content
        if(user == "Type your name here" || user.length() > 20 || user == ""){
            JOptionPane.showMessageDialog(field, "Please Type a proper username");
            successRegister = false;
        }

        // Check if Registering New user name is already taken
        for(int i = 0; i< model.getUserList().size(); i++) {
            if(user.contentEquals(model.getUserList().get(i).getUsername())){
                JOptionPane.showMessageDialog(field, "Sorry! Existing User! Please choose another name.");
                successRegister = false;
                break;
            }
        }

        // Continue register success
        if(successRegister) {
            if(jaydenYoda){
                JOptionPane.showMessageDialog(field, "Registered Master Jayden Yoda!");
            }
            else if(alexYoda){
                JOptionPane.showMessageDialog(field, "Registered Master Alex Yoda!");
            }
            else {
                JOptionPane.showMessageDialog(field, "Registered!");
            }

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
