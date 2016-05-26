package test;

import java.io.IOException;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import model.Model;
import model.WeatherStation;
import view.WelcomeWindow;
import com.github.dvdme.ForecastIOLib.ForecastIO;

/**
 * Created by Alex on 21/04/2016.
 */
public class UnitTesting {
    String[] NswRegions = {"Northern Rivers", "Mid North Coast", "Hunter", "Northern Tablelands", "Sydney Metropolitan",
            "Illawarra", "South Coast", "Central Tablelands", "Southern Tablelands", "Snowy Mountains", "North West Slopes & Plains",
            "Central West Slopes & Plains", "South West Slopes", "Riverina", "Lower Western", "Upper Western", "Islands"};
    String[] NRStations = {"Ballina", "Cape Byron", "Casino", "Coolangatta", "Evans Head", "Grafton AgRS", "Grafton Airport",
            "Lismore Airport", "Murwillumbah", "Yamba"};
    //Alex i don't remember what the website is, please put it in
    Model model = Model.getInstance();
    WelcomeWindow welcome = new WelcomeWindow();
    ForecastIO forecast = new ForecastIO("ea126d89b1151afbe571ea42d6be7ff8");

    @Test
    public void NswRegionsTrue() throws IOException{
        assertEquals(NswRegions, model.searchRegionArray("New South Wales", "/nsw/observations/nswall.shtml"));
    }
    @Test
    public void NswRegionsFalse() throws IOException{
        assertNotSame(NswRegions, model.searchRegionArray("New South Wales", "/nsw/observations/nswall.shtml"));
    }
    @Test
    public void NswStationTrue()throws IOException{
        assertEquals(NRStations, model.searchStationArray("Northern Rivers", "http://www.bom.gov.au/nsw/observations/nswall.shtml"));
    }
    @Test
    public void NswStationFalse()throws IOException{
        assertNotSame(NRStations, model.searchStationArray("Northern Rivers", "http://www.bom.gov.au/nsw/observations/nswall.shtml"));
    }

    @Test
    public void welcomeWindow(){
        assertNotNull(welcome.getWelcomePanel());
    }

    @Test(expected= IOException.class)
    public void urlNotFound() throws IOException
    {
        model.searchStationArray("Northern Rivers", "http://www.bom.gov.au/nsw/observations/nswal.shtml");
    }

    @Test
    public void testForecast()
    {
        assertNotNull(model.getForecastData("Northern Rivers"));
    }

//    @Test
//    public void testFindElement()
//    {
//        assertEquals("http://www.bom.gov.au/catalogue/data-feeds.shtml", findElement("http://www.bom.gov.au/catalogue/data-feeds.shtml", "area"));
//    }
//    @Test
//    public void wrongAPI()
//    {
//        forecast = null;
//        forecast = new ForecastIO("ea126d89b1151afbe571ea42d6be7ff8aaaaaa");
//        assertNull(forecast);
//    }




}
