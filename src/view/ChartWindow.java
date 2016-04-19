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
    private String title;

    public ChartWindow(String title) throws HeadlessException {
        this.title = title;

        tableModel = new TableModel(model.getTable(title));
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
        setTitle(title + " Weather Table");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        model.addChartWindow(this);
        launch();

    }

    private void launch(){
        addWindowListener(new DataWindowListener(this));
        model.addOpenWindow(this);
        setVisible(true);
        setLocationRelativeTo(null);
        setLocation(getX() + 200, 50);
    }

    //function when user presses refresh, getting the data again
    public void updateTable(){
        tableModel = new TableModel(model.getTable(title));
        table = new JTable(tableModel){
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
        getContentPane().remove(0);

        setSize(table.getPreferredSize().width, 300);

    }

    @Override
    public void relaunch() {
        launch();
    }



}
