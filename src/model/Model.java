package model;



import controller.JSONFileWrite;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import view.MainWindow;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import view.WelcomeWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.*;
import java.text.SimpleDateFormat;


public class Model {

    private static Model ourInstance = new Model();
    private WelcomeWindow welcomeWindow;
    private MainWindow mainWindow;
    private WeatherStation weatherStation;
    private String regionUrl;
    private String stationUrl;
    private ArrayList<User> users = new ArrayList<>();
    private User currentUser;

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

//    public void saveWindow(){
//        currentUser.setOpenWindows(windows);
//    }

    public void addOpenWindow(JFrame frame) {
        currentUser.getOpenWindows().add(frame);
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

    public String getStationUrlFromWeb(String station) {
        String url;
        Element stationTableData = findElement(regionUrl, station);
        url = stationTableData.attr("href");
        url = "http://www.bom.gov.au".concat(url);
        return url;
    }

    public void setLblName(String name) {
        mainWindow.setLblName(name);
    }

//    public boolean isAreaSelected(){
//        return mainWindow.isAreaSelected();
//    }
//
//    public boolean isRegionSelected(){
//        return mainWindow.isRegionSelected();
//    }
//
//    public boolean isStationSelected(){
//        return mainWindow.isStationSelected();
//    }

//    public void searchRegionWeatherStation(String query){
//
//    }

    public void closeWelcome() {
        welcomeWindow.closeWindow();
    }

    public void searchStationWeatherStation(String query) {
        // Uncomment when implemented
//        weatherStation.searchStationArray(query);
    }

    public void setStationEnabled(boolean enabled) {
        // Set the state of station combobox
        mainWindow.setStationEnabled(enabled);
    }

    public void setRegionEnabled(boolean enabled) {
        // Set the state of region combobox
        mainWindow.setRegionEnabled(enabled);
    }

//    public String getStationSelected(){
//        return mainWindow.getStationSelected();
//    }

//    public String getRegionSelected(){
//        return mainWindow.getRegionSelected();
//    }

//    public String getAreaSelected(){
//        return mainWindow.getAreaSelected();
//    }

    public void changeRegion(String area) {
        String id;
        Element foundArea = findElement("http://www.bom.gov.au/catalogue/data-feeds.shtml", area);
        regionUrl = foundArea.attr("href");
        changeRegionDataset(searchRegionArray(foundArea.text(), regionUrl));
        regionUrl = "http://www.bom.gov.au".concat(regionUrl);
    }

    public void changeStation(String region) {
        String id;
        changeStationDataset(searchStationArray(region, regionUrl));
    }

    public void setArea() {
        try {
            String[] areas;
            ArrayList<String> ArrayListAreas = new ArrayList<String>();
            Document docArea = Jsoup.connect("http://www.bom.gov.au/catalogue/data-feeds.shtml").get();
            Elements table = docArea.getElementsByAttributeValue("width", "712");
            Elements tableData = table.select("tbody").select("a");
            String regex = "http.*";
            for (Element area : tableData) {
                String areaText = area.text();
                if (Pattern.matches(regex, areaText))
                    continue;
                ArrayListAreas.add(areaText);
            }
            areas = new String[ArrayListAreas.size()];
            ArrayListAreas.toArray(areas);

            changeAreaDataset(areas);
        } catch (Exception e) {

        }


    }

    public String[] searchRegionArray(String area, String url) {
        ArrayList<String> regions = new ArrayList<String>();
        String[] regionsArray;

        String fullUrl = "http://www.bom.gov.au";
        fullUrl = fullUrl.concat(url);
        Elements regionsTable;
        Element regionTable;
        String pattern = ".*";
        pattern = pattern.concat(area);
        pattern = pattern.concat(".*");
        try {
            Document doc = Jsoup.connect(fullUrl).get();
            if (Pattern.matches(".*all.*", url)) {
                regionsTable = doc.getElementsByClass("box1");
                Elements classes = regionsTable.select("a");
                for (Element loop : classes) {
                    String text = loop.text();
                    regions.add(text);
                }

            } else {
                area = area.replace(" area", "");
                area = area.toUpperCase();
                regions.add(doc.getElementById(area).text());
            }

        } catch (Exception e) {

        }
        regionsArray = new String[regions.size()];
        regions.toArray(regionsArray);
        return regionsArray;

    }

    public String[] searchStationArray(String region, String url) {
        ArrayList<String> stationsStrings = new ArrayList<String>();
        String[] stationsArray;
        regionUrl = url;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements stations = doc.select("tbody").select("th");
            if (Pattern.matches(".*all.*", url)) {
                Elements test = doc.getElementsByClass("box1");
                Elements classes = test.select("a");
                for (Element loop : classes) {
                    String text = loop.text();
                    String id = loop.attr("href");
                    String idTrim = id.replace("#", "");
                    if (text.equals(region)) {
                        for (Element station : stations) {
                            String stationsid = station.attr("id");

                            String pattern = "t";
                            pattern = pattern.concat(idTrim);
                            pattern = pattern.concat("-.*");

                            if (Pattern.matches(pattern, stationsid)) {
                                stationsStrings.add(station.text());
                            }
                        }
                    }
                }
            } else {
                for (Element station : stations) {
                    if (Pattern.matches("obs-station-.*", station.attr("id"))) {
                        stationsStrings.add(station.text());
                    }
                }
            }


        } catch (Exception e) {

        }
        stationsArray = new String[stationsStrings.size()];
        stationsStrings.toArray(stationsArray);
        return stationsArray;
    }

