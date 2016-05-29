package view;

import controller.*;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class MainWindow extends JFrame {

    private Model model = Model.getInstance();

    private JLabel lblName = new JLabel();
    private MainWindowListener actionListener;
    private SourceListener sourceListener;
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
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        // WHY DID YOU DO THIS
        mainPanel.setLayout(new BorderLayout());
        this.setContentPane(mainPanel);
        mainPanel.add(getNorthPanel(), BorderLayout.NORTH);
        mainPanel.add(getCenterPanel(), BorderLayout.CENTER);
        mainPanel.add(favePanel, BorderLayout.LINE_END);
//        mainPanel.setSize(new Dimension(700, 200));
        this.setResizable(true);
        this.pack();
        this.setVisible(true);

        // Allow favourite panel to observe user changes
        model.getCurrent().addObserver(favePanel);

        // Set Window listener
        actionListener = new MainWindowListener();
        this.addWindowListener(actionListener);
        this.setLocationRelativeTo(null);

        // Check if open windows present in user
//        if(model.getCurrent().getOpenWindows().size() > 0){
//            relaunch();
//        }


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
        lblName.setText("Welcome, "+model.getCurrent().getUsername()+"!");
        namePanel.add(lblName);
        namePanel.add(Box.createHorizontalGlue());

        north.add(namePanel, 0);
        north.setPreferredSize(new Dimension(north.getWidth(), 30));



        return north;
    }

    private JPanel getCenterPanel(){
        final JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
        JPanel stationSelection = new JPanel();

        area = new JComboBox(areaDataset);
        area.setPreferredSize(new Dimension(area.getWidth(), 15));
        region = new JComboBox(regionDataset);
        region.setPreferredSize(new Dimension(region.getWidth(), 15));
        station = new JComboBox(stationDataset);
        station.setPreferredSize(new Dimension(station.getWidth(), 15));
        btnFavourite = new JButton("Add to Favourites");
        btnFavourite.addActionListener(new AddFavouriteListener(this));
        stationSelection.setLayout(new BoxLayout(stationSelection, BoxLayout.Y_AXIS));

        JPanel panelStationLabel = new JPanel();
        panelStationLabel.setLayout(new BoxLayout(panelStationLabel, BoxLayout.X_AXIS));
        panelStationLabel.add(new JLabel("Please select a weather station"));
        panelStationLabel.add(Box.createHorizontalGlue());

        JPanel panelArea = new JPanel();
        panelArea.setLayout(new BoxLayout(panelArea, BoxLayout.X_AXIS));
        JLabel lblArea = new JLabel("Area: ");
        lblArea.setPreferredSize(new Dimension(120, lblArea.getHeight()));
        panelArea.add(lblArea);
        panelArea.add(area);
        area.addActionListener(new AreaListener(area));

        JPanel panelRegion = new JPanel();
        panelRegion.setLayout(new BoxLayout(panelRegion, BoxLayout.X_AXIS));
        JLabel lblRegion = new JLabel("Region: ");
        lblRegion.setPreferredSize(new Dimension(120, lblRegion.getHeight()));
        panelRegion.add(lblRegion);
        panelRegion.add(region);
        region.setEnabled(false);
        region.addActionListener(new RegionListener(region));

        JPanel panelStation = new JPanel();
        panelStation.setLayout(new BoxLayout(panelStation, BoxLayout.X_AXIS));
        JLabel lblStation = new JLabel("Station: ");
        lblStation.setPreferredSize(new Dimension(120, lblStation.getHeight()));
        panelStation.add(lblStation);
        panelStation.add(station);
        station.setEnabled(false);
        station.addActionListener(new StationListener(station));

        stationSelection.add(panelStationLabel);
        stationSelection.add(panelArea);
        stationSelection.add(panelRegion);
        stationSelection.add(panelStation);
        center.add(stationSelection);

        JPanel forecastPanel = new JPanel();
        forecastPanel.setLayout(new BoxLayout(forecastPanel, BoxLayout.X_AXIS));
        JLabel lblSource = new JLabel("Forecast source: ");
        lblSource.setPreferredSize(new Dimension(120, lblSource.getHeight()));
        forecastPanel.add(lblSource);
        forecastPanel.add(Box.createHorizontalGlue());
        forecastPanel.add(sourcePanel());
        forecastPanel.add(Box.createHorizontalGlue());
        stationSelection.add(forecastPanel);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(btnFavourite);
        btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new RefreshListener());
        bottomPanel.add(btnRefresh);
        bottomPanel.add(Box.createHorizontalGlue());
        stationSelection.add(bottomPanel);



        return center;
    }

    private JPanel sourcePanel() {
        JPanel sourcePanel = new JPanel();
        sourcePanel.setLayout(new BoxLayout(sourcePanel, BoxLayout.X_AXIS));

        this.sourceListener = new SourceListener();

        JRadioButton radForcastIO = new JRadioButton("ForcastIO");
        radForcastIO.addItemListener(sourceListener);
        JRadioButton radOpenWeather = new JRadioButton("OpenWeatherMap");
        radOpenWeather.addItemListener(sourceListener);

        ButtonGroup group = new ButtonGroup();
        group.add(radForcastIO);
        group.add(radOpenWeather);

        // Add radio buttons to panel
        sourcePanel.add(radForcastIO);
        sourcePanel.add(Box.createHorizontalGlue());
        sourcePanel.add(radOpenWeather);

        // Set default selection
        radForcastIO.setSelected(true);


        return sourcePanel;


    }

    public void reposition(){
        this.setLocation(0, getY());
    }

    /**
     * This method changes the drop down menu into the areas that is
     * present in the website
     * @param strings is an array of areas in the website
     */
    public void setAreaDataset(String[] strings){
        this.areaDataset = strings;
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel(strings);
        area.setModel(defaultComboBoxModel);
    }

    /**
     * This method changes the region drop down menu
     * into that which contains all the regions inside a certain area
     * @param strings is the array of regions in the selected area
     */
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
