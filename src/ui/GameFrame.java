package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    private JMenuBar menuBar;
    private JMenuItem rules, icon, pause, replay;

    public GameFrame() {
        String nickname = JOptionPane.showInputDialog("Enter your nickname:");
        gamePanel = new GamePanel(nickname, this);
        add(gamePanel);
        setTitle("Space Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setVisible(true);

        menuBar = new JMenuBar();
        rules = new JMenuItem("Rules");
        icon = new JMenuItem("Choose Icon");
        pause = new JMenuItem("Pause");
        replay = new JMenuItem("Replay");

        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame rulesFrame = new JFrame("Rules");
                JTextArea rulesTextArea = new JTextArea("Rules of the game...");
                rulesFrame.add(rulesTextArea);
                rulesFrame.setSize(300, 200);
                rulesFrame.setVisible(true);
            }
        });

        icon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Icon 1", "Icon 2", "Icon 3"};
                String icon = (String) JOptionPane.showInputDialog(null, "Choose your icon",
                        "Icon", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                gamePanel.setPlayerIcon(icon);
            }
        });

        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.pauseGame();
            }
        });

        replay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.replayGame();
            }
        });

        menuBar.add(rules);
        menuBar.add(icon);
        menuBar.add(pause);
        menuBar.add(replay);
        setJMenuBar(menuBar);
    }


}