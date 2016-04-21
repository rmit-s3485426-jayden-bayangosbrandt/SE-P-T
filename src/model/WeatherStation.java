package model;


import java.util.ArrayList;
import java.util.HashMap;



public class WeatherStation {

    private ArrayList stationArray = new ArrayList();
    private ArrayList regionArray = new ArrayList();
    private ArrayList regionID = new ArrayList();
    String currentID;
    private String name;
    private HashMap<String,String> history = new HashMap<String,String>();
    private String stationUrl;

    private Model model = Model.getInstance();

    public WeatherStation(String name, String stationUrl) {
        this.name = name;
        this.stationUrl = stationUrl;
    }

    public void searchRegionArray(String query){

        for(int i = 0 ; i < regionArray.size() + 1 ; i++)
        {
            if( query == regionArray.get(i))
            {
                currentID = (String) regionID.get(i + 1);
            }
        }
    }

    public HashMap<String,String> getHistory() {
        return history;
    }

    public void setHistory(HashMap<String,String> history) {
        this.history = history;
    }

    public void addHistory(String key, String value){history.put(key,value);}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStationUrl(){return stationUrl;}
}


