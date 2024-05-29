package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.File;

public class Menu extends JFrame {

    private JLabel titleLabel;
    private JPanel menuPanel, optionsPanel;
    private JMenuBar menuBar;
    private JButton start, options, exit;
    private Image backgroundImage;
    private JComboBox<String> difficultyComboBox, aliensDelayComboBox;
    private JTextField maxAlienWavesTextField;
    private int difficulty=1, aliensDelay=8000, maxAlienWaves=5;

    public Menu() {
        super("Space invaders");
        setSize(800,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            backgroundImage = ImageIO.read(new File("res/ui/background.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        contentPane.setLayout(new GridBagLayout());
        setContentPane(contentPane);

        Font customFont = null;

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/ui/upheaval/upheavtt.ttf"));
            customFont = customFont.deriveFont(12f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        titleLabel = new JLabel("Space Invaders", JLabel.CENTER);
        titleLabel.setFont(customFont.deriveFont(48f));
        titleLabel.setOpaque(false);
        titleLabel.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        contentPane.add(titleLabel, gbc);

        start = new JButton("Start");
        options = new JButton("Options");
        exit = new JButton("Exit");

        start.addActionListener(e -> {
            new GameFrame(difficulty, aliensDelay, maxAlienWaves);
            setVisible(false);
            dispose();
        });

        options.addActionListener(e -> showOptionsDialog());

        exit.addActionListener(e -> System.exit(0));

        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);

        menuPanel.add(Box.createVerticalStrut(50));
        menuPanel.add(start);
        menuPanel.add(Box.createVerticalStrut(25));
        menuPanel.add(options);
        menuPanel.add(Box.createVerticalStrut(25));
        menuPanel.add(exit);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        contentPane.add(menuPanel, gbc);

        optionsPanel = new JPanel();
        optionsPanel.setOpaque(false);

        gbc.anchor = GridBagConstraints.SOUTH;
        contentPane.add(optionsPanel, gbc);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        setVisible(true);
    }

    //Zad pp3/4/5
    private void showOptionsDialog() {
        JDialog optionsDialog = new JDialog(this, "Options", true);
        optionsDialog.setLayout(new GridLayout(0, 2));

        difficultyComboBox = new JComboBox<>(new String[] {"Easy", "Medium", "Hard"});
        aliensDelayComboBox = new JComboBox<>(new String[] {"Slow", "Normal", "Fast"});
        maxAlienWavesTextField = new JTextField();

        optionsDialog.add(new JLabel("Difficulty:"));
        optionsDialog.add(difficultyComboBox);
        optionsDialog.add(new JLabel("Aliens Delay:"));
        optionsDialog.add(aliensDelayComboBox);
        optionsDialog.add(new JLabel("Max Alien Waves:"));
        optionsDialog.add(maxAlienWavesTextField);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            difficulty = difficultyComboBox.getSelectedItem() == "Easy" ? 1 : difficultyComboBox.getSelectedItem() == "Medium" ? 2 : 3;
            aliensDelay = aliensDelayComboBox.getSelectedItem() == "Slow" ? 8000 : aliensDelayComboBox.getSelectedItem() == "Normal" ? 4000 : 2000;
            try {
                maxAlienWaves = Integer.parseInt(maxAlienWavesTextField.getText()) <= 0? 1 : Integer.parseInt(maxAlienWavesTextField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number for max alien waves. Please enter a valid number.");
                return;
            }
            optionsDialog.dispose();
        });

        optionsDialog.add(okButton);

        optionsDialog.pack();
        optionsDialog.setLocationRelativeTo(this);
        optionsDialog.setVisible(true);
    }
}