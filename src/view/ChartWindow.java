package view;

import controller.DataWindowListener;
import model.Model;
import model.WeatherObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ChartWindow extends JFrame implements Relaunch {

    private JTable table;
    private TableModel tableModel;
    private Model model = Model.getInstance();

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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        launch();

    }

    private void launch(){
        addWindowListener(new DataWindowListener(this));
        model.addOpenWindow(this);
        setVisible(true);
        setLocationRelativeTo(null);
        setLocation(getX() + 200, 50);
    }

    @Override
    public void relaunch() {
        launch();
    }



}
