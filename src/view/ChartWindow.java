package view;

import controller.ChartGraphListener;
import controller.ColumnCheckedListener;
import controller.DataWindowListener;
import model.Model;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class ChartWindow extends JFrame implements Relaunch {

    private JTable table;
    private TableModel tableModel;
    private Model model = Model.getInstance();
    private String title;
    private Point location;
    private boolean opened;
    private ColumnCheckedListener checkedListener = new ColumnCheckedListener();
    private ChartGraphListener actionListener = new ChartGraphListener(this);
    private ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
    private final static Logger LOGGER = Logger.getLogger(ChartWindow.class.getName());

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

        int currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int rowStart = 0;
        int tempRowVar = 0;

        LOGGER.info("currentDate is: " + currentDate);
        tableModel = new TableModel(model.getTable(title.replace(" Weather Table","")));
        table = new JTable(tableModel){
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };

        for( int j = 0; j < table.getRowCount(); j++, tempRowVar = j){


            String date = table.getValueAt(j,0).toString().substring(0,2);
            if( date.contains("/")){
                date = table.getValueAt(j,0).toString().substring(0,1);
            }
            Integer day = Integer.parseInt(date);

            //LOGGER.info("tempRowVar is: " + tempRowVar);
            if(day > currentDate){
                rowStart = j;
                break;
            }
        }

        final int m = rowStart;

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row >= m ? Color.red: Color.GREEN);
                c.setForeground(row >= m ? Color.WHITE: Color.BLACK);
                return c;
            }
        });

        table.setFont(new Font("Arial", Font.BOLD, 12));

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
            checkBox.addItemListener(this.checkedListener);
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
