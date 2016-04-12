package view;

import controller.FavouriteListener;
import model.Model;
import model.WeatherStation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class FavouritePanel extends JPanel implements Observer {

    private Model model = Model.getInstance();
    private JList<String> faveList;
    private String[] faveDataset = new String[]{"1", "2", "3"};
    private JFrame frame;
    private JScrollPane scrollPane;

    public FavouritePanel(JFrame frame){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        faveList = new JList(getListModel(model.getCurrent().getFavorites()));
        faveList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        faveList.setLayoutOrientation(JList.VERTICAL);
        faveList.addListSelectionListener(new FavouriteListener(faveList, frame));

        scrollPane = new JScrollPane(faveList);
        scrollPane.setPreferredSize(new Dimension(10, 10));

        // Add component to panel
        add(new JLabel("Favourites"));
        add(scrollPane, 1);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Update");
        faveList.setModel(getListModel(model.getCurrent().getFavorites()));
        scrollPane.repaint();

    }

    private DefaultListModel getListModel(ArrayList<WeatherStation> faves){
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for(WeatherStation w : faves){
            listModel.addElement(w.getName());
        }
        return listModel;

    }
}
