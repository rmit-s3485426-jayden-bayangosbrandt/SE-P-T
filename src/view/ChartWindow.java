package view;

import controller.ChartGraphListener;
import controller.DataWindowListener;
import model.Model;

import javax.swing.*;
import javax.swing.table.TableColumn;
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
    private ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

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

        model.addOpenWindow(this);
        launch();

    }

    private void launch(){
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(addCheckboxes(table.getColumnCount()));
        // Adds panel containing checkboxes
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        // Adds table
        mainPanel.add(table);

        getContentPane().add(scrollPane);
        setSize(table.getPreferredSize().width, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        model.addChartWindow(this);
        addWindowListener(actionListener);
        setVisible(true);
        setLocationRelativeTo(null);
        setTitle(title);
        if(location!=null)
            setLocation(location);
        else
            setLocation(getX() + 200, 50);


    }

    private JPanel addCheckboxes(int columnCount) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        for(int i = 0; i<columnCount; i++) {
            JCheckBox checkBox = new JCheckBox(table.getColumnName(i));
//            TableColumn column = table.getColumnModel().getColumn(i);
//            checkBox.setPreferredSize(new Dimension(column.getWidth(), checkBox.getHeight()));
            this.checkBoxes.add(checkBox);
            panel.add(checkBox);
        }

        return panel;
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
