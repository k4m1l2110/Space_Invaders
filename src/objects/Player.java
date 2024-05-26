package objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Player extends Entity {
    String nickname;
    int health = 100, maxHealth = 100;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public Player(String nickname, int x, int y, int initVel) {
        super(x, y, initVel, "res/player/lvl1", new Point(0, 0));
        this.nickname = nickname;
    }
    // Add a boolean to track if the player can shoot
    private boolean canShoot = true;

    // Add a Timer that will be started when the player shoots
    private Timer shootDelayTimer = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            canShoot = true;
            shootDelayTimer.stop();
        }
    });

    public void shoot() {
        // Only shoot if canShoot is true
        if (canShoot) {
            Bullet bullet = new Bullet(x, y, 5, angle);
            bullets.add(bullet);

            // Disable shooting and start the timer
            canShoot = false;
            shootDelayTimer.start();
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 10, 10);
    }

    public void getHurts() {
        health -= 10;
    }

    public int getHealth() {
        return health;
    }

    @Override
public void draw(Graphics g) {
    super.draw(g);

        g.setColor(Color.RED);
        g.fillRect(x, y - 20,  boundingRectangle.width, 5);

        g.setColor(Color.GREEN);
        g.fillRect(x, y - 20, (int)(((double)health / maxHealth) * boundingRectangle.width), 5);

}

}

