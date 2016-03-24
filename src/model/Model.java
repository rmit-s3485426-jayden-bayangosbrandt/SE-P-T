package model;


import com.sun.javafx.stage.WindowHelper;
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

    public void changeDataset(){
        String[] strings = new String[]{"a", "b", "c"};
        mainWindow.setAreaDataset(strings);
    }
}
