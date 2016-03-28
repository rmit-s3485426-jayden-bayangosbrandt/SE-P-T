package view;

import model.Model;

import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {

    private JLabel userName = new JLabel("Name");
    private JTextField search = new JTextField("Search weather stations");
    private String[] areaDataset = new String[]{"1", "2", "3"};
    private String[] regionDataset = new String[]{"1", "2", "3"};
    private String[] stationDataset = new String[]{"1", "2", "3"};
    private Model model = Model.getInstance();
    private JComboBox area;
    private JComboBox region;
    private JComboBox station;

    public MainWindow() throws HeadlessException {
        this.setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        this.setContentPane(mainPanel);
        mainPanel.add(getNorthPanel(), BorderLayout.NORTH);
        mainPanel.add(getCenterPanel(), BorderLayout.CENTER);
        this.setSize(new Dimension(500, 500));
        this.setResizable(false);
        this.setVisible(true);

        model.setMainWindow(this);
        model.changeAreaDataset();
    }

    private JPanel getNorthPanel(){
        JPanel north = new JPanel();
//        north.setSize(new Dimension(50, 50));
        north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
        JPanel namePanel = new JPanel();
        JPanel favePanel = new JPanel();

        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.add(Box.createHorizontalGlue());
        namePanel.add(userName);
        namePanel.add(Box.createHorizontalGlue());
//        namePanel.add(Box.createRigidArea(new Dimension(300,0)), 1);

        favePanel.setLayout(new BoxLayout(favePanel, BoxLayout.Y_AXIS));
//        favePanel.setPreferredSize(new Dimension(50, 20));
        // Setup favourite list component
        JList list = new JList(areaDataset);
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
        north.add(Box.createHorizontalGlue(), 1);
        north.add(favePanel, 2);



        return north;
    }

    private JPanel getCenterPanel(){
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
        JPanel stationSelection = new JPanel();
        JPanel stationSearch = new JPanel();
        area = new JComboBox(areaDataset);
        region = new JComboBox(regionDataset);
        station = new JComboBox(stationDataset);
        stationSelection.setLayout(new BoxLayout(stationSelection, BoxLayout.Y_AXIS));

        JPanel panelArea = new JPanel();
        panelArea.setLayout(new BoxLayout(panelArea, BoxLayout.X_AXIS));
        panelArea.add(new JLabel("Area: "));
        panelArea.add(area);

        JPanel panelRegion = new JPanel();
        panelRegion.setLayout(new BoxLayout(panelRegion, BoxLayout.X_AXIS));
        panelRegion.add(new JLabel("Region: "));
        panelRegion.add(region);

        JPanel panelStation = new JPanel();
        panelStation.setLayout(new BoxLayout(panelStation, BoxLayout.X_AXIS));
        panelStation.add(new JLabel("Station: "));
        panelStation.add(station);

        stationSelection.add(new JLabel("Please select a weather station"), 0);
        stationSelection.add(panelArea, 1);
        stationSelection.add(panelRegion, 2);
        stationSelection.add(panelStation, 3);
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

    public void setAreaDataset(String[] strings){
        this.areaDataset = strings;
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel(strings);
        area.setModel(defaultComboBoxModel);
    }

    public void setRegionDataset(String[] strings){
        this.regionDataset = strings;
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel(strings);
        area.setModel(defaultComboBoxModel);
    }

    public void setStationDataset(String[] strings){
        this.stationDataset = strings;
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel(strings);
        area.setModel(defaultComboBoxModel);
    }
}
