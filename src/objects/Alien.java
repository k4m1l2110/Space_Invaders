package objects;

import java.awt.*;

public class Alien extends Entity {


    public Alien(int x, int y) {
        super(x, y, 1,"res/alien/lvl1", new Point(0, 1));
    }

    public void move() {
        super.moveDown();
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 10, 10);
    }

}