    public Element findElement(String link, String item) {
        try {
            Document docArea = Jsoup.connect(link).get();
            Elements tableData = docArea.select("tbody").select("a");
            for (Element data : tableData) {
                String text = data.text();
                System.out.println(text);
                if (text.equals(item))
                    return data;
            }

        } catch (Exception e) {

        }
        return null;
    }

    public void addUser(String userName) {
        // Add user to Users Arraylist
        User newUser = new User();
        newUser.setUsername(userName);
        users.add(newUser);
    }

    public void setCurrentUser(String current) {

        for (int i = 0; i < getUserList().size(); i++) {
            if (users.get(i).getUsername().equals(current)) {
                setCurrentUser(users.get(i));
            }
        }

        System.out.println("CurrentUser is: " + currentUser.getUsername());

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
//        addUser("Yung");
//        addUser("Jayden");
//        addUser("Alex");
//        addUser("Aaron");

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

    public void addCurrentFavorite(String stationName) {
        String url = findElement(regionUrl, stationName).attr("href");
        url = "http://www.bom.gov.au".concat(url);
        currentUser.addFavorite(new WeatherStation(stationName, url));
    }

    public ArrayList<WeatherObject> getTable(String stationName) {
        ArrayList<WeatherObject> weatherObjects = new ArrayList<WeatherObject>();
        String todaydate;
//        String url = getStationUrlFromWeb(stationUrl);
        String url = currentUser.findWeatherStation(stationName).getStationUrl();
        stationUrl = currentUser.findWeatherStation(stationName).getStationUrl();
        String dayTime = "", temp = "", apparentTemp = "", viewPoint = "", relativeHumidity = "", dealta_T = "", windDirection = "",
                windSpeedKmh = "", windSpeedKnts = "", windGustKmh = "", windGustKnts = "", pressure1 = "", pressure2 = "", rainSince9am = "";
        int indicator = 0;
        Date today = new Date();
        ArrayList<String> daterows = new ArrayList<String>();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd");
        System.out.println(dateformat.format(today));
        todaydate = dateformat.format(today);
        String regex = todaydate.concat(".*");
        try {
            Document doc = Jsoup.connect(url).get();
            Elements dates = doc.getElementsByClass("rowleftcolumn");
            for (Element date : dates) {
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
                weatherObjects.add(new WeatherObject(dayTime, temp, apparentTemp, viewPoint, relativeHumidity,
                        dealta_T, windDirection, windSpeedKmh, windSpeedKnts, windGustKmh, windGustKnts, pressure1,
                        pressure2, rainSince9am));

            }

        } catch (Exception e) {

        }
        return weatherObjects;
    }

    public HashMap<String,String> getTemp() {
        int indicator = 0;
        Date today = new Date();
        String dayTime;
        HashMap<String,String> temps = new HashMap<String,String>();
        String regex9 = ".*09:00am";
        String regex3 = ".*03.00pm";
        String keyDate="skip";
        try {
            Document doc = Jsoup.connect(stationUrl).get();
            Elements dates = doc.getElementsByClass("rowleftcolumn");
            for (Element date : dates) {
                indicator = 0;
                keyDate = "skip";
                Elements rows = date.select("td");
                for (Element row : rows) {
                    switch (indicator) {
                        case 0:
                            if(row.text().matches(regex3)||row.text().matches(regex9))
                                keyDate = row.text();
                            else
                                keyDate ="skip";
                            break;
                        case 1:
                            if(!keyDate.equals("skip"))
                                temps.put(keyDate,row.text());
                            break;
                        default:
                            break;
                    }
                    indicator++;
                }
            }

        } catch (Exception e) {

        }
        return temps;

    }
}
