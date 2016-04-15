package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

/**
 * Created by YungYung on 16/04/2016.
 */
public class GraphWindow extends JFrame {

    public GraphWindow(){

        // TEST DATA
        XYSeries morning = new XYSeries("9am temp");
        morning.add(1, 13);
        morning.add(2, 12);
        morning.add(3, 15);
        morning.add(4, 16);
        morning.add(5, 14);

        XYSeries evening = new XYSeries("3pm temp");
        evening.add(1, 17);
        evening.add(2, 20);
        evening.add(3, 19);
        evening.add(4, 18);
        evening.add(5, 17);


        XYDataset dataSet = new XYSeriesCollection(morning);

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
        setContentPane(graphWindow);
        setSize(new Dimension(900, 350));
        setVisible(true);
        setLocationRelativeTo(null);
        setLocation(getX()+200, 350);

    }
}
