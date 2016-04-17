package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.Model;
import model.User;
import model.WeatherObject;
import org.json.simple.JSONArray;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by Jayden on 14/04/2016.
 */
public class JSONFileWrite {

    private Model model = Model.getInstance();
    private Gson gson;
    private JsonReader reader;

    public boolean writeFile(){
        ArrayList<User> users = model.getUserList();
//        JSONObject object = model.getStorage();
//        object.put("users", users);
//        gson = new Gson();
//        Type type = new TypeToken<List<User>>(){}.getType();
//        String json = gson.toJson(users, type);
//
//        for(User u: users){
//            System.out.println(gson.toJson(u,type));
//        }
//        String json = gson.toJson(users, type);
//        String json = gson.toJson(users);
//        System.out.println(json);

        JSONArray userGroup = new JSONArray();

        for(User u: users){
            JSONObject userJSON = new JSONObject();
            JSONArray weatherStations = new JSONArray();
            for(String wo: u.getFavouriteList()){
                JSONObject station = new JSONObject();
                station.put("name", wo);
                station.put("stationUrl", u.findWeatherStation(wo).getStationUrl());
                weatherStations.add(station);
            }
            userJSON.put("favorites",weatherStations);
            userJSON.put("username",u.getUsername());
            userGroup.add(userJSON);
        }

        System.out.println(userGroup);

        try (FileWriter file = new FileWriter("file1.txt")) {
//            System.out.println(gson.toJson(users));
            file.write(userGroup.toString());
            System.out.println("Successfully Copied JSON Object to File...");
//            System.out.println("\nJSON Object: " + object);
            return true;
        }

        catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<User> loadFile() {
//        JSONParser parser = new JSONParser();

        ArrayList<User> users = new ArrayList<User>() ;
        try{
            ClassLoader classLoader = getClass().getClassLoader();
//            File file = new File(classLoader.getResource("file/file1.txt").getFile());
//            Scanner scanner = new Scanner(file);
            BufferedReader reader = new BufferedReader(new FileReader("file1.txt"));
            String usersJSON = reader.readLine();
//            String usersJSON = scanner.nextLine();
            JSONParser parser = new JSONParser();
            Object userObject = parser.parse(usersJSON);
            JSONArray userJArray = (JSONArray) userObject;
            for(int i=0; i<userJArray.size(); i++){
                User user = new User();
                user.setUsername(getUsername(userJArray.get(i).toString()));
                user.setFavorite(getFavorites(userJArray.get(i).toString()));
//                System.out.println(userJArray.get(i).getString("username"));
//                user.setUsername(userJArray.get(i).("username"));
                users.add(user);
            }
        }
        catch(Exception e){
            try{
                PrintWriter writer = new PrintWriter("file1.txt","UTF-8");
            }
            catch(Exception f){
                f.printStackTrace();
            }

        }

//        gson = new Gson();
//        try {
//            reader = new JsonReader(new FileReader("file1.txt"));
//            ArrayList<User> users = gson.fromJson(reader, ArrayList.class);
//
////            JSONObject jsonObject = (JSONObject) obj;
////            ArrayList<User> users = (ArrayList<User>) jsonObject.get("users");
//            System.out.println(users.size());
//            return users;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return users;
    }

    public String getUsername(String line){
        String separator = "{}[:],\"";
        StringTokenizer token = new StringTokenizer(line, separator);
        String tokens;
        tokens = token.nextToken();
        while(token.hasMoreTokens() && !tokens.equals("username")){
            tokens = token.nextToken();
        }
        return token.nextToken();
    }

    public HashMap<String, String> getFavorites(String line){
        String separator = "{}[:],\"";
        StringTokenizer token = new StringTokenizer(line, separator);
        String tokens, name, url;
        HashMap<String, String> favorites = new HashMap<String, String>();
        tokens = token.nextToken(); //favorites
        tokens = token.nextToken(); //either data or username
        while(token.hasMoreTokens() && !tokens.equals("username")) {
            name = token.nextToken();
            token.nextToken(); //"stationUrl"
            url = token.nextToken(); //http
            url = url.concat(":");
            url = url.concat(token.nextToken());
            url = url.replace("\\","" );
            favorites.put(name, url);
            tokens = token.nextToken();
        }
        return favorites;
    }

}
