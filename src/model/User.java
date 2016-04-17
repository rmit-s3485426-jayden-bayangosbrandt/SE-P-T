package model;

import javax.swing.*;
import java.util.*;

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

    public void setFavorite(HashMap<String,String> setFavorites){
        Set set = setFavorites.entrySet();
        Iterator iterate = set.iterator();
        while(iterate.hasNext()){
            Map.Entry station = (Map.Entry)iterate.next();
            favorites.add(new WeatherStation(station.getKey().toString()
                    ,station.getValue().toString()));
        }

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

    public WeatherStation findWeatherStation(String stationName){
        for(WeatherStation w: favorites){
            if(w.getName().equals(stationName))
                return w;
        }

        return null;
    }
}
