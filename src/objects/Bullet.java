package objects;

import java.awt.*;

public class Bullet extends Entity{
    int direction;
    public Bullet(int x, int y, int vel, double direction) {
        super(x, y, vel, "res/bullet/lvl1", new Point(0, -1));
        this.direction = (int) Math.toDegrees(direction) < 0 ? 360 + (int) Math.toDegrees(direction) : (int) Math.toDegrees(direction);

    }

    public void move() {
        switch (direction) {
            case 0:
                moveUp();
                break;
            case 90:
                moveRight();
                break;
            case 180:
                moveDown();
                break;
            case 270:
                moveLeft();
                break;
        }
    }
}
