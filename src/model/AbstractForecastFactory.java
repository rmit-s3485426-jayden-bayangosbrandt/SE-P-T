package model;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.github.dvdme.ForecastIOLib.ForecastIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Alex on 27/05/2016.
 */
public class AbstractForecastFactory {
    private static AbstractForecastFactory ourInstance = new AbstractForecastFactory();
    Logger theLogger = Logger.getLogger(AbstractForecastFactory.class.getName());

    ArrayList<WeatherObject> temp;

    public static AbstractForecastFactory getInstance() {
        return ourInstance;
    }


    public ArrayList<WeatherObject> getForecastData(String station) throws IOException
    {
        ForecastIO forecast = new ForecastIO("8f8d061085f6aff231896bd712ff62f0");
        String[] coordinates = getCoordinates(station);
        String lat = coordinates[0];
        String lon = coordinates[1];
        //getting data from forecast.io with the latitude and longitude obtained
        if(forecast.getForecast(lat,lon))
            theLogger.log(Level.INFO, "Fetched forecast data correctly");
        JsonObject daily = forecast.getDaily();
        System.out.println(daily);
        JsonValue data = daily.get("data");

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
            String forecastTime = "" + calendar.get(Calendar.YEAR) + "-";
            if(calendar.get(Calendar.MONTH) < 10)
                forecastTime+="0";
            forecastTime += calendar.get(Calendar.MONTH) + "-";
            if(calendar.get(Calendar.DAY_OF_MONTH) < 10)
                forecastTime+="0";
            forecastTime += calendar.get(Calendar.DAY_OF_MONTH) + "T00:00:00-0400";
            System.out.println(forecastTime);
            forecast.setTime(forecastTime);
            forecast.getForecast(lat,lon);
            System.out.println("ALL HOURLY" + forecast.getHourly());

            //for each time data received, retrieve hourly data and store as weatherObject, and add to arraylist
            for(JsonValue b: forecast.getHourly().get("data").asArray())
            {
                JsonObject weatherData = JsonObject.readFrom(b.toString());
                calendar.setTimeInMillis(weatherData.get("time").asInt() * 1000L);
                System.out.println("HOURLY: " + weatherData);
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
                double windSpeedMPH = -1;
                double windSpeedKMH = -1;
                double windSpeedKnots =-1;
                if(weatherData.get("windSpeed")!=null)
                {
                    windSpeedMPH = Double.parseDouble(weatherData.get("windSpeed").toString());
                    windSpeedKMH = windSpeedMPH * 1.60934;
                    windSpeedKnots = windSpeedMPH * 0.868976;
                }
                String speedKMH = "-";
                String speedKnot = "-";
                if(windSpeedKMH!=-1)
                {
                    speedKMH = ""+windSpeedKMH;
                    speedKnot = ""+windSpeedKnots;
                }
                String pressure = "-";
                if(weatherData.get("pressure")!= null)
                    pressure = weatherData.get("pressure").toString();
                forecasts.add(new WeatherObject(dateTime, weatherData.get("temperature").toString(), weatherData.get("apparentTemperature").toString(),
                        "-","-","-","-", speedKMH, speedKnot,"-","-",pressure,"-","-"));
            }
        }
        //disconnect from google maps api

        temp = forecasts;

        return forecasts;

    }


    public HashMap<String,String> getTempForecast()
    {
        String regex9 = ".*09:00am";
        String regex3 = ".*15.00pm";
        HashMap<String,String> returnValue = new HashMap<String,String>();

        for(WeatherObject forecast: temp)
            if(forecast.getDayTime().matches(regex9) || forecast.getDayTime().matches(regex3))
                returnValue.put(forecast.getDayTime(), forecast.getTemp());

        theLogger.log(Level.INFO, "Forecast temperature data obtained successfully");

        return returnValue;
    }

    // Whether forcast source is
    // ForecastIO or
    // OpenWeatherMap


    public ArrayList<WeatherObject> getForecastDataORG(String station) throws IOException
    {

        ArrayList<WeatherObject> returnValue = new ArrayList<WeatherObject>();
        String[] coordinates = getCoordinates(station);
        String lat = coordinates[0];
        String lon = coordinates[1];
        String APIKey = "f7c27a007857d74ed4d47fa394e41759";

        URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?lat="+lat+"&lon="+lon+"&metrics=metric&appid="+APIKey);
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
            all += output;
        }
        System.out.println(all);

        JsonObject allData = JsonObject.readFrom(all);
        JsonArray dataList = allData.get("list").asArray();

        Calendar calendar = Calendar.getInstance();

        for(JsonValue a : dataList)
        {
            JsonObject individualData = JsonObject.readFrom(a.toString());

            calendar.setTimeInMillis(individualData.get("dt").asInt() * 1000L);
            System.out.println("DATE TIME: " + calendar.getTime());

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


            JsonObject mainData = JsonObject.readFrom(individualData.get("main").toString());
            double temp = Math.round((Double.parseDouble(mainData.get("temp").toString()) - 273.15)*100)/100;
            System.out.println("TEMP: "+ temp);

            Double windSpeedMS = Double.parseDouble(JsonObject.readFrom(individualData.get("wind").toString()).get("deg").toString());
            Double windSpeedKMH = windSpeedMS *3.6 ;
            Double windSpeedKnot = windSpeedKMH * 0.539957;
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);

            returnValue.add(new WeatherObject(dateTime, ""+temp,"-", "-",mainData.get("humidity").toString(),"-",
                    JsonObject.readFrom(individualData.get("wind").toString()).get("deg").toString(),
                    df.format(windSpeedKMH),df.format(windSpeedKnot),"-","-",mainData.get("pressure").toString(),"-","-" ));
        }
        conn.disconnect();
        theLogger.log(Level.INFO,"Forecast data from openweathermap.org successfully retrieved");
        return returnValue;
    }


    /**
     * this method finds the location of the certain station we want to find, finds the latitude
     * and longitude
     * @param station name of the wetaherstation
     * @return latitude/longitude
     * @throws IOException thrown when the website isn't found
     */
    public String[] getCoordinates(String station) throws IOException
    {
        String[] coordinates = new String[2];
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
            all += output;
        }
        theLogger.log(Level.INFO, "Data of a location successfully retrieved");

        //extracting the latitude and longitude data from the json received
        JsonObject locationArray = JsonObject.readFrom(all);
        JsonValue fromLocationArray = locationArray.get("results");
        StringTokenizer token = new StringTokenizer(fromLocationArray.toString(), ":,]}{[\"");
        String tokens="";
        while(!tokens.equals("lat"))
            tokens = token.nextToken();
        String lat = token.nextToken();
        token.nextToken();
        String lon = token.nextToken();
        System.out.println("Latitude: " +lat+ " Longitude" + lon);
        coordinates[0] = lat;
        coordinates[1] = lon;
        conn.disconnect();
        return coordinates;
    }
}
