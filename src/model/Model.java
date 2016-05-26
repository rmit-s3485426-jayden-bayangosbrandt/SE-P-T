package model;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.github.dvdme.ForecastIOLib.ForecastIO;
import controller.JSONFileWrite;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import view.ChartWindow;
import view.GraphWindow;
import view.MainWindow;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import view.WelcomeWindow;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.*;

public class Model {

    private static Model ourInstance = new Model();
    private WelcomeWindow welcomeWindow;
    private MainWindow mainWindow;
    private WeatherStation weatherStation;
    private String regionUrl;
    private String stationUrl;
    private ArrayList<User> users = new ArrayList<>();
    private User currentUser;
    private ArrayList<ChartWindow> chartWindows= new ArrayList<ChartWindow>();
    private ArrayList<GraphWindow> graphWindows= new ArrayList<GraphWindow>();
    int indicator = 0;
    Logger theLogger = Logger.getLogger(Model.class.getName());

    public static Model getInstance() {
        return ourInstance;
    }

    private Model() {
    }

    public void setWelcomeWindow(WelcomeWindow window) {
        this.welcomeWindow = window;
    }

    public void setMainWindow(MainWindow window) {
        this.mainWindow = window;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }


    /**
     * This method checks to see whether or not there is already
     * a window open for the certain station that the user is
     * pulling up
     * @param station is the station name in a region
     * @return boolean to check if a window exists or not
     */
    public boolean checkWindow(String station){
        ArrayList<JFrame> windows = currentUser.getOpenWindows();
        String regex = station;
        regex = regex.concat(".*");
        String compare;
        for(JFrame window : windows){
            if(window instanceof ChartWindow)
                compare = ( (ChartWindow) window).getTitleValue();
            else
                compare = ( (GraphWindow) window).getTitleValue();
            if(Pattern.matches(regex,compare))
                return true;
        }
        return false;
    }


    /**
     * A method that adds a window for a station's information.
     * That is, it adds a chart window and a table window.
     * @param frame a jframe for the window
     */
    public void addOpenWindow(JFrame frame) {
        if(currentUser.getOpenWindows().size() < 2)
            currentUser.getOpenWindows().add(frame);
        int indicator =0;
        for(JFrame window: currentUser.getOpenWindows()){
            if(window instanceof ChartWindow)
                if(frame instanceof ChartWindow)
                    if(!((ChartWindow) window).getTitleValue().equals(((ChartWindow) frame).getTitleValue()))
                        indicator++;
            if(window instanceof GraphWindow)
                if(frame instanceof GraphWindow)
                    if(!((GraphWindow) window).getTitleValue().equals(((GraphWindow) frame).getTitleValue()))
                        indicator++;
        }
        if(indicator>0)
            currentUser.getOpenWindows().add(frame);
    }

    //function to keep track opened chartwindows
    public void addChartWindow(ChartWindow chartWindow) {
        boolean check = false;
        if(chartWindows.size()==0)
            check = true;
        for(ChartWindow chart : chartWindows){
            if(!chart.getTitleValue().equals(chartWindow.getTitleValue()))
                check = true;
        }
        if(check)
            chartWindows.add(chartWindow);
    }

    //function to keep track opened graphwindows
    public void addGraphWindow(GraphWindow graphWindow) {
        boolean check = false;
        if(graphWindows.size()==0)
            check = true;
        for(GraphWindow graph : graphWindows){
            if(!graph.getTitleValue().equals(graphWindow.getTitleValue()))
                check = true;
        }
        if(check)
            graphWindows.add(graphWindow);
    }

    public void removeOpenWindow(JFrame frame) {
        currentUser.getOpenWindows().remove(frame);
    }

    public void setWeatherStation(WeatherStation station) {
        this.weatherStation = station;
    }

    public void changeAreaDataset(String[] strings) {
        mainWindow.setAreaDataset(strings);
    }

    public void changeRegionDataset(String[] strings) {
        mainWindow.setRegionDataset(strings);
    }

    public void changeStationDataset(String[] strings) {
        mainWindow.setStationDataset(strings);
    }

