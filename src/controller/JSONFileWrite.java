package controller;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import model.Model;
import model.User;
import model.WeatherStation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Jayden on 14/04/2016.
 */
public class JSONFileWrite {

    private Model model = Model.getInstance();
    private Gson gson;
    private JsonReader reader;

    public boolean writeFile(){
        ArrayList<User> users = model.getUserList();

        JSONArray userGroup = new JSONArray();

        //printing all users into a file
        for(User u: users){
            JSONObject userJSON = new JSONObject();
            JSONArray weatherStations = new JSONArray();

            //adding weatherstations of each user to a JSONArray and adding it to userJSON
            for(String wo: u.getFavouriteList()){
                JSONObject station = new JSONObject();
                JSONArray historyArray = new JSONArray();
                HashMap<String,String> histories = u.findWeatherStation(wo).getHistory();
                station.put("name", wo);
                station.put("stationUrl", u.findWeatherStation(wo).getStationUrl());

                //writes the temperature history of a station
                Set historySet = histories.entrySet();
                Iterator iterateHistory = historySet.iterator();
                while(iterateHistory.hasNext()){
                    Map.Entry temp = (Map.Entry)iterateHistory.next();
                    JSONObject history = new JSONObject();
                    history.put(temp.getKey().toString(), temp.getValue().toString());
                    historyArray.add(history);
                }
                station.put("history", historyArray);
                weatherStations.add(station);
            }
            //adding username and favorites attributes to userJSON
            userJSON.put("favorites",weatherStations);
            userJSON.put("username",u.getUsername());
            //adding each user JSON to a bigger JSONArray
            userGroup.add(userJSON);
        }

        //writing it to a file
        try (FileWriter file = new FileWriter("file1.txt")) {
            file.write(userGroup.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            return true;
        }

        //printing out error if file not found
        catch (IOException e){
            JOptionPane.showMessageDialog(new JTextField(), "ERROR FILE NOT FOUND");
        }

        return false;
    }

    public ArrayList<User> loadFile() {
        ArrayList<User> users = new ArrayList<User>() ;
        try{
            //opening up the file to read data
            ClassLoader classLoader = getClass().getClassLoader();

            //reading the line from file
            BufferedReader reader = new BufferedReader(new FileReader("file1.txt"));
            String usersJSON = reader.readLine();

            //parsing line into a JSON object
            JSONParser parser = new JSONParser();
            Object userObject = parser.parse(usersJSON);
            JSONArray userJArray = (JSONArray) userObject;

            //for each element in the array, create a new user and assign the username and favorites
            for(int i=0; i<userJArray.size(); i++){
                User user = new User();
                user.setUsername(getUsername(userJArray.get(i).toString()));
                user.setFavorite(getFavorites(userJArray.get(i).toString()));
                users.add(user);
            }
        }

        //if there is no file to write into, create a new file
        catch(IOException e){
            try{
                PrintWriter writer = new PrintWriter("file1.txt","UTF-8");
            }
            catch(Exception f){
                f.printStackTrace();
            }

        }
        catch (ParseException e){
            JOptionPane.showMessageDialog(new JTextField(), "ERROR FILE CONTENT IN WRONG FORMAT");
        }

        return users;
    }

    //function to get username from a json format string
    public String getUsername(String line){
        String separator = "{}[:],\"";
        StringTokenizer token = new StringTokenizer(line, separator);
        String tokens;

        //tokenizing the string, and when it finds a token with "username" as its value,
        // the next value will be the actual username, so return that value
        tokens = token.nextToken();
        while(token.hasMoreTokens() && !tokens.equals("username")){
            tokens = token.nextToken();
        }
        return token.nextToken();
    }

    //function to get stations from a json format string
    //return value HashMap<String, String> with stationname as key and the url as value
    public ArrayList<WeatherStation> getFavorites(String line){
        ArrayList<WeatherStation> stations = new ArrayList<WeatherStation>();

        //getting today's date to be compared with history, to determine how old a data is
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        int todayDate = Integer.parseInt(sdf.format(today));
        int date;

        String separator = "{}[:],\"";
        String pattern9am = ".*-9";
        String pattern3pm = ".*-3";
        StringTokenizer token = new StringTokenizer(line, separator);
        String tokens, name, url;
        HashMap<String, String> favorites = new HashMap<String, String>();
        tokens = token.nextToken(); //favorites
        tokens = token.nextToken(); //either data or username

        //getting all stations with a termination condition when the token found is "username"
        while(token.hasMoreTokens() && !tokens.equals("username")) {
            name = token.nextToken();
            token.nextToken(); //"stationUrl"
            url = token.nextToken(); //http
            //modifying the url so it's a valid url since the character : will be excluded when tokenizing
            url = url.concat(":");
            url = url.concat(token.nextToken());
            url = url.replace("\\","" );
            WeatherStation station = new WeatherStation(name,url);

            tokens = token.nextToken();
            //if the station has a history, reads every single one
            if(tokens.equals("history")) {
                HashMap<String,String> histories = new HashMap<String,String>(); //key is date-time (e.g. 20-9), value is temperature
                String day;
                tokens = token.nextToken();
                //loop until all temperature data has been read, the indicator is either it goes to the next station or username
                while(token.hasMoreTokens() && (!tokens.equals("name") || !tokens.equals("username"))){
                    //checking whether the data is more than a week old, if yes, then it is not read / deleted
                    if(Pattern.matches(pattern9am,tokens))
                    {
                        date = Integer.parseInt(tokens.replace("-9",""));
                        if(todayDate - date > 7){
                            token.nextToken();
                            tokens = token.nextToken();
                            continue;
                        }
                    }
                    if(Pattern.matches(pattern3pm,tokens)) {
                        date = Integer.parseInt(tokens.replace("-3",""));
                        if(todayDate - date > 7){
                            token.nextToken();
                            tokens = token.nextToken();
                            continue;
                        }
                    }
                    day = tokens;
                    tokens = token.nextToken();
                    histories.put(day,tokens);
                    tokens = token.nextToken();
                }
                station.setHistory(histories);
            }
            stations.add(station);
        }
        return stations;
    }


}
