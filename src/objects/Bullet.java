package objects;

import java.awt.*;

public class Bullet extends Entity{

    public Bullet(int x, int y, int vel, double angle) {
        super(x, y, vel, "res/bullet/lvl1", new Point(0, -1));
        this.angle = angle - Math.PI / 2;
    }

    public void move() {
        x += vel * Math.cos(angle);
        y += vel * Math.sin(angle);
        updateBoundingRectangle();
    }
}