    /**
   * Allows the user to refresh the window to add a station
   * to favourites.
   */
    public void refresh(){
        //resetting area combobox
        setArea();
        String[] empty = {""};
        //resetting region and station combobox to empty
        changeRegionDataset(empty);
        changeStationDataset(empty);
        //updating data in all chartWindow of user
        for(ChartWindow chart: chartWindows)
        {
            chart.updateTable();
            if(chart.getOpen())
                chart.relaunch();
        }
        //updating data in all graphWindow of user
        for(GraphWindow graph: graphWindows)
        {
            graph.updateGraph();
            if(graph.getOpen())
                graph.relaunch();
        }
    }


    public void setLblName(String name) {
        mainWindow.setLblName(name);
    }

    public void closeWelcome() {
        welcomeWindow.closeWindow();
    }

    public void setStationEnabled(boolean enabled) {
        // Set the state of station combobox
        mainWindow.setStationEnabled(enabled);
    }

    public void setRegionEnabled(boolean enabled) {
        // Set the state of region combobox
        mainWindow.setRegionEnabled(enabled);
    }

    //function to change the value of regionUrl
    public void changeRegionUrl(String area) {
        try
        {
            Element foundArea = findElement("http://www.bom.gov.au/catalogue/data-feeds.shtml", area);
            regionUrl = foundArea.attr("href");
            changeRegionDataset(searchRegionArray(foundArea.text(), regionUrl));
            regionUrl = "http://www.bom.gov.au".concat(regionUrl);
        }
        catch(IOException o)
        {
            JOptionPane.showMessageDialog(new JTextField(), "ERROR WEBSITE NOT FOUND");
            theLogger.log(Level.FINE,"The url " + regionUrl + "does not exist" );
        }
    }

    //function to change station combobox value according to the region
    public void changeStation(String region) {
        try{
            changeStationDataset(searchStationArray(region, regionUrl));
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(new JTextField(), "ERROR WEBSITE NOT FOUND");
            theLogger.log(Level.FINE,"The url " + regionUrl + "does not exist" );
        }

    }


