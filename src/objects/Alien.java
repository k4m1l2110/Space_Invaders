package objects;

import java.awt.*;

public class Alien extends Entity {
    int health, maxHealth = 10;

    public Alien(int x, int y) {
        super(x, y, 1 ,"res/alien/lvl1", new Point(0, 1));
        this.health = 10;
    }

    public void move() {
        super.moveDown();
    }

    public void getHurts() {
        health -= 2;
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