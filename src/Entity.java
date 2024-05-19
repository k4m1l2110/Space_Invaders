import java.awt.*;

public class Entity {
    protected int x, y, vel, width, height;

    public Entity(int x, int y, int initVel) {
        this.x = x;
        this.y = y;
        this.vel = initVel;
    }

    public void moveLeft() {
        x -= vel;
    }

    public void moveRight() {
        x += vel;
    }

    public void move() {
        y++;
    }

    public void draw(Graphics g) {
        g.fillRect(x, y, 20, 20);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 10, 10);
    }
}
