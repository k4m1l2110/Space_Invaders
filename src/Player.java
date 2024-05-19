
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

class Player extends Entity {
    String nickname;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Image playerImage;
    private long lastShotTime;
    private final long shotDelay = 500; // 500 milliseconds delay between shots

    public Player(String nickname, int x, int y, int initVel) {
        super(x, y, initVel);
        this.nickname = nickname;
        this.playerImage = new ImageIcon("body.png").getImage();
        this.lastShotTime = System.currentTimeMillis();
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(playerImage, x, y, null);
        g.drawImage(new ImageIcon("wing.png").getImage(), x ,y, null);
    }
    public void shoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= shotDelay) {
            Bullet bullet = new Bullet(x+playerImage.getWidth(null)/2, y);
            bullets.add(bullet);
            lastShotTime = currentTime;
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 10, 10);
    }
}

