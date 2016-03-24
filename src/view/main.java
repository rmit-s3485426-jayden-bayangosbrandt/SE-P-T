package view;

import model.Model;
import sun.applet.Main;

import javax.swing.*;


public class main {

    private Model model = Model.getInstance();

    public static void main(String[] args) {
        JFrame frame = new MainWindow();
//        JFrame frame = new WelcomeWindow();
    }
}
