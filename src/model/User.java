package model;

import java.util.*;

public class User {
    String username;
    ArrayList<WeatherStation> favorites;

    public User(String newUsername)
    {
        username = newUsername;
    }

    public void addFavorite(WeatherStation newFavorite)
    {
        favorites.add(newFavorite);
    }
}
