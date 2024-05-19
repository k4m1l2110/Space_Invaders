import java.awt.*;

public class Bullet {
    private int x, y;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.fillRect(x, y, 5, 5);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 5, 5);
    }

    public void move() {
        y--;
    }
}
