package model;

import view.ChartWindow;
import view.GraphWindow;

import javax.swing.*;
import java.util.*;
import java.util.regex.Pattern;

public class User extends Observable {
    String username;
    ArrayList<WeatherStation> favorites = new ArrayList<>();
    ArrayList<JFrame> openWindows = new ArrayList<>();

    public void setUsername(String newUsername) {
        username = newUsername;
    }

    public void addFavorite(WeatherStation newFavorite)
    {
        favorites.add(newFavorite);
        setChanged();
        notifyObservers();
        clearChanged();
    }

    //function to set up favorites of a user, used when loading data from a file
    public void setFavorite(ArrayList<WeatherStation> stations){
        this.favorites = stations;
    }

    public WeatherStation getFavourite(int index){
        return favorites.get(index);
    }

    public ArrayList<WeatherStation> getFavorites(){
        return favorites;
    }

    public String getUsername(){
        return username;
    }

    //function that returns an array of strings containing all favorites of a user
    public String[] getFavouriteList(){

        String[] localList = new String[getFavorites().size()];

        for(int i = 0; i < getFavorites().size(); i++){
            localList[i] = getFavorites().get(i).getName();
        }

        return localList;
    }

    public ArrayList<JFrame> getOpenWindows() {
        return openWindows;
    }

    public ArrayList<JFrame> findWindowsSet(String search) {
        ArrayList<JFrame> returnValue = new ArrayList<JFrame>();
        String regex = search;
        regex = regex.concat(".*");
        for(JFrame window : openWindows){
            if(window instanceof ChartWindow){
                if(Pattern.matches(regex,((ChartWindow) window).getTitleValue()))
                    returnValue.add(window);
            }
            if(window instanceof GraphWindow){
                if(Pattern.matches(regex,((GraphWindow) window).getTitleValue()))
                    returnValue.add(window);
            }
        }
        return returnValue;
    }

    public void setOpenWindows(ArrayList<JFrame> openWindows) {
        this.openWindows = openWindows;
    }

    public void addOpenWindow(JFrame window) {this.openWindows.add(window);}

    //function to find a specific weatherStation object within the arraylist of a user
    public WeatherStation findWeatherStation(String stationName){
        for(WeatherStation w: favorites){
            if(w.getName().equals(stationName))
                return w;
        }

        return null;
    }
}
