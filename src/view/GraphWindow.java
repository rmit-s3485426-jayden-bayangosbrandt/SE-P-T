package view;

import controller.DataWindowListener;
import model.Model;
import model.WeatherObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by YungYung on 16/04/2016.
 */
public class GraphWindow extends JFrame implements Relaunch {

    private Model model = Model.getInstance();

    public GraphWindow(){

        XYDataset dataSet = createDataset(model.getTable());

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Temperature History", "Date", "Temperature",
                dataSet, PlotOrientation.VERTICAL, true, true, false);
                ChartPanel cp = new ChartPanel(chart) {

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

    private static XYDataset createDataset(ArrayList<WeatherObject> objects){

        XYSeriesCollection dates = new XYSeriesCollection();

        WeatherObject starting = objects.get(0);

        for (int i = 0; i < objects.size(); i++) {

            if(objects.get(i).getDayTime() == starting.getDayTime()) {
                XYSeries temp = new XYSeries("9am Temp");
                temp.add(i,2+1);
                dates.addSeries(temp);
            }
        }
        return dates;
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
