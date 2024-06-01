package objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Alien extends Entity {


    public interface Move {
        void move(Entity entity);
    }

    protected Move move;
    protected boolean sentient = false;

    static public ArrayList<Bullet> bullets = new ArrayList<>();

    public Alien(int x, int y, Move moveOperation, ArrayList<Component> components, boolean sentient) {
        super(x, y, 1 ,components, new Point(0, 1));
        this.move=moveOperation;
        this.sentient = sentient;
    }

    private boolean canShoot = true;

    public Timer shootDelayTimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            shoot();
        }
    });

    public void shoot() {
            Bullet bullet = new Bullet(x, y, 10, angle);
            bullets.add(bullet);
    }

    public void move() {
        move.move(this);
    }

    public void move(Entity target){
        moveTowards(target);
    }

    public void moveTowards(Entity target) {

    int dx = target.getX() - this.getX();
    int dy = target.getY() - this.getY();

    double angleToTarget = Math.atan2(dy, dx);

        if (dx > 0) {
            moveRight();
        } else if (dx < 0) {
            moveLeft();
        }

        if (dy > 0) {
            moveDown();
        } else if (dy < 0) {
            moveUp();
        }

        this.angle = angleToTarget + Math.PI / 2;
}

    public boolean isSentient() {
        return sentient;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
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