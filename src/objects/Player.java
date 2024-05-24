package objects;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Player extends Entity {
    String nickname;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public Player(String nickname, int x, int y, int initVel) {
        super(x, y, initVel, "res/player/lvl1", new Point(0, 0));
        this.nickname = nickname;
    }

    public void shoot() {
        Bullet bullet = new Bullet(x, y, 5);
        bullets.add(bullet);

    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 10, 10);
    }
}

