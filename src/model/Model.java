package model;

/**
 * Created by Jayden on 18/03/2016.
 */
public class Model {
    private static Model ourInstance = new Model();

    public static Model getInstance() {
        return ourInstance;
    }

    private Model() {
    }
}
