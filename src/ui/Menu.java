package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Menu extends JFrame {

    private JLabel titleLabel;
    private JPanel menuPanel, optionsPanel;
    private JMenuBar menuBar;
    private JButton start, options, exit;
    public Menu(){

        super("Space invaders");
        setSize(800,800);
        setLayout(new GridLayout(3,1));

        titleLabel = new JLabel("Space Invaders", JLabel.CENTER);
        titleLabel.setFont(new Font("roboto", Font.PLAIN, 50));

        add(titleLabel);

        start = new JButton("Start");
        options = new JButton("Options");
        exit = new JButton("Exit");



        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameFrame();
                setVisible(false);
                dispose();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());

        menuPanel.add(start);
        menuPanel.add(options);
        menuPanel.add(exit);

        add(menuPanel);

        optionsPanel = new JPanel();



        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        setVisible(true);
    }
}