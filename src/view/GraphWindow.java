package view;

import controller.ChartGraphListener;
import controller.DataWindowListener;
import controller.ZoomInListener;
import controller.ZoomOutListener;
import model.Model;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by YungYung on 16/04/2016.
 */
public class GraphWindow extends JFrame implements Relaunch {

    private Model model = Model.getInstance();
    TimeSeries morning = new TimeSeries("9am temp");
    TimeSeries evening = new TimeSeries("3pm temp");
    private String station;
    JFreeChart chart;
    JPanel graphWindow = new JPanel();
    private Point location;
    private ChartGraphListener actionListener = new ChartGraphListener(this);
    private boolean opened;

    public GraphWindow(String station, int x, int y){
        this.station = station;
        location = new Point(x,y);
    }

    public String getTitleValue(){return station;};


    public Point getLocationValue(){return location;}

    public void setLocationValue(int x, int y){
        location = new Point(x,y);
    }

    public void setOpen(boolean value){
        opened = value;
    }

    public boolean getOpen(){return opened; }

    public GraphWindow(String station){

        addWindowListener(actionListener);

        this.station = station;
        this.setTitle(station);

        String regex9 = ".*09:00am";
        String regex3 = ".*03:00pm";
        String regex15 = ".*15:00pm";
        int tempDay = 0;

        //getting data from the website & from temperature history
        HashMap<String,String> temps = model.getTemp(station);
        HashMap<String,String> historyTemps = model.checkHistory(station);

        //adding data from temperature history before getting data from website
        Set historySet = historyTemps.entrySet();
        Iterator iterateHistory = historySet.iterator();
        while(iterateHistory.hasNext()) {
            Map.Entry temp = (Map.Entry)iterateHistory.next();

            //when the data is a 9am temperature data, it's added to morning plot
            if(temp.getKey().toString().matches(regex9)){
                //changing the date format so it can be added into the plot
                Integer day = Integer.parseInt(temp.getKey().toString().replace("/09:00am", ""));
                Day now = new Day(day, Calendar.getInstance().get(Calendar.MONTH) + 1,
                        Calendar.getInstance().get(Calendar.YEAR) );
                tempDay = day;
                morning.add(now,Double.parseDouble(temp.getValue().toString()));
            }
            //when the data is a 3pm temperature data, it's added to evening plot
            if(temp.getKey().toString().matches(regex3)){
                Integer day = Integer.parseInt(temp.getKey().toString().replace("/03:00pm", ""));
                Day now = new Day(day, Calendar.getInstance().get(Calendar.MONTH) + 1,
                        Calendar.getInstance().get(Calendar.YEAR) );
                evening.add(now,Double.parseDouble(temp.getValue().toString()));
                tempDay = day;
            }
            if(temp.getKey().toString().matches(regex15)){
                Integer day = Integer.parseInt(temp.getKey().toString().replace("/15:00pm", ""));
                Day now = new Day(day, Calendar.getInstance().get(Calendar.MONTH) + 1,
                        Calendar.getInstance().get(Calendar.YEAR) );
                evening.add(now,Double.parseDouble(temp.getValue().toString()));
                tempDay = day;
            }
        }

        //handling data got from website
        Set set = temps.entrySet();
        Iterator iterate = set.iterator();
        while(iterate.hasNext()){
            Map.Entry temp = (Map.Entry)iterate.next();
            //when the data is a 9am temperature data, it's either added or updated to morning plot
            if(temp.getKey().toString().matches(regex9)) {
                Integer day = Integer.parseInt(temp.getKey().toString().replace("/09:00am", ""));
                Day now = new Day(day, Calendar.getInstance().get(Calendar.MONTH) + 1,
                        Calendar.getInstance().get(Calendar.YEAR) );
                if(temp.getValue().toString().equals("-"))
                    continue;
                morning.addOrUpdate(now,
                        Double.parseDouble(temp.getValue().toString()));
                //adding possibly new temperature data to history
                model.addHistory(day.toString(), "9", temp.getValue().toString(), station);

            }
            //when the data is a 3pm temperature data, it's either added or updated to evening plot
            if(temp.getKey().toString().matches(regex3)) {
                Integer day = Integer.parseInt(temp.getKey().toString().replace("/03:00pm", ""));
                Day now = new Day(day, Calendar.getInstance().get(Calendar.MONTH)  + 1,
                        Calendar.getInstance().get(Calendar.YEAR) );
                if(temp.getValue().toString().equals("-"))
                    continue;
                evening.addOrUpdate(now,
                        Double.parseDouble(temp.getValue().toString()));
                //adding possibly new temperature data to history
                model.addHistory(day.toString(), "3", temp.getValue().toString(), station);

            }
            if(temp.getKey().toString().matches(regex15)) {
                Integer day = Integer.parseInt(temp.getKey().toString().replace("/15:00pm", ""));
                Day now = new Day(day, Calendar.getInstance().get(Calendar.MONTH)  + 1,
                        Calendar.getInstance().get(Calendar.YEAR) );
                if(temp.getValue().toString().equals("-"))
                    continue;
                evening.addOrUpdate(now,
                        Double.parseDouble(temp.getValue().toString()));
                //adding possibly new temperature data to history
                model.addHistory(day.toString(), "3", temp.getValue().toString(), station);

            }
        }
        XYDataset dataSet = createDataset();

        chart = ChartFactory.createTimeSeriesChart("Temperature History",
                "Date", "Temperature", dataSet, true, true, false);

                ChartPanel cp = new ChartPanel(chart) {
                    @Override
                public Dimension getPreferredSize() {
                    return new Dimension(700,300);
                }
        };


        graphWindow.add(cp);

        this.station = station;

        model.addOpenWindow(this);


        launch();

    }

