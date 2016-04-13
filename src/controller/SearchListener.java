package controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Created by YungYung on 13/04/2016.
 */
public class SearchListener implements DocumentListener {

    private JTextField field;
    private JList list;

    public SearchListener(JTextField textField, JList localList){ this.field = textField; this.list = localList;}

    @Override
    public void insertUpdate(DocumentEvent e) {
        list.setSelectedValue(field.getText(),true);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}