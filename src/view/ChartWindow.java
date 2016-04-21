package view;

import controller.ChartGraphListener;
import controller.DataWindowListener;
import model.Model;
import model.WeatherObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ChartWindow extends JFrame implements Relaunch {

    private JTable table;
    private TableModel tableModel;
    private Model model = Model.getInstance();
    private String title;
    private Point location;
    private boolean opened;
    private ChartGraphListener actionListener = new ChartGraphListener(this);

    public ChartWindow(String title, int x, int y){
        this.title = title;
        location = new Point(x,y);
    }

    public String getTitleValue(){return title;};

    public Point getLocationValue(){return location;}

    public void setLocationValue(int x, int y){
        location = new Point(x,y);
    }

    public void setOpen(boolean value){
        opened = value;
    }

    public boolean getOpen(){return opened; }

    public ChartWindow(String title) throws HeadlessException {
        this.title = title;

        tableModel = new TableModel(model.getTable(title));
        table = new JTable(tableModel){
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };

        launch();

    }

    private void launch(){
//        addWindowListener(new DataWindowListener(this));
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
        setSize(table.getPreferredSize().width, 300);
        this.title = title + " Weather Table";
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        model.addChartWindow(this);
        addWindowListener(actionListener);
        model.addOpenWindow(this);
        setVisible(true);
        setLocationRelativeTo(null);
        setTitle(title.replace(" Weather Table", "") + " Weather Table");
        if(location!=null)
            setLocation(location);
        else
            setLocation(getX() + 200, 50);
    }

    //function when user presses refresh, getting the data again
    public void updateTable(){
        tableModel = new TableModel(model.getTable(title.replace(" Weather Table","")));
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

    }

    @Override
    public void relaunch() {
        launch();
    }



}
