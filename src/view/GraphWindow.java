package view;

import controller.DataWindowListener;
import model.Model;
import model.WeatherStation;
import model.WeatherObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberAxis.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.*;
import org.jfree.data.*;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.*;
import java.util.ArrayList;

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

    public GraphWindow(String station){

        this.station = station;
        this.setTitle(station);

        String regex9 = ".*09:00am";
        String regex3 = ".*03:00pm";

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
                morning.add(now,Double.parseDouble(temp.getValue().toString()));
            }
            //when the data is a 3pm temperature data, it's added to evening plot
            if(temp.getKey().toString().matches(regex3)){
                Integer day = Integer.parseInt(temp.getKey().toString().replace("/03:00pm", ""));
                Day now = new Day(day, Calendar.getInstance().get(Calendar.MONTH) + 1,
                        Calendar.getInstance().get(Calendar.YEAR) );
                evening.add(now,Double.parseDouble(temp.getValue().toString()));
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
        setTitle(station + " Temperature Graph");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(graphWindow);
        model.addGraphWindow(this);
        launch();

    }

    public XYDataset createDataset(){

        TimeSeriesCollection tempMornEve = new TimeSeriesCollection();

        tempMornEve.addSeries(morning);
        tempMornEve.addSeries(evening);

        return tempMornEve;
    }

    private void launch(){
        addWindowListener(new DataWindowListener(this));
        model.addOpenWindow(this);
        setSize(new Dimension(900, 350));
        setVisible(true);
        setLocationRelativeTo(null);
        setLocation(getX()+200, 350);
    }

    public void updateGraph(){
        String regex9 = ".*09:00am";
        String regex3 = ".*03:00pm";

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

        graphWindow.remove(0);
        graphWindow.add(cp);

    }

    @Override
    public void relaunch() {
        launch();
    }
}
