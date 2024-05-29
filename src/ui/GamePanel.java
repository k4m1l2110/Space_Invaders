package ui;

import objects.*;
import objects.Component;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
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
    private int cursorX = 0, cursorY = 0, difficultyLevel = 8, score = 0;
    ArrayList<Component> components = new ArrayList<>();

    public GamePanel(String nickname, GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        player = new Player(nickname,200, 350, 5);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                cursorX = e.getX();
                cursorY = e.getY();
            }
        });

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

        alienSpawner = new AlienSpawner();

        alienSpawnTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alienSpawner.spawnWave(score);
            }
        });

        alienSpawnTimer.start();

        JButton moveLeftButton = new JButton("Move Left");

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

        difficultyLevel = (score/1000)+1;

        if ( keysPressed.contains(KeyEvent.VK_LEFT) ) {
            player.moveLeft();
        }
        if ( keysPressed.contains(KeyEvent.VK_RIGHT) ) {
            player.moveRight();
        }
        if ( keysPressed.contains(KeyEvent.VK_UP) ) {
            player.moveUp();
        }
        if ( keysPressed.contains(KeyEvent.VK_DOWN) ) {
            player.moveDown();
        }
        if ( keysPressed.contains(KeyEvent.VK_SPACE) ) {
            player.shoot();
        }

        alienSpawner.getAliens().forEach(alien -> alien.move(player));
        player.getBullets().forEach(Bullet::move);

        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Alien> aliensToRemove = new ArrayList<>();
        for (Bullet bullet : player.getBullets()) {
            for (Alien alien : alienSpawner.getAliens()) {
                if (bullet.detectCollision(alien)) {
                    bulletsToRemove.add(bullet);
                    alien.getHurts();
                    score += 10;
                }
                if (alien.getHealth() <= 0) {
                    aliensToRemove.add(alien);
                }
            }
        }

        for(Alien alien : alienSpawner.getAliens()) {
            if(alien.detectCollision(player)) {
                player.getHurts();
                aliensToRemove.add(alien);
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

        g.drawString("Score: " + score, 10, 20);
        player.draw(g);
        for (Alien alien : alienSpawner.getAliens()) {
            alien.draw(g);
        }
        for (Bullet bullet : player.getBullets()) {
            bullet.draw(g);
        }

        g.setColor(Color.WHITE);
        g.drawString("Cursor X: " + cursorX + ", Y: " + cursorY, 10, 30);
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