package view;

import javax.swing.*;
import java.awt.*;


public class Window extends JFrame {

    private JLabel userName = new JLabel("Name");
    private JTextField search = new JTextField("Search weather stations");
    private Object[] objects = new Object[]{"1", "2", "3"};

    public Window() throws HeadlessException {
        this.setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        this.setContentPane(mainPanel);
        mainPanel.add(getNorthPanel(), BorderLayout.NORTH);
        mainPanel.add(getCenterPanel(), BorderLayout.CENTER);
        this.setSize(new Dimension(500, 500));
        this.setResizable(false);
        this.setVisible(true);
    }

    private JPanel getNorthPanel(){
        JPanel north = new JPanel();
//        north.setSize(new Dimension(50, 50));
        north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
        JPanel namePanel = new JPanel();
        JPanel favePanel = new JPanel();

        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.add(userName, 0);
//        namePanel.add(Box.createRigidArea(new Dimension(300,0)), 1);

        favePanel.setLayout(new BoxLayout(favePanel, BoxLayout.Y_AXIS));
//        favePanel.setPreferredSize(new Dimension(50, 20));
        // Setup favourite list component
        JList list = new JList(objects);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
//        list.setPreferredSize(new Dimension(50, 20));
        list.setVisibleRowCount(3);
        JScrollPane scrollPane = new JScrollPane(list);
//        scrollPane.setPreferredSize(new Dimension(50, 20));
        // Add component to panel
        favePanel.add(new JLabel("Favourites"));
        favePanel.add(scrollPane, 1);

        north.add(namePanel, 0);
        north.add(Box.createRigidArea(new Dimension(300,0)), 1);
        north.add(favePanel, 2);



        return north;
    }

    private JPanel getCenterPanel(){
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
        JPanel stationSelection = new JPanel();
        JPanel stationSearch = new JPanel();
        JComboBox area = new JComboBox(objects);
        JComboBox region = new JComboBox(objects);
        JComboBox station = new JComboBox(objects);
        stationSelection.setLayout(new BoxLayout(stationSelection, BoxLayout.Y_AXIS));
        stationSelection.add(new JLabel("Please select a weather station"), 0);
        stationSelection.add(area, 1);
        stationSelection.add(region, 2);
        stationSelection.add(station, 3);
        stationSearch.setLayout(new BoxLayout(stationSearch, BoxLayout.Y_AXIS));
        stationSearch.add(new JLabel("Or search for a specific station"), 0);
        stationSearch.add(search, 1);
        stationSearch.add(Box.createVerticalStrut(100));
        center.add(stationSelection, 0);
        center.add(Box.createHorizontalStrut(100), 1);
        center.add(stationSearch, 2);

        return center;
    }

    public JLabel getUserName() {
        return userName;
    }

    public void setUserName(JLabel userName) {
        this.userName = userName;
    }

    public JTextField getSearch() {
        return search;
    }

    public void setSearch(JTextField search) {
        this.search = search;
    }
}
