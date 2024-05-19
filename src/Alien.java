import java.awt.*;

class Alien extends Entity {


    public Alien(int x, int y) {
        super(x, y, 3);
    }

    public void draw(Graphics g) {
        g.fillRect(x, y, 20, 20);
    }

    public void move() {
        y+=vel;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 10, 10);
    }

}