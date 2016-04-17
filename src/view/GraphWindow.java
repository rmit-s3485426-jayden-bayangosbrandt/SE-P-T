package view;

import controller.DataWindowListener;
import model.Model;
import model.WeatherStation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberAxis.*;
import org.jfree.chart.plot.PlotOrientation;
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

/**
 * Created by YungYung on 16/04/2016.
 */
public class GraphWindow extends JFrame implements Relaunch {

    private Model model = Model.getInstance();

    public GraphWindow(){

        // TEST DATA
        HashMap<String,String> temps = model.getTemp();
        XYSeries morning = new XYSeries("9am temp");
        XYSeries evening = new XYSeries("3pm temp");
        Set set = temps.entrySet();
        Iterator iterate = set.iterator();
        String regex9 = ".*09:00am";
        String regex3 = ".*03.00pm";
        while(iterate.hasNext()){
            Map.Entry temp = (Map.Entry)iterate.next();
            if(temp.getKey().toString().matches(regex9))
                morning.add(Double.parseDouble(temp.getKey().toString().replace("/09:00am","")),
                        Double.parseDouble(temp.getValue().toString()));
            if(temp.getKey().toString().matches(regex3))
                evening.add(Double.parseDouble(temp.getKey().toString().replace("/03:00pm","")),
                        Double.parseDouble(temp.getValue().toString()));
        }
//
//        morning.add(1, 13);
//        morning.add(2, 12);
//        morning.add(3, 15);
//        morning.add(4, 16);
//        morning.add(5, 14);
//
//
//        evening.add(1, 17);
//        evening.add(2, 20);
//        evening.add(3, 19);
//        evening.add(4, 18);
//        evening.add(5, 17);


        XYDataset dataSet = new XYSeriesCollection(morning);

        final JFreeChart chart = ChartFactory.createXYLineChart(
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

        JPanel graphWindow = new JPanel();

        graphWindow.add(cp);
        setTitle("Temperature Graph");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(graphWindow);
        launch();

    }

    private void launch(){
        addWindowListener(new DataWindowListener(this));
        model.addOpenWindow(this);
        setSize(new Dimension(900, 350));
        setVisible(true);
        setLocationRelativeTo(null);
        setLocation(getX()+200, 350);
    }

    @Override
    public void relaunch() {
        launch();
    }
}
