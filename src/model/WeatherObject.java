package model;

/**
 * Created by Jayden on 8/04/2016.
 */
public class WeatherObject {

    private String dayTime;
    private String temp;
    private String apparentTemp;
    private String viewPoint;
    private String relativeHumidity;
    private String dealta_T;
    private String windDirection;
    private String windSpeedKmh;
    private String windSpeedKnts;
    private String windGustKmh;
    private String windGustKnts;
    private String pressure1;
    private String pressure2;
    private String rainSince9am;

    public WeatherObject(String dayTime, String temp, String apparentTemp,
                         String viewPoint, String relativeHumidity, String dealta_T,
                         String windDirection, String windSpeedKmh, String windSpeedKnts,
                         String windGustKmh, String windGustKnts, String pressure1,
                         String pressure2, String rainSince9am) {
        this.dayTime = dayTime;
        this.temp = temp;
        this.apparentTemp = apparentTemp;
        this.viewPoint = viewPoint;
        this.relativeHumidity = relativeHumidity;
        this.dealta_T = dealta_T;
        this.windDirection = windDirection;
        this.windSpeedKmh = windSpeedKmh;
        this.windSpeedKnts = windSpeedKnts;
        this.windGustKmh = windGustKmh;
        this.windGustKnts = windGustKnts;
        this.pressure1 = pressure1;
        this.pressure2 = pressure2;
        this.rainSince9am = rainSince9am;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getApparentTemp() {
        return apparentTemp;
    }

    public void setApparentTemp(String apparentTemp) {
        this.apparentTemp = apparentTemp;
    }

    public String getViewPoint() {
        return viewPoint;
    }

    public void setViewPoint(String viewPoint) {
        this.viewPoint = viewPoint;
    }

    public String getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(String relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public String getDealta_T() {
        return dealta_T;
    }

    public void setDealta_T(String dealta_T) {
        this.dealta_T = dealta_T;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindSpeedKmh() {
        return windSpeedKmh;
    }

    public void setWindSpeedKmh(String windSpeedKmh) {
        this.windSpeedKmh = windSpeedKmh;
    }

    public String getWindSpeedKnts() {
        return windSpeedKnts;
    }

    public void setWindSpeedKnts(String windSpeedKnts) {
        this.windSpeedKnts = windSpeedKnts;
    }

    public String getWindGustKmh() {
        return windGustKmh;
    }

    public void setWindGustKmh(String windGustKmh) {
        this.windGustKmh = windGustKmh;
    }

    public String getWindGustKnts() {
        return windGustKnts;
    }

    public void setWindGustKnts(String windGustKnts) {
        this.windGustKnts = windGustKnts;
    }

    public String getPressure1() {
        return pressure1;
    }

    public void setPressure1(String pressure1) {
        this.pressure1 = pressure1;
    }

    public String getPressure2() {
        return pressure2;
    }

    public void setPressure2(String pressure2) {
        this.pressure2 = pressure2;
    }

    public String getRainSince9am() {
        return rainSince9am;
    }

    public void setRainSince9am(String rainSince9am) {
        this.rainSince9am = rainSince9am;
    }
}
