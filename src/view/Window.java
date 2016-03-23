package view;

import javax.swing.*;
import java.awt.*;


public class Window extends JFrame {

    private JLabel userName = new JLabel("Name");
    private Object[] objects = new Object[]{"1", "2", "3"};

    public Window() throws HeadlessException {
        this.setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        this.setContentPane(mainPanel);
        mainPanel.add(getNorthPanel(), BorderLayout.NORTH);
        this.setSize(500, 500);
        this.setResizable(false);
        this.setVisible(true);
    }

    private JPanel getNorthPanel(){
        JPanel north = new JPanel();
        north.setPreferredSize(new Dimension(500, 100));
        north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
        JPanel namePanel = new JPanel();
        JPanel favePanel = new JPanel();

        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.add(userName, 0);
//        namePanel.add(Box.createRigidArea(new Dimension(300,0)), 1);

        favePanel.setLayout(new BoxLayout(favePanel, BoxLayout.Y_AXIS));
        // Setup favourite list component
        JList list = new JList(objects);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(3);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(100, 20));
        // Add component to panel
        favePanel.add(new JLabel("Favourites"));
        favePanel.add(scrollPane, 1);

        north.add(namePanel, 0);
        north.add(Box.createRigidArea(new Dimension(300,0)), 1);
        north.add(favePanel, 2);



        return north;
    }

    public JLabel getUserName() {
        return userName;
    }

    public void setUserName(JLabel userName) {
        this.userName = userName;
    }
}