    public XYDataset createDataset(){

        TimeSeriesCollection tempMornEve = new TimeSeriesCollection();

        tempMornEve.addSeries(morning);
        tempMornEve.addSeries(evening);

        return tempMornEve;
    }

    private void launch(){

        // Zoom in and out check boxes
        JPanel zoomPanel = new JPanel();
        zoomPanel.setLayout(new BoxLayout(zoomPanel, BoxLayout.Y_AXIS));
        JButton zoomIn = new JButton("Zoom In +");
        JButton zoomOut = new JButton("Zoom Out -");
        zoomPanel.add(zoomIn);
        zoomIn.addActionListener(new ZoomInListener());
        zoomPanel.add(zoomOut);
        zoomOut.addActionListener(new ZoomOutListener());
        graphWindow.add(zoomPanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(graphWindow);
        addWindowListener(new DataWindowListener(this));
        model.addGraphWindow(this);
        setSize(new Dimension(900, 350));
        setTitle(station);
        setVisible(true);
        setLocationRelativeTo(null);
        setLocation(getX() + 200, 350);
    }


    /**
     * this method updates the graph of the weatherstation so that it shows all the history and
     * necessary info we want it to show
     */
    public void updateGraph(){
        station = station;
        station = station;
        String regex9 = ".*09:00am";
        String regex3 = ".*03:00pm";
        String regex15 = ".*15:00pm";

        //getting data from the website & from temperature history
        HashMap<String,String> temps = model.getTemp(station);
        HashMap<String,String> historyTemps = model.checkHistory(station);

        //adding data from temperature history before getting data from website
        Set historySet = historyTemps.entrySet();
        Iterator iterateHistory = historySet.iterator();
        while(iterateHistory.hasNext()) {
            Map.Entry temp = (Map.Entry)iterateHistory.next();
            //when the data is a 9am temperature data, it's either added or updated to morning plot
            if(temp.getKey().toString().matches(regex9)){
                //changing the date format so it can be added into the plot
                Integer day = Integer.parseInt(temp.getKey().toString().replace("/09:00am", ""));
                Day now = new Day(day, Calendar.getInstance().get(Calendar.MONTH) + 1,
                        Calendar.getInstance().get(Calendar.YEAR) );
                morning.addOrUpdate(now,Double.parseDouble(temp.getValue().toString()));
            }
            //when the data is a 3pm temperature data, it's either added or updated to evening plot
            if(temp.getKey().toString().matches(regex3)){
                Integer day = Integer.parseInt(temp.getKey().toString().replace("/03:00pm", ""));
                Day now = new Day(day, Calendar.getInstance().get(Calendar.MONTH) + 1,
                        Calendar.getInstance().get(Calendar.YEAR) );
                evening.addOrUpdate(now,Double.parseDouble(temp.getValue().toString()));
            }
            if(temp.getKey().toString().matches(regex15)){
                Integer day = Integer.parseInt(temp.getKey().toString().replace("/15:00pm", ""));
                Day now = new Day(day, Calendar.getInstance().get(Calendar.MONTH) + 1,
                        Calendar.getInstance().get(Calendar.YEAR) );
                evening.addOrUpdate(now,Double.parseDouble(temp.getValue().toString()));
            }
        }

        //handling data got from website
        Set set = temps.entrySet();
        Iterator iterate = set.iterator();
        while(iterate.hasNext()){
            Map.Entry temp = (Map.Entry)iterate.next();
            //when the data is a 9am temperature data, it's either added or updated to morning plot
            if(temp.getKey().toString().matches(regex9)) {
                Integer day = Integer.parseInt(temp.getKey().toString().replace("/09:00am", ""));
                Day now = new Day(day, Calendar.getInstance().get(Calendar.MONTH)  + 1,
                        Calendar.getInstance().get(Calendar.YEAR) );
                morning.addOrUpdate(now,
                        Double.parseDouble(temp.getValue().toString()));
                //adding possibly new temperature data to history
                model.addHistory(day.toString(), "9", temp.getValue().toString(), station);

            }
            //when the data is a 3pm temperature data, it's either added or updated to evening plot
            if(temp.getKey().toString().matches(regex3)) {

                Integer day = Integer.parseInt(temp.getKey().toString().replace("/03:00pm", ""));
                Day now = new Day(day, Calendar.getInstance().get(Calendar.MONTH) + 1,
                        Calendar.getInstance().get(Calendar.YEAR) );
                System.out.println(temp.getValue().toString());
                evening.addOrUpdate(now,
                        Double.parseDouble(temp.getValue().toString()));
                //adding possibly new temperature data to history
                model.addHistory(day.toString(), "3", temp.getValue().toString(), station);

            }
            if(temp.getKey().toString().matches(regex15)) {

                Integer day = Integer.parseInt(temp.getKey().toString().replace("/15:00pm", ""));
                Day now = new Day(day, Calendar.getInstance().get(Calendar.MONTH) + 1,
                        Calendar.getInstance().get(Calendar.YEAR) );
                System.out.println(temp.getValue().toString());
                evening.addOrUpdate(now,
                        Double.parseDouble(temp.getValue().toString()));
                //adding possibly new temperature data to history
                model.addHistory(day.toString(), "3", temp.getValue().toString(), station);

            }
        }
        XYDataset dataSet = createDataset();

        chart = ChartFactory.createTimeSeriesChart("Temperature History",
                                                        "Date", "Temperature", dataSet, true, true, false);
        ChartPanel cp = new ChartPanel(chart) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(700,300);
            }
        };

        graphWindow.add(cp);

    }

    @Override
    public void relaunch() {
        launch();
    }
}
