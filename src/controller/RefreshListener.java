package controller;

import model.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jayden on 16/04/2016.
 */
public class RefreshListener implements ActionListener {

    private Model model = Model.getInstance();

    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                model.refresh();
            }
        }).start();
//        model.refresh();
    }
}
