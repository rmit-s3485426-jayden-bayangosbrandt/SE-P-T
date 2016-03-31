package model;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import view.Window;

import java.util.*;
import java.util.regex.Pattern;



public class WeatherStation {

    private ArrayList stationArray = new ArrayList();
    private ArrayList regionArray = new ArrayList();
    private ArrayList regionID = new ArrayList();
    String currentID;

    private Model model = Model.getInstance();
    public WeatherStation() {
        int a = 0;
        try {
            System.out.println("TEST");
            Document doc = Jsoup.connect("http://www.bom.gov.au/nsw/observations/nswall.shtml").get();
            Elements test = doc.getElementsByClass("box1");
            Elements classes = test.select("a");
            Elements stations = doc.select("tbody").select("th");
            for (Element loop : classes) {
                String text = loop.text();
                String id = loop.attr("href");
                String idTrim = id.replace("#", "");
                regionID.add(idTrim);

                System.out.println(text);
                regionArray.add(text);

                for (Element station : stations) {
                    String stationsid = station.attr("id");

                    String pattern = "t";
                    pattern = pattern.concat(idTrim);
                    pattern = pattern.concat("-.*");

                    if (Pattern.matches(pattern, stationsid)) {
                        stationArray.add(station.text());
                        System.out.println(station.text());
                        a++;
                    }
                }
                System.out.println();

            }
            System.out.println(a);

        } catch (Exception e) {

        }
        String[] stationStrings = new String[stationArray.size()+1];
        stationArray.add(0, "Please Select");
        stationArray.toArray(stationStrings);

        String[] regionString = new String[regionArray.size()+1];
        regionArray.add(0, "Please Select");
        regionArray.toArray(regionString);

        model.changeRegionDataset(regionString);
        model.changeStationDataset(stationStrings);

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
    }


