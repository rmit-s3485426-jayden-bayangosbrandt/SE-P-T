package controller;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.oracle.javafx.jmx.json.JSONReader;
import model.Model;
import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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
        gson = new Gson();
        try (FileWriter file = new FileWriter("file1.txt")) {
            file.write(gson.toJson(users));
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
        gson = new Gson();
        try {
            reader = new JsonReader(new FileReader("file1.txt"));
            ArrayList<User> users = gson.fromJson(reader, ArrayList.class);

//            JSONObject jsonObject = (JSONObject) obj;
//            ArrayList<User> users = (ArrayList<User>) jsonObject.get("users");
            System.out.println(users.size());
            return users;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
