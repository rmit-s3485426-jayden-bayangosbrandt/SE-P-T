package view;


import model.Model;
import model.User;
import controller.UserListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WelcomeWindow extends JFrame {

    private Model model = Model.getInstance();

    private JTextField newUser = new JTextField("Type your name here");
    private JButton register = new JButton("Register");
    private JTextField searchUsers = new JTextField("Search your username here");
    private JList existingUsers = new JList(stringUsers(model.getUserList()));


    public WelcomeWindow() throws HeadlessException {
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        setContentPane(contentPanel);
        contentPanel.add(getWelcomePanel(), 0);
        contentPanel.add(getRegisterPanel(), 1);
        contentPanel.add(new JLabel("or choose from existing below:"), 2);
        contentPanel.add(getExistingUserPanel(), 3);
        setSize(new Dimension(500, 300));
        setVisible(true);
    }

    private JPanel getWelcomePanel(){
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.X_AXIS));
        welcomePanel.add(Box.createHorizontalGlue());
        welcomePanel.add(new JLabel("WELCOME"));
        welcomePanel.add(Box.createHorizontalGlue());
        return welcomePanel;
    }

    private JPanel getRegisterPanel(){
        JPanel registerPanel = new JPanel();
        newUser.setMaximumSize(new Dimension(300, 30));
        newUser.setPreferredSize(new Dimension(300, 30));
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.X_AXIS));
        registerPanel.add(Box.createHorizontalGlue(), 0);
        registerPanel.add(new JLabel("New user?"), 1);
        registerPanel.add(Box.createHorizontalStrut(5), 2);
        registerPanel.add(newUser, 3);
        registerPanel.add(register, 4);
        registerPanel.add(Box.createHorizontalGlue(), 5);
        return registerPanel;
    }

    private JPanel getExistingUserPanel(){
        JPanel existingPanel = new JPanel();
        JPanel scrollPanel = new JPanel();
        JPanel searchUsersPanel = new JPanel();
        existingPanel.setLayout(new BoxLayout(existingPanel, BoxLayout.Y_AXIS));
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.X_AXIS));
        searchUsersPanel.setLayout(new BoxLayout(searchUsersPanel, BoxLayout.X_AXIS));
        JScrollPane scrollUsers = new JScrollPane(existingUsers);
        scrollUsers.setPreferredSize(new Dimension(300, 100));
        scrollUsers.setMaximumSize(new Dimension(300, 100));
        searchUsers.setMaximumSize(new Dimension(300, 30));
        searchUsers.setPreferredSize(new Dimension(300, 30));

        existingUsers.addListSelectionListener(new UserListener(existingUsers));

        scrollPanel.add(Box.createHorizontalGlue());
        scrollPanel.add(scrollUsers);
        scrollPanel.add(Box.createHorizontalGlue());

        searchUsersPanel.add(Box.createHorizontalGlue());
        searchUsersPanel.add(searchUsers);
        searchUsersPanel.add(Box.createHorizontalGlue());

        existingPanel.add(scrollPanel);
        existingPanel.add(searchUsersPanel);

//        scrollPanel.add(Box.createHorizontalGlue());
//        scrollPanel.add(existingPanel);
//        scrollPanel.add(Box.createHorizontalGlue());
        return existingPanel;
    }

    // String user list
    public String[] stringUsers( ArrayList<User> users) {

        String[] localList = new String[users.size()];

        for (int i = 0; i < users.size(); i++) {
            localList[i] = users.get(i).getUsername();
        }

        return localList;
    }

    public void closeWindow(){
        this.dispose();
    }
}
