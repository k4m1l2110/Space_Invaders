import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.awt.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



class GamePanel extends JPanel implements KeyListener {
    private Player player;
    private List<Point> stars;
    private List<Alien> aliens;
    private Timer gameTimer;
    private Timer alienSpawnTimer;
    private Set<Integer> keysPressed;
    Menu menu;
    int difficultyLevel = 8, score = 0;
    public GamePanel(String nickname, Menu menu) {
        this.menu = menu;
        player = new Player(nickname,200, 350, 5);
        aliens = new ArrayList<>();

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

        alienSpawnTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < difficultyLevel; i++) {
                    aliens.add(new Alien(i * 100, 50));
                }
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

    private void updateGameState() {
        if ( keysPressed.contains(KeyEvent.VK_LEFT) ) {
            player.moveLeft();
        }
        if ( keysPressed.contains(KeyEvent.VK_RIGHT) ) {
            player.moveRight();
        }
        if ( keysPressed.contains(KeyEvent.VK_SPACE) ) {
                player.shoot();
        }
        for (Alien alien : aliens) {
            alien.move();
        }
        for (Bullet bullet : player.getBullets()) {
            bullet.move();
        }
        for (Bullet bullet : new ArrayList<>(player.getBullets())) {
            for (Alien alien : new ArrayList<>(aliens)) {
                if (bullet.getBounds().intersects(alien.getBounds())) {
                    player.getBullets().remove(bullet);
                    aliens.remove(alien);
                    score++;
                    break;
                }
            }
        }
        for (Alien alien : new ArrayList<>(aliens)) {
            if (alien.getBounds().intersects(player.getBounds())) {
                gameOver();
                menu.showMenu();
                break;
            }
        }
        aliens = aliens.stream().filter(alien -> !alien.getBounds().intersects(player.getBounds())).collect(Collectors.toList());
        player.getBullets().removeIf(bullet -> aliens.stream().anyMatch(alien -> bullet.getBounds().intersects(alien.getBounds())));
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
        gameTimer.stop();
        alienSpawnTimer.stop();
        new Menu();
    }

    public void createStars() {
        stars = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
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
        for (Alien alien : aliens) {
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