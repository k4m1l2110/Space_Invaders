package objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static java.lang.Thread.sleep;


public class Player extends Entity {
    String nickname;
    private ArrayList<Bullet> bullets = new ArrayList<>();


    public Player(String nickname, int x, int y, int initVel) {
        super(x, y, initVel, new ArrayList<>(Arrays.asList(
                new Component(
                        "res/player/lvl1/basic/body.png", Map.of(
                        "health", 20,
                        "armor", 0,
                        "speed", 2,
                        "agility", 5,
                        "strength", 5)),
                new Component(
                        "res/player/lvl1/basic/wing.png", Map.of(
                        "health", 20,
                        "armor", 0,
                        "speed", 2,
                        "agility", 5,
                        "strength", 5))

        )), new Point(0, 0));
        this.nickname = nickname;
    }

    private boolean canShoot = true;

    private Timer shootDelayTimer = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            canShoot = true;
            shootDelayTimer.stop();
        }
    });

    public void shoot() {
        if (canShoot) {
            Bullet bullet = new Bullet(x, y, 5, angle);
            bullets.add(bullet);

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

@Override
    public void moveLeft(){
        angle -= Math.toRadians(agility);
        super.updateBoundingRectangle();
    }

    @Override
    public void moveRight(){
        angle += Math.toRadians(agility);
        super.updateBoundingRectangle();
    }

    @Override
    public void moveUp(){
        x += vel * Math.cos(angle - Math.PI /2);
        y += vel * Math.sin(angle - Math.PI /2);
        super.updateBoundingRectangle();
    }

    @Override
    public void moveDown(){
        x -= vel * Math.cos(angle - Math.PI /2);
        y -= vel * Math.sin(angle - Math.PI /2);
        super.updateBoundingRectangle();
    }

}

