package model;



import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import view.MainWindow;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Model {
    private static Model ourInstance = new Model();

    private MainWindow mainWindow;
    private WeatherStation weatherStation;
    private String regionUrl;
    private String stationUrl;
    public static Model getInstance() {
        return ourInstance;
    }

    private Model() {
    }

    public void setMainWindow(MainWindow window){
        this.mainWindow = window;
    }
    public void setWeatherStation(WeatherStation station){
        this.weatherStation = station;
    }

    public void changeAreaDataset(String[] strings){
        mainWindow.setAreaDataset(strings);
    }

    public void changeRegionDataset(String[] strings){
        mainWindow.setRegionDataset(strings);
    }

    public void changeStationDataset(String[] strings){
        mainWindow.setStationDataset(strings);
    }

    public void setLblName(String name){
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

    public void searchStationWeatherStation(String query){
        // Uncomment when implemented
//        weatherStation.searchStationArray(query);
    }

    public void setStationEnabled(boolean enabled){
        // Set the state of station combobox
        mainWindow.setStationEnabled(enabled);
    }

    public void setRegionEnabled(boolean enabled){
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
        changeRegionDataset(searchRegionArray(foundArea.text(),regionUrl));
        regionUrl = "http://www.bom.gov.au".concat(regionUrl);
    }

    public void changeStation(String region) {
        String id;
        changeStationDataset(searchStationArray(region,regionUrl));
    }

    public void setArea()
    {
        try {
            String[] areas;
            ArrayList<String> ArrayListAreas = new ArrayList<String>();
            Document docArea = Jsoup.connect("http://www.bom.gov.au/catalogue/data-feeds.shtml").get();
            Elements table = docArea.getElementsByAttributeValue("width", "712");
            Elements tableData = table.select("tbody").select("a");
            String regex = "http.*";
            for(Element area : tableData) {
                String areaText = area.text();
                if(Pattern.matches(regex, areaText))
                    continue;
                ArrayListAreas.add(areaText);
            }
            areas = new String[ArrayListAreas.size()];
            ArrayListAreas.toArray(areas);

            changeAreaDataset(areas);
        }
        catch(Exception e)
        {

        }


    }
    public String[] searchRegionArray(String area, String url){
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
            if(Pattern.matches(".*all.*",url))
            {
                regionsTable = doc.getElementsByClass("box1");
                Elements classes = regionsTable.select("a");
                for (Element loop : classes) {
                    String text = loop.text();
                    regions.add(text);
                }

            }
            else
            {
                area = area.replace(" area","");
                area = area.toUpperCase();
                regions.add(doc.getElementById(area).text());
            }

        }
        catch (Exception e) {

        }
        regionsArray = new String[regions.size()];
        regions.toArray(regionsArray);
        return regionsArray;

    }



    public String[] searchStationArray(String region, String url) {
        ArrayList<String> stationsStrings = new ArrayList<String>();
        String[] stationsArray;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements stations = doc.select("tbody").select("th");
            if(Pattern.matches(".*all.*",url))
            {
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
            }
            else
            {
                for(Element station: stations){
                    if(Pattern.matches("obs-station-.*",station.attr("id")))
                    {
                        stationsStrings.add(station.text());
                    }
                }
            }



        }
        catch(Exception e) {

        }
        stationsArray = new String[stationsStrings.size()];
        stationsStrings.toArray(stationsArray);
        return stationsArray;
    }

    public Element findElement(String link, String item){
        try{
            Document docArea = Jsoup.connect(link).get();
            Elements tableData = docArea.select("tbody").select("a");
            for(Element data: tableData) {
                String text = data.text();
                System.out.println(text);
                if(text.equals(item))
                    return data;
            }

        }
        catch(Exception e) {

        }
        return null;
    }

    public String getStationUrl() {
        return stationUrl;
    }

    public void setStationUrl(String stationUrl) {
        this.stationUrl = stationUrl;
    }
}
