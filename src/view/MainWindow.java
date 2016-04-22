package view;

import controller.*;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class MainWindow extends JFrame {

    private Model model = Model.getInstance();

    private JLabel lblName = new JLabel(model.getCurrent().getUsername());
    private MainWindowListener actionListener;
    private String[] areaDataset = new String[]{};
    private String[] regionDataset = new String[]{};
    private String[] stationDataset = new String[]{};
    private FavouritePanel favePanel = new FavouritePanel(this);
    private JComboBox area;
    private JComboBox region;
    private JComboBox station;
    private JButton btnFavourite;
    private JButton btnRefresh;

    public MainWindow() throws HeadlessException {
        this.setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        this.setContentPane(mainPanel);
        mainPanel.add(getNorthPanel(), BorderLayout.NORTH);
        mainPanel.add(getCenterPanel(), BorderLayout.CENTER);
        this.setSize(new Dimension(500, 500));
        this.setResizable(true);
        this.setVisible(true);

        // Allow favourite panel to observe user changes
        model.getCurrent().addObserver(favePanel);

        // Set Window listener
        actionListener = new MainWindowListener();
        this.addWindowListener(actionListener);
        this.setLocationRelativeTo(null);

        // Check if open windows present in user
        if(model.getCurrent().getOpenWindows().size() > 0){
            relaunch();
        }


    }

    private void relaunch(){
        ArrayList<JFrame> windows = model.getCurrent().getOpenWindows();
//        for(JFrame window : windows){
//            Relaunch frame = (Relaunch) window;
//            frame.relaunch();
//        }
    }

    private JPanel getNorthPanel(){
        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
        JPanel namePanel = new JPanel();

        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.add(Box.createHorizontalGlue());
        namePanel.add(lblName);
        namePanel.add(Box.createHorizontalGlue());

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
        btnFavourite.addActionListener(new AddFavouriteListener(this));
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

        stationSelection.add(panelStationLabel);
        stationSelection.add(panelArea);
        stationSelection.add(panelRegion);
        stationSelection.add(panelStation);
        center.add(stationSelection);
        JPanel addFavourite = new JPanel();
        addFavourite.setLayout(new BoxLayout(addFavourite, BoxLayout.X_AXIS));
        addFavourite.add(Box.createHorizontalGlue());
        addFavourite.add(btnFavourite);
        btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new RefreshListener());
        addFavourite.add(btnRefresh);
        addFavourite.add(Box.createHorizontalGlue());
        stationSelection.add(addFavourite);
//        center.add(Box.createHorizontalStrut(100), 1);
//        center.add(stationSearch, 2);

        return center;
    }

    public void reposition(){
        this.setLocation(0, getY());
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
