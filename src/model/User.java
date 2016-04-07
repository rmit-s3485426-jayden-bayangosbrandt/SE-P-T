package model;

import java.util.*;

public class User {
    String username;
    ArrayList<WeatherStation> favorites;

    public void setUsername(String newUsername) {
        username = newUsername;
    }

    public void addFavorite(WeatherStation newFavorite)
    {
        favorites.add(newFavorite);
    }

    public String getUsername(){
        return username;
    }
}
