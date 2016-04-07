package view;

import controller.AreaListener;
import controller.RegionListener;
import controller.StationListener;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainWindow extends JFrame {

    private JLabel lblName = new JLabel("Name");
//    private JTextField search = new JTextField("Search weather stations");
    private String[] areaDataset = new String[]{"1", "2", "3"};
    private String[] regionDataset = new String[]{"1", "2", "3"};
    private String[] stationDataset = new String[]{"1", "2", "3"};
    private JComboBox area;
    private JComboBox region;
    private JComboBox station;
    private JButton btnFavourite;
    private Model model = Model.getInstance();

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
        final JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
        JPanel stationSelection = new JPanel();

        area = new JComboBox(areaDataset);
        region = new JComboBox(regionDataset);
        station = new JComboBox(stationDataset);
        btnFavourite = new JButton("Add to Favourites");
        btnFavourite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainWindow.this, "Fuck trees!!!");
                model.setStationUrl(getStationSelected());
            }
        });
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
        area.addActionListener(new AreaListener(area));

        JPanel panelRegion = new JPanel();
        panelRegion.setLayout(new BoxLayout(panelRegion, BoxLayout.X_AXIS));
        panelRegion.add(new JLabel("Region: "));
        panelRegion.add(region);
        region.setEnabled(false);
        region.addActionListener(new RegionListener(region));

        JPanel panelStation = new JPanel();
        panelStation.setLayout(new BoxLayout(panelStation, BoxLayout.X_AXIS));
        panelStation.add(new JLabel("Station: "));
        panelStation.add(station);
        station.setEnabled(false);
        station.addActionListener(new StationListener(station));

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
        JPanel addFavourite = new JPanel();
        addFavourite.setLayout(new BoxLayout(addFavourite, BoxLayout.X_AXIS));
        addFavourite.add(Box.createHorizontalGlue());
        addFavourite.add(btnFavourite);
        addFavourite.add(Box.createHorizontalGlue());
        stationSelection.add(addFavourite);
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

    public boolean isAreaSelected(){
        if(area.getSelectedIndex() == 0){
            return false;
        }

        return true;
    }

    public boolean isRegionSelected(){
        if(region.getSelectedIndex() == 0){
            return false;
        }

        return true;
    }

    public String getRegionSelected(){
        return (String) region.getSelectedItem();
    }

    public boolean isStationSelected(){
        if(station.getSelectedIndex() == 0){
            return false;
        }

        return true;
    }

    public String getStationSelected(){
        return (String) station.getSelectedItem();
    }

    public void setStationEnabled(boolean enabled){
        station.setEnabled(enabled);
    }

    public void setRegionEnabled(boolean enabled){
        region.setEnabled(enabled);
    }




}
