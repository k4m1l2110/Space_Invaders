package objects;

import java.awt.*;



public class Alien extends Entity {
    int health, maxHealth = 10;

    int armor=0;
    public interface Move {
        void move(Entity entity);
    }
    protected Move move;

    public Alien(int x, int y,Move moveOperation) {
        super(x, y, 1 ,"res/alien/lvl1", new Point(0, 1));
        this.health = 10;
        this.move=moveOperation;

    }

    public void move() {
        move.move(this);
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