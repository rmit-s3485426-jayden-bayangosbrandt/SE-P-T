package model;



import view.MainWindow;

public class Model {
    private static Model ourInstance = new Model();

    private MainWindow mainWindow;
    private WeatherStation weatherStation;

    public static Model getInstance() {
        return ourInstance;
    }

    private Model() {
    }

    public void setMainWindow(MainWindow window){
        this.mainWindow = window;
    }
    public void setWeatherStation(WeatherStation station){
        this.weatherStation = weatherStation;
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

    public boolean isAreaSelected(){
        return mainWindow.isAreaSelected();
    }

    public boolean isRegionSelected(){
        return mainWindow.isRegionSelected();
    }

    public boolean isStationSelected(){
        return mainWindow.isStationSelected();
    }

    public void searchRegionWeatherStation(String query){
        weatherStation.searchRegionArray(query);
    }

}
