package view;

import model.WeatherObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ChartWindow extends JFrame {

    private JTable table;
    private TableModel tableModel;
//    private String[] columnNames = new String[]{"Day Time", "Temp", "ApparentTemp",
//                                                "ViewPoint", "RelativeHumidity", "Delta_T",
//                                                "WindDirection", "WindSpeedKmh", "WindSpeedKnts",
//                                                "WindGustKmh", "WindGustKnts", "Pressure1",
//                                                "RainSince9am"};

    public ChartWindow(ArrayList<WeatherObject> objects) throws HeadlessException {
        tableModel = new TableModel(objects);
        table = new JTable(tableModel){
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
        setSize(table.getPreferredSize().width, 300);
        setTitle("Weather Table");
        setVisible(true);
        setLocationRelativeTo(null);
        setLocation(getX() + 200, 50);

    }

}
