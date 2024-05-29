package ui;

import objects.*;
import objects.Component;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.awt.*;
import java.util.List;


class GamePanel extends JPanel implements KeyListener {
    private Player player;
    private List<Point> stars;
    private Timer gameTimer, alienSpawnTimer;
    private Set<Integer> keysPressed;
    private AlienSpawner alienSpawner;
    private GameFrame gameFrame;
    private int gamemode, difficulty=1, delay =1, maxwaves=1, minscore;
    private int cursorX = 0, cursorY = 0, difficultyLevel = 8, score = 0;
    ArrayList<Component> components = new ArrayList<>();

    public GamePanel(String nickname, GameFrame gameFrame,
                     int gm, int difficulty, int delay, int maxw, int minscore) {

        this.gameFrame = gameFrame;
        this.gamemode = gm;
        this.difficulty = difficulty;
        this.maxwaves = maxw;
        this.delay = delay;
        this.minscore = minscore;

        player = new Player(nickname,200, 350, 5);


        keysPressed = new HashSet<>();
        setFocusable(true);
        addKeyListener(this);

        gameTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGameState();
                repaint();
            }
        });
        gameTimer.start();

        alienSpawner = new AlienSpawner(gamemode, this.difficulty);

        //Zad pp4
        alienSpawnTimer = new Timer(this.delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gm==0) {
                    if(maxwaves>0)
                    alienSpawner.spawnWave(score);
                    maxwaves--;
                    //Zad pp6
                    if (maxwaves <= 0 && alienSpawner.getAliens().isEmpty()) {
                        gameOver();
                    }
                }
                else{
                    alienSpawner.spawnWave(score);
                }
            }
        });

        alienSpawnTimer.start();

        JButton moveLeftButton = new JButton("Move Left");
        //Zad pp1
        moveLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.moveLeft();
            }
        });

        JButton moveRightButton = new JButton("Move Right");
        moveRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.moveRight();
            }
        });

        //Zad pp2
        JButton shootButton = new JButton("Shoot");
        shootButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.shoot();
            }
        });

        add(moveLeftButton);
        add(moveRightButton);
        add(shootButton);
    }

    void spawnWave(){

    }

    private void updateGameState() {

//        if(score>minscore)
//            JOptionPane.showMessageDialog(this, "Congratulations! Your score is in the top 10!");
//
        //Zad pp1
        if ( keysPressed.contains(KeyEvent.VK_LEFT) ) {
            if(gamemode==1)
                player.Left();
            else
                player.moveLeft();
        }
        if ( keysPressed.contains(KeyEvent.VK_RIGHT) ) {
            if(gamemode==1)
                player.Right();
            else
                player.moveRight();
        }
        if ( keysPressed.contains(KeyEvent.VK_UP) ) {
            if(gamemode==1)
                player.Up();
            else
                player.moveUp();
        }
        if ( keysPressed.contains(KeyEvent.VK_DOWN) ) {
            if(gamemode==1)
                player.Down();
            else
                player.moveDown();
        }
        //Zad pp2
        if ( keysPressed.contains(KeyEvent.VK_SPACE) ) {
            player.shoot();
        }
        if(gamemode==0)
            alienSpawner.getAliens().forEach(alien -> alien.move());
        else {
            alienSpawner.getAliens().forEach(alien -> alien.move());
        }
        player.getBullets().forEach(Bullet::move);

        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Alien> aliensToRemove = new ArrayList<>();
        for (Bullet bullet : player.getBullets()) {
            for (Alien alien : alienSpawner.getAliens()) {
                if (bullet.detectCollision(alien)) {
                    bulletsToRemove.add(bullet);
                    alien.getHurts();
                }
                if (alien.getHealth() <= 0) {
                    //Zad pp7
                    score += gamemode==0?1:alien.getMaxHealth()/2;
                    aliensToRemove.add(alien);
                }
            }
        }

        for(Alien alien : alienSpawner.getAliens()) {
            if(alien.detectCollision(player)) {
                if(gamemode==0)
                    gameOver();
                else {
                    player.getHurts();
                    aliensToRemove.add(alien);
                }
            }
            if(alien.getX() > 800 || alien.getY() > 800) {
                aliensToRemove.add(alien);
            }
        }

        if(player.getHealth() <= 0) {
            gameOver();
        }

        player.getBullets().removeAll(bulletsToRemove);
        alienSpawner.getAliens().removeAll(aliensToRemove);

    }

    private void gameOver() {
    JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
    gameTimer.stop();
    alienSpawnTimer.stop();
    gameFrame.setVisible(false);
    gameFrame.dispose();
    new Menu();
    //Zad pp10
    Path path = Paths.get("scores.txt");
    try {
        List<String> lines = new ArrayList<>();
        if (Files.exists(path)) {
            lines = Files.readAllLines(path);
            boolean found = false;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith("Nickname: " + player.getNickname())) {
                    lines.set(i, "Nickname: " + player.getNickname() + ", Score: " + score);
                    found = true;
                    break;
                }
            }
            if (!found) {
                lines.add("Nickname: " + player.getNickname() + ", Score: " + score);
            }
        } else {
            lines.add("Nickname: " + player.getNickname() + ", Score: " + score);
        }
        Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    public void createStars() {
        stars = new ArrayList<>();
        for (int i = 0; i < 100; i++) { // adjust the number of stars here
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            stars.add(new Point(x, y));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (stars == null || stars.isEmpty()) {
            createStars();
        }

        g.setColor(Color.WHITE);
        for (Point star : stars) {
            star.y += 1;
            if (star.y > getHeight()) {

                star.y = 0;
                star.x = (int) (Math.random() * getWidth());
            }
            g.fillOval(star.x, star.y, 2, 2);
        }
        //Zad pp8
        g.drawString("Score: " + score, 10, 20);
        player.draw(g);
        for (Alien alien : alienSpawner.getAliens()) {
            alien.draw(g);
        }
        for (Bullet bullet : player.getBullets()) {
            bullet.draw(g);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed.remove(e.getKeyCode());
    }

    public void setPlayerIcon(String icon) {
        //TODO
    }

    public void pauseGame() {
        //TODO
    }

    public void replayGame() {
        //TODO
    }
}