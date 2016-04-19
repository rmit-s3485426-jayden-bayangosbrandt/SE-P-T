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
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
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
    XYSeries morning = new XYSeries("9am temp");
    XYSeries evening = new XYSeries("3pm temp");
    private String station;
    JFreeChart chart;
    JPanel graphWindow = new JPanel();

    public GraphWindow(String station){

//        XYDataset dataSet = createDataset(model.getTable());
        // TEST DATA
        this.station = station;
        this.setTitle(station);
        HashMap<String,String> temps = model.getTemp(station);
        Set set = temps.entrySet();
        Iterator iterate = set.iterator();
        String regex9 = ".*09:00am";
        String regex3 = ".*03:00pm";
        while(iterate.hasNext()){
            Map.Entry temp = (Map.Entry)iterate.next();
            if(temp.getKey().toString().matches(regex9))
                morning.add(Double.parseDouble(temp.getKey().toString().replace("/09:00am","")),
                        Double.parseDouble(temp.getValue().toString()));
            if(temp.getKey().toString().matches(regex3))
                evening.add(Double.parseDouble(temp.getKey().toString().replace("/03:00pm","")),
                        Double.parseDouble(temp.getValue().toString()));
        }
        XYDataset dataSet = new XYSeriesCollection(morning);

        chart = ChartFactory.createXYLineChart(
                "Temperature History", "Date", "Temperature",
                dataSet, PlotOrientation.VERTICAL, true, true, false);
                ChartPanel cp = new ChartPanel(chart) {
//        final NumberAxis domainAxis = (NumberAxis)chart.getXYPlot().getDomainAxis();
//        final DecimalFormat format = new DecimalFormat("####");
//        domainAxis.setNumberFormatOverride(format);


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

//    private static XYDataset createDataset(ArrayList<WeatherObject> objects){
//
//        XYSeriesCollection dates = new XYSeriesCollection();
//
//        WeatherObject starting = objects.get(0);
//
//        for (int i = 0; i < objects.size(); i++) {
//
//            if(objects.get(i).getDayTime() == starting.getDayTime()) {
//                XYSeries temp = new XYSeries("9am Temp");
//                temp.add(i,2+1);
//                dates.addSeries(temp);
//            }
//        }
//        return dates;
//    }

    private void launch(){
        addWindowListener(new DataWindowListener(this));
        model.addOpenWindow(this);
        setSize(new Dimension(900, 350));
        setVisible(true);
        setLocationRelativeTo(null);
        setLocation(getX()+200, 350);
    }

    public void updateGraph(){
        HashMap<String,String> temps = model.getTemp(station);
        Set set = temps.entrySet();
        Iterator iterate = set.iterator();
        String regex9 = ".*09:00am";
        String regex3 = ".*03:00pm";
        while(iterate.hasNext()){
            Map.Entry temp = (Map.Entry)iterate.next();
            if(temp.getKey().toString().matches(regex9))
                morning.add(Double.parseDouble(temp.getKey().toString().replace("/09:00am","")),
                        Double.parseDouble(temp.getValue().toString()));
            if(temp.getKey().toString().matches(regex3))
                evening.add(Double.parseDouble(temp.getKey().toString().replace("/03:00pm","")),
                        Double.parseDouble(temp.getValue().toString()));
        }
        XYDataset dataSet = new XYSeriesCollection(morning);

        chart = ChartFactory.createXYLineChart(
                "Temperature History", "Date", "Temperature",
                dataSet, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel cp = new ChartPanel(chart) {
//        final NumberAxis domainAxis = (NumberAxis)chart.getXYPlot().getDomainAxis();
//        final DecimalFormat format = new DecimalFormat("####");
//        domainAxis.setNumberFormatOverride(format);

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