    /**
   * This method sets the area that the user picks, done so by
   * going through the areas in Australia until there is an area
   * that matches the selected area.
   */
    public void setArea() {
        String url ="";
        try {
            String[] areas;
            ArrayList<String> ArrayListAreas = new ArrayList<String>();
            //connecting to the website and getting html using jsoup
            url = "http://www.bom.gov.au/catalogue/data-feeds.shtml";
            Document docArea = Jsoup.connect(url).get();
            //getting each table data that contains a link, which will be the areas name
            Elements table = docArea.getElementsByAttributeValue("width", "712");
            Elements tableData = table.select("tbody").select("a");
            String regex = "http.*";
            //for each link, if it has a valid url, add it to the arraylist
            for (Element area : tableData) {
                String areaText = area.text();
                if (Pattern.matches(regex, areaText))
                    continue;
                ArrayListAreas.add(areaText);
            }
            //converting arraylist to array of strings
            areas = new String[ArrayListAreas.size()];
            ArrayListAreas.toArray(areas);
            //calling functions to change value of area combobox
            changeAreaDataset(areas);
            theLogger.log(Level.INFO, "Area data obtained correctly");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JTextField(), "ERROR WEBSITE NOT FOUND");
            theLogger.log(Level.FINE,"The url " + url + "does not exist" );
        }


    }

    /**
   * This method is used to search the regions in a certain area
   * and hence adding each region into an array depending on the area.
   * <p>
   * JSON is used to retreive said data, through the url given and a loop
   * in order to check whether or not there are more regions inside the area.
   * <p>
   * An exception is catched when the website cannot be found.
   * @param area is used to determine which area the regions are from
   * @param url is where JSON is used to retreive each data
   * @return regionsArray returns an array with all the regions inside
   */
    public String[] searchRegionArray(String area, String url) throws IOException {
        ArrayList<String> regions = new ArrayList<String>();
        String[] regionsArray;
        //setting up the url for retrieving html page according to area
        String fullUrl = "http://www.bom.gov.au";
        fullUrl = fullUrl.concat(url);
        Elements regionsTable;
        Element regionTable;
        boolean urlWorks = false;
        int urlTest = 10;
        Document doc = null;

//        try {
            //connecting to the website and getting html using jsoup
            while(!urlWorks && urlTest > 0) {
                doc = Jsoup.connect(fullUrl).get();
                urlTest--;

                if(doc != null){
                    urlWorks = true;
                }
            }
            //if url matches pattern ".*all.*", the area is included in state - all observations
            //therefore we take the value differently
            if (Pattern.matches(".*all.*", url)) {
                //the div with box1 class includes all regions name
                regionsTable = doc.getElementsByClass("box1");
                Elements classes = regionsTable.select("a");
                //for every region name, add it to the arraylist
                for (Element loop : classes) {
                    String text = loop.text();
                    regions.add(text);
                }

            } else {
                //area is included in state capital city area, which means there is only 1 area
                //that has the same name as the area itself
                area = area.replace(" area", "");
                area = area.toUpperCase();
                regions.add(doc.getElementById(area).text());
            }


//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(new JTextField(), "ERROR WEBSITE NOT FOUND");
//            theLogger.log(Level.FINE,"The url " + fullUrl + "does not exist" );
//
//        }
        //converting arraylist to array of string and returning the value
        regionsArray = new String[regions.size()];
        regions.toArray(regionsArray);
        theLogger.log(Level.INFO, "Region data obtained correctly");
        return regionsArray;

    }

    
    /**
   * This method is used to search the stations of a certain region
   * and hence adding each station into an array depending on the region
   * <p>
   * An exception is catched when the website cannot be found.
   * @param region is used to determine what region the stations are from
   * @param url is where JSON is used to retreive each data
   * @return stationsArray is an array filled with the stations of the region
   */
    public String[] searchStationArray(String region, String url) throws IOException {
        ArrayList<String> stationsStrings = new ArrayList<String>();
        String[] stationsArray;
        regionUrl = url;
//        try {
            //connecting to the website and getting html using jsoup
            Document doc = Jsoup.connect(url).get();
            //getting all station names from the page
            Elements stations = doc.select("tbody").select("th");

            //if url matches pattern ".*all.*", the area is included in state - all observations
            //therefore we take the value differently
            if (Pattern.matches(".*all.*", url)) {
                //the div with box1 class includes all regions name & url
                Elements test = doc.getElementsByClass("box1");
                Elements classes = test.select("a");
                //for each of the region, get the name & url to find the correct station
                for (Element loop : classes) {
                    String text = loop.text();
                    String id = loop.attr("href");
                    String idTrim = id.replace("#", "");
                    //if the name is the same as the requested region, store all station name
                    if (text.equals(region)) {
                        for (Element station : stations) {
                            String stationsid = station.attr("id");
                            String pattern = "t";
                            pattern = pattern.concat(idTrim);
                            pattern = pattern.concat("-.*");
                            //for all station names, if the id matches the pattern made from the region url got beforehand
                            //add to arraylist
                            if (Pattern.matches(pattern, stationsid)) {
                                stationsStrings.add(station.text());
                            }
                        }
                    }
                }
            } else {
                //area is included in state capital city area, which means there is only 1 area
                //that has the same name as the area itself
                //store all station names in arraylist
                for (Element station : stations) {
                    if (Pattern.matches("obs-station-.*", station.attr("id"))) {
                        stationsStrings.add(station.text());
                    }
                }
            }


//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(new JTextField(), "ERROR WEBSITE NOT FOUND");
//            theLogger.log(Level.FINE,"The url " + url + "does not exist" );
//
//        }
        //convert arraylist into an arrya of strings and return it
        stationsArray = new String[stationsStrings.size()];
        stationsStrings.toArray(stationsArray);
        theLogger.log(Level.INFO, "Stations data obtained correctly");
        return stationsArray;
    }

    //function to find a specific station
    public Element findElement(String link, String item) throws IOException{
//        try {
            //connecting to the website and getting html using jsoup
            Document docArea = Jsoup.connect(link).get();

            //getting all station elements and returning the element if it has the same name
            Elements tableData = docArea.select("tbody").select("a");
            for (Element data : tableData) {
                String text = data.text();
                System.out.println(text);
                if (text.equals(item))
                    return data;
            }

//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(new JTextField(), "ERROR WEBSITE NOT FOUND");
//            theLogger.log(Level.FINE,"The url " + link + "does not exist" );
//        }
        return null;
    }
    
    /**
   * Used to add a user into the system.
   */
    public void addUser(String userName) {
        // Add user to Users Arraylist
        User newUser = new User();
        newUser.setUsername(userName);
        users.add(newUser);
    }

    //function to set the current user
    public void setCurrentUser(String current) {

        for (int i = 0; i < getUserList().size(); i++) {
            if (users.get(i).getUsername().equals(current)) {
                setCurrentUser(users.get(i));
            }
        }

//        System.out.println("CurrentUser is: "     + currentUser.getUsername());

    }

    public User getCurrent() {
        return currentUser;
    }

    public void setCurrentUser(User newUser) {
        currentUser = newUser;
    }

    public ArrayList<User> getUserList() {
        return users;
    }

    //Dummy data
    public void setData() {

        JSONFileWrite readFile = new JSONFileWrite();
        users = readFile.loadFile();
    }

    public String getStationUrl() {
        return stationUrl;
    }

    public String getStationSelected() {
        return mainWindow.getStationSelected();
    }

    public void setStationUrl(String stationUrl) {
        this.stationUrl = stationUrl;
    }

    public void addCurrentFavorite(String stationName) throws IOException {
        String url = findElement(regionUrl, stationName).attr("href");
        url = "http://www.bom.gov.au".concat(url);
        currentUser.addFavorite(new WeatherStation(stationName, url));
    }

    
    /**
   * This method is used to retreive every data from the stations.
   * This is for putting all the data received into a table using JSON.
   * @param stationName is used to determine what station all the data is coming from.
   * @return weatherObjects returns each object created from all the data received.
   * @see ArrayList
   */
    public ArrayList<WeatherObject> getTable(String stationName) {
        ArrayList<WeatherObject> weatherObjects = new ArrayList<WeatherObject>();
        String todaydate;
        //getting the url of requested station
        String url = currentUser.findWeatherStation(stationName).getStationUrl();
        stationUrl = url;
        String dayTime = "", temp = "", apparentTemp = "", viewPoint = "", relativeHumidity = "", dealta_T = "", windDirection = "",
                windSpeedKmh = "", windSpeedKnts = "", windGustKmh = "", windGustKnts = "", pressure1 = "", pressure2 = "", rainSince9am = "";
        int indicator = 0;
        ArrayList<String> daterows = new ArrayList<String>();

        try {
            //connecting to the website and getting html using jsoup
            Document doc = Jsoup.connect(url).get();

            //get all datas and assign it to its respective variable
            Elements dates = doc.getElementsByClass("rowleftcolumn");
            for (Element date : dates) {
                //indicator is to indicate what kind of data each column is
                indicator = 0;
                Elements rows = date.select("td");
                for (Element row : rows) {
                    switch (indicator) {
                        case 0:
                            dayTime = row.text();
                            break;
                        case 1:
                            temp = row.text();
                            break;
                        case 2:
                            apparentTemp = row.text();
                            break;
                        case 3:
                            viewPoint = row.text();
                            break;
                        case 4:
                            relativeHumidity = row.text();
                            break;
                        case 5:
                            dealta_T = row.text();
                            break;
                        case 6:
                            windDirection = row.text();
                            break;
                        case 7:
                            windSpeedKmh = row.text();
                            break;
                        case 8:
                            windSpeedKnts = row.text();
                            break;
                        case 9:
                            windGustKmh = row.text();
                            break;
                        case 10:
                            windGustKnts = row.text();
                            break;
                        case 11:
                            pressure1 = row.text();
                            break;
                        case 12:
                            pressure2 = row.text();
                            break;
                        case 13:
                            rainSince9am = row.text();
                            break;
                    }
                    indicator++;
                }
                //creating a weatherObject object for each row of data
                weatherObjects.add(new WeatherObject(dayTime, temp, apparentTemp, viewPoint, relativeHumidity,
                        dealta_T, windDirection, windSpeedKmh, windSpeedKnts, windGustKmh, windGustKnts, pressure1,
                        pressure2, rainSince9am));

            }
            weatherObjects.addAll(getForecastData(stationName));
            theLogger.log(Level.INFO, "Table data obtained correctly");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JTextField(), "ERROR WEBSITE NOT FOUND");
            theLogger.log(Level.FINE,"The url " + url + "does not exist" );
        }


        return weatherObjects;
    }

    //function to get only the temperature datas from stationpage
    //return value HashMap<String,String> where the key is date, and value is the temperature
    public HashMap<String,String> getTemp(String station) {
        //getting the url of requested station
        String url = currentUser.findWeatherStation(station).getStationUrl();
        int indicator = 0;
        Date today = new Date();
        String dayTime;
        HashMap<String,String> temps = new HashMap<String,String>();

        //setting up regex for 9am and 3pm temperature
        String regex9 = ".*09:00am";
        String regex3 = ".*03.00pm";

        //the value skip for
        String keyDate="skip";

        try {
            //connecting to the website and getting html using jsoup
            Document doc = Jsoup.connect(url).get();

            //getting all datas from the page
            Elements dates = doc.getElementsByClass("rowleftcolumn");
            for (Element date : dates) {
                indicator = 0;
                keyDate = "skip";
                Elements rows = date.select("td");
                for (Element row : rows) {
                    switch (indicator) {
                        case 0:
                            //for each row, if the column value matches the pattern, change value oy keyDate to temp
                            if(row.text().matches(regex3)||row.text().matches(regex9))
                                keyDate = row.text();
                            else
                            //if not, change it into "skip"
                                keyDate ="skip";
                            break;
                        case 1:
                            //if keyDate is not skip, that means we're at the correct row, either 9am or 3pm temp
                            //store date as key, and temp as value
                            if(!keyDate.equals("skip"))
                            {
                                temps.put(keyDate,row.text());
                                System.out.println("KeyDate: " + keyDate);
                            }
                            break;
                        default:
                            break;
                    }
                    indicator++;
                }
            }

            temps.putAll(getTempForecast(getForecastData(station)));

            theLogger.log(Level.INFO, "Temperature data obtained correctly");

        } catch (Exception e) {
            theLogger.log(Level.FINE,"The url " + url + "does not exist" );

        }
        return temps;

    }

    /**
   * This method is used to create a graph for the temperatures received
   * from the station
   * @param stationName is used to determine what station all the data is coming from.
   * @return returnValue returns all the temperature values in order to create the GUI for graph.
   * @see HashMap
   */
    public HashMap<String,String> checkHistory(String stationName)
    {
        WeatherStation station = currentUser.findWeatherStation(stationName);
        HashMap<String,String> returnValue = new HashMap<String,String>(); //key is date, value is temperature
        HashMap<String,String> histories = station.getHistory();
        String day;

        //iterate through all temperature history and putting it inside the hashmap
        Set set = histories.entrySet();
        Iterator iterate = set.iterator();
        while(iterate.hasNext()){
            Map.Entry history =
                    (Map.Entry)iterate.next();
            if(Pattern.matches(".*-9",history.getKey().toString()))
            {
                day = history.getKey().toString();
                //before putting the date inside the hashmap, the format is changed so GraphWindow can recognize it
                day = day.replace("-9","");
                day = day.concat("/09:00am");
                returnValue.put(day,history.getValue().toString());
            }
            if(Pattern.matches(".*-3",history.getKey().toString())) {
                day = history.getKey().toString();
                day = day.replace("-3", "");
                day = day.concat("/03:00pm");
                returnValue.put(day, history.getValue().toString());
            }
        }
        theLogger.log(Level.INFO, "History data obtained successfully");

        return returnValue;

    }

    //function to add a temperature history into a specific weather station
    public void addHistory(String day, String time, String temp, String stationName)
    {
        WeatherStation station = currentUser.findWeatherStation(stationName);
        //changing the format of the date so it can be differentiated between 9am or 3pm temperature
        day = day.concat("-" + time);

        //iterating the history to see whether there is already a same value inside history
        Set set = station.getHistory().entrySet();
        Iterator iterate = set.iterator();
        while(iterate.hasNext()){
            Map.Entry history = (Map.Entry)iterate.next();
            if(history.getKey().toString().equals(day))
                return;
        }
        station.addHistory(day, temp);

    }


    public ArrayList<WeatherObject> getForecastData(String station) throws IOException
    {
        ForecastIO forecast = new ForecastIO("8f8d061085f6aff231896bd712ff62f0");
            //http://code.runnable.com/VRq-p4PM6chtbDGy/google-api-for-java
            station = station.replace(" ", "+");

            //retrieving latitude ang longitude value of a location using google maps api
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+ station +",+Australia&key=AIzaSyBNJsALP1cpeLBmv0SOYcBUxEugc4IG760");
            System.out.println(url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml");
            OutputStream os = conn.getOutputStream();
            os.flush();
            System.out.println("HTTP code : "+ conn.getResponseCode());
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            String all = "";
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                all += output;
            }
            theLogger.log(Level.INFO, "Data of a location successfully retrieved");

            //extracting the latitude and longitude data from the json received
            JsonObject locationArray = JsonObject.readFrom(all);
            JsonValue fromLocationArray = locationArray.get("results");
            System.out.println(fromLocationArray);
            StringTokenizer token = new StringTokenizer(fromLocationArray.toString(), ":,]}{[\"");
            String tokens="";
            while(!tokens.equals("lat"))
                tokens = token.nextToken();
            String lat = token.nextToken();
            token.nextToken();
            String lon = token.nextToken();
            System.out.println("Latitude: " +lat+ " Longitude" + lon);
            System.out.println(lat);
            System.out.println(lon);

            //getting data from forecast.io with the latitude and longitude obtained
            if(forecast.getForecast(lat,lon))
                theLogger.log(Level.INFO, "Fetched forecast data correctly");
            JsonObject daily = forecast.getDaily();
            JsonValue data = daily.get("data");

            JsonArray array = data.asArray();
            JsonArray otherArray = JsonArray.readFrom(data.toString());
            Calendar calendar = Calendar.getInstance();
            ArrayList<WeatherObject> forecasts = new ArrayList<WeatherObject>();

            //separating data from the json
            for (JsonValue a: otherArray) {
                JsonObject object = JsonObject.readFrom(a.toString());
                System.out.println(object.get("time"));
                calendar.setTimeInMillis(object.get("time").asInt() * 1000L);
                System.out.println(calendar.getTime().toString());

                //setting time for the forecast to retrieve hourly data
                forecast.setTime(calendar.getTime().toString());

                //for each time data received, retrieve hourly data and store as weatherObject, and add to arraylist
                for(JsonValue b: forecast.getHourly().get("data").asArray())
                {
                    JsonObject weatherData = JsonObject.readFrom(b.toString());
                    calendar.setTimeInMillis(weatherData.get("time").asInt() * 1000L);
                    System.out.println("  " + weatherData.get("time"));
                    String hour = ""+calendar.get(Calendar.HOUR_OF_DAY);
                    String day = ""+calendar.get(Calendar.DAY_OF_MONTH);
                    String dateTime = day + "/";
                    if(Integer.parseInt(hour)<10)
                        dateTime += "0";
                    dateTime += hour + ":00";
                    if(Integer.parseInt(hour)<12)
                        dateTime +="am";
                    else
                        dateTime +="pm";
                    System.out.println("  DateTime: " + dateTime);
                    double windSpeedMPH = Double.parseDouble(weatherData.get("windSpeed").toString());
                    double windSpeedKMH = windSpeedMPH * 1.60934;
                    double windSpeedKnots = windSpeedMPH * 0.868976;
                    forecasts.add(new WeatherObject(dateTime, weatherData.get("temperature").toString(), weatherData.get("apparentTemperature").toString(),
                            "-","-","-","-", ""+windSpeedKMH, ""+windSpeedKnots,"-","-",weatherData.get("pressure").toString(),"-","-"));
                }
            }
            //disconnect from google maps api
            conn.disconnect();

            return forecasts;

    }


    public HashMap<String,String> getTempForecast(ArrayList<WeatherObject> forecasts)
    {
        String regex9 = ".*09:00am";
        String regex3 = ".*15.00pm";
        HashMap<String,String> returnValue = new HashMap<String,String>();

        for(WeatherObject forecast: forecasts)
            if(forecast.getDayTime().matches(regex9) || forecast.getDayTime().matches(regex3))
                returnValue.put(forecast.getDayTime(), forecast.getTemp());

        theLogger.log(Level.INFO, "Forecast temperature data obtained successfully");

        return returnValue;
    }

}
