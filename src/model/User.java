package model;

import javax.swing.*;
import java.util.*;

public class User extends Observable {
    String username;
    ArrayList<WeatherStation> favorites = new ArrayList<>();
    ArrayList<JFrame> openWindows = new ArrayList<>();

    /**
   * This method is the set the username for the user.
   * @param newUsername is the username the user has entered.
   */
    public void setUsername(String newUsername) {
        username = newUsername;
    }

    /**
   * This method is for adding the stations to the user's 
   * favourite list.
   * @param newFavorite is the station that the user has entered and
   * wishes to add to the list
   * @see WeatherStation
   */
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

    public void setOpenWindows(ArrayList<JFrame> openWindows) {
        this.openWindows = openWindows;
    }

    //function to find a specific weatherStation object within the arraylist of a user
    public WeatherStation findWeatherStation(String stationName){
        for(WeatherStation w: favorites){
            if(w.getName().equals(stationName))
                return w;
        }

        return null;
    }
}
