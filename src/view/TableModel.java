package view;

import model.WeatherObject;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TableModel extends AbstractTableModel {

    private ArrayList<WeatherObject> objects;
    private String[] columnNames = new String[]{"Day Time", "Temp", "ApparentTemp",
                                                "ViewPoint", "RelativeHumidity", "Delta_T",
                                                "WindDirection", "WindSpeedKmh", "WindSpeedKnts",
                                                "WindGustKmh", "WindGustKnts", "Pressure1",
                                                "RainSince9am"};

    public TableModel(ArrayList<WeatherObject> objects) {
        this.objects = objects;
    }

    @Override
    public int getRowCount() {
        return objects.size();
    }

    @Override
    public int getColumnCount() {
        return 13;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }


    /**
     * this method gets all the different attributes inside a station
     * that was selected by the user
     * @param rowIndex the row number of the table
     * @param columnIndex the coloumn number of the table
     * @return value returns the selected attribute that is found
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "??";
        WeatherObject weather = objects.get(rowIndex);
        switch (columnIndex){
            case 0:
                value = weather.getDayTime();
                break;
            case 1:
                value = weather.getTemp();
                break;
            case 2:
                value = weather.getApparentTemp();
                break;
            case 3:
                value = weather.getViewPoint();
                break;
            case 4:
                value = weather.getRelativeHumidity();
                break;
            case 5:
                value = weather.getDealta_T();
                break;
            case 6:
                value = weather.getWindDirection();
                break;
            case 7:
                value = weather.getWindSpeedKmh();
                break;
            case 8:
                value = weather.getWindSpeedKnts();
                break;
            case 9:
                value = weather.getWindGustKmh();
                break;
            case 10:
                value = weather.getWindGustKnts();
                break;
            case 11:
                value = weather.getPressure1();
                break;
            case 12:
                value = weather.getPressure2();
                break;
            case 13:
                value = weather.getRainSince9am();
                break;
        }
        return value;
    }
}
