package model;

import java.util.*;

public class User extends Observable {
    String username;
    ArrayList<WeatherStation> favorites = new ArrayList<>();

    public User(String newUsername)
    {
        username = newUsername;
    }

    public void addFavorite(WeatherStation newFavorite)
    {
        favorites.add(newFavorite);
        notifyObservers();
    }

    public WeatherStation getFavourite(int index){
        return favorites.get(index);
    }
}
