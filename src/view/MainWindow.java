package view;

import model.Model;

import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {

    private JLabel lblName = new JLabel("Name");
    private JTextField search = new JTextField("Search weather stations");
    private String[] areaDataset = new String[]{"1", "2", "3"};
    private String[] regionDataset = new String[]{"1", "2", "3"};
    private String[] stationDataset = new String[]{"1", "2", "3"};
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


    }

    private JPanel getNorthPanel(){
        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
        JPanel namePanel = new JPanel();
        JPanel favePanel = new JPanel();

        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.add(Box.createHorizontalGlue());
        namePanel.add(lblName);
        namePanel.add(Box.createHorizontalGlue());

        favePanel.setLayout(new BoxLayout(favePanel, BoxLayout.Y_AXIS));
        // Setup favourite list component
        JList list = new JList(areaDataset);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
//        list.setVisibleRowCount(3);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(10, 10));
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

        area = new JComboBox(areaDataset);
        region = new JComboBox(regionDataset);
        station = new JComboBox(stationDataset);
        stationSelection.setLayout(new BoxLayout(stationSelection, BoxLayout.Y_AXIS));

        JPanel panelStationLabel = new JPanel();
        panelStationLabel.setLayout(new BoxLayout(panelStationLabel, BoxLayout.X_AXIS));
        panelStationLabel.add(Box.createHorizontalGlue());
        panelStationLabel.add(new JLabel("Please select a weather station"));
        panelStationLabel.add(Box.createHorizontalGlue());

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

//        stationSelection.add(new JLabel("Please select a weather station"));
        stationSelection.add(panelStationLabel);
        stationSelection.add(panelArea);
        stationSelection.add(panelRegion);
        stationSelection.add(panelStation);
//        stationSearch.setLayout(new BoxLayout(stationSearch, BoxLayout.Y_AXIS));
//        stationSearch.add(new JLabel("Or search for a specific station"), 0);
//        stationSearch.add(search, 1);
//        stationSearch.add(Box.createVerticalStrut(100));
        center.add(stationSelection);
//        center.add(Box.createHorizontalStrut(100), 1);
//        center.add(stationSearch, 2);

        return center;
    }

    public JLabel getLblName() {
        return lblName;
    }
    
    public void setAreaDataset(String[] strings){
        this.areaDataset = strings;
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel(strings);
        area.setModel(defaultComboBoxModel);
    }

    public void setRegionDataset(String[] strings){
        this.regionDataset = strings;
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel(strings);
        region.setModel(defaultComboBoxModel);
    }

    public void setStationDataset(String[] strings){
        this.stationDataset = strings;
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel(strings);
        station.setModel(defaultComboBoxModel);
    }

    public void setLblName(String name){
        lblName.setText("Hi, "+name);
    }


}
