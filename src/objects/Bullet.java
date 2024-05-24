package objects;

import java.awt.*;

public class Bullet extends Entity{
    private int x, y;

    public Bullet(int x, int y, int vel) {
        super(x, y, vel, "res/bullet/lvl1", new Point(0, -1));
        this.x = x;
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 5, 5);
    }

    public void move() {
        moveUp();
    }
}
