package model;


public class Model {
    private static Model ourInstance = new Model();

    public static Model getInstance() {
        return ourInstance;
    }

    private Model() {
    }
}
