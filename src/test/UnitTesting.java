package test;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import model.Model;
import model.WeatherStation;
import view.WelcomeWindow;

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

    @Test
    public void NswRegionsTrue(){
        assertEquals(NswRegions, model.searchRegionArray("New South Wales", "/nsw/observations/nswall.shtml"));
    }
    @Test
    public void NswRegionsFalse(){
        assertNotSame(NswRegions, model.searchRegionArray("New South Wales", "/nsw/observations/nswall.shtml"));
    }
    @Test
    public void NswStationTrue(){
        assertEquals(NRStations, model.searchStationArray("Northern Rivers", "http://www.bom.gov.au/nsw/observations/nswall.shtml"));
    }
    @Test
    public void NswStationFalse(){
        assertNotSame(NRStations, model.searchStationArray("Northern Rivers", "http://www.bom.gov.au/nsw/observations/nswall.shtml"));
    }

    @Test
    public void welcomeWindow(){
        assertNotNull(welcome.getWelcomePanel());
    }
}
