package view;

import model.Model;

/**
 * Created by Jayden on 28/03/2016.
 */
public class Window {

    private Model model = Model.getInstance();

    public Window(){

        model.setData();

        WelcomeWindow welcome = new WelcomeWindow();
        model.setWelcomeWindow(welcome);
    }
}
