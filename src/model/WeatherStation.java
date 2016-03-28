package model;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;
import java.util.regex.Pattern;

public class WeatherStation {
    public WeatherStation(String Inputid)
    {
        int a=0;
        try
        {
            System.out.println("TEST");
            Document doc = Jsoup.connect("http://www.bom.gov.au/nsw/observations/nswall.shtml").get();
            Elements test = doc.getElementsByClass("box1");
            Elements classes = test.select("a");
            Elements stations = doc.select("tbody").select("th");
            for(Element loop: classes)
            {
                String text = loop.text();
                String id = loop.attr("href");
                String idTrim = id.replace("#","");
                System.out.println(text);
                // model.changeRegionDataset(text);
                // add the different regions into the dropdown
                
                
                // This is my plan Alex:
                // if(region dropdown menu is != NULL)
                //{
                      // get the ID of the region only
                      // String regionID = selected region
                      // then do your loop at the bottom
                //}
                
                }
                for(Element station: stations)
                {
                    String stationsid = station.attr("id");

                    String pattern = "t";
                    pattern = pattern.concat(idTrim);
                    pattern = pattern.concat("-.*");

                    if(Pattern.matches(pattern, stationsid))
                    {
                        // model.changeStationDataset(station.text());
                        // to change the drop-down menu probs, idk
                        System.out.println(station.text());
                        a++;
                    }
                }
                System.out.println();

            }
            System.out.println(a);

        }
        catch(Exception e)
        {

        }
    }

}
