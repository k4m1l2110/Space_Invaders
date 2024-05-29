package objects;

import java.awt.*;
import java.util.ArrayList;


public class Alien extends Entity {


    public interface Move {
        void move(Entity entity);
    }

    protected Move move;

    public Alien(int x, int y, Move moveOperation, ArrayList<Component> components) {
        super(x, y, 1 ,components, new Point(0, 1));
        this.health = 10;
        this.move=moveOperation;

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