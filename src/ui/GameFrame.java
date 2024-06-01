package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    private JMenuBar menuBar;
    private JMenuItem rules, icon, pause, replay;
    private int minscore=0;

    public GameFrame(int difficulty, int delay, int maxwaves) {
        //Zad pp9
        String nickname = JOptionPane.showInputDialog("Enter your nickname:");
        int gamemode = JOptionPane.showInternalOptionDialog(null, "Choose your gamemode", "Gamemode",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Classic", "Extended"},"Classic");

        Path path = Paths.get("scores.txt");
        if (Files.exists(path)) {
            try {
                List<String> lines = Files.readAllLines(path);
                List<Integer> scores = new ArrayList<>();
                for (String line : lines) {
                    String[] parts = line.split(", ");
                    int score = Integer.parseInt(parts[1].substring(7));
                    scores.add(score);
                }
                if (!scores.isEmpty()) {
                    minscore = Collections.min(scores);
                }
                else {
                    minscore = 0;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        gamePanel = new GamePanel(nickname, this, gamemode,
                difficulty, delay, maxwaves, minscore);
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

        icon.setFocusable(false);
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

        pause.setFocusable(false);
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.pauseGame();
            }
        });

        replay.setFocusable(false);
        replay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.replayGame();
            }
        });

        JButton top10Button = new JButton("Top 10");
        top10Button.setFocusable(false);
        top10Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Path path = Paths.get("scores.txt");
                try {
                    List<String> lines = Files.readAllLines(path);
                    List<String> scores = new ArrayList<>();
                    for (String line : lines) {
                        String[] parts = line.split(", ");
                        String nickname = parts[0].substring(10);
                        int score = Integer.parseInt(parts[1].substring(7));
                        scores.add(nickname + ": " + score);
                    }
                    scores.sort(Collections.reverseOrder());
                    minscore = Integer.parseInt(scores.get(9).split(": ")[1]);
                    StringBuilder top10 = new StringBuilder();
                    for (int i = 0; i < Math.min(10, scores.size()); i++) {
                        top10.append((i + 1) + ". " + scores.get(i) + "\n");
                    }
                    JOptionPane.showMessageDialog(null, top10.toString(), "Top 10 Players", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        menuBar.add(top10Button);

        menuBar.add(rules);
        menuBar.add(icon);
        menuBar.add(pause);
        menuBar.add(replay);
        setJMenuBar(menuBar);
    }


}