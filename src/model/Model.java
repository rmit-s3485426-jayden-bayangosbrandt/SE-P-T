package model;



import view.MainWindow;

public class Model {
    private static Model ourInstance = new Model();

    private MainWindow mainWindow;

    public static Model getInstance() {
        return ourInstance;
    }

    private Model() {
    }

    public void setMainWindow(MainWindow window){
        this.mainWindow = window;
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

}
