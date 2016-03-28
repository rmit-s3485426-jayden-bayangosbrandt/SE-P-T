package view;

import model.Model;
import model.WeatherStation;

import javax.swing.*;

/**
 * Created by Jayden on 28/03/2016.
 */
public class Window {

    private Model model = Model.getInstance();

    public Window(){
        MainWindow window = new MainWindow();
        model.setMainWindow(window);
        WeatherStation weatherStation = new WeatherStation();
        model.setWeatherStation(weatherStation);
    }
}
