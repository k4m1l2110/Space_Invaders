package objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Entity {
    protected int x, y, vel;
    protected double angle;
    protected BufferedImage texture;
    protected Rectangle bounds;
    protected Point delta;
    public Rectangle collision;

    public Entity(int x, int y, int initVel, String path, Point delta) {
        this.x = x;
        this.y = y;
        this.vel = initVel;
        this.angle = 0.0;
        this.texture = loadTextures(path);
        this.delta = delta;
        updateBounds();
    }

    private BufferedImage loadTextures(String path) {
    File folder = new File(path);
    File[] listOfFiles = folder.listFiles();
    BufferedImage combined = null;

    if (listOfFiles != null) {
        int width = 0;
        int height = 0;
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".png")) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    width = Math.max(width, image.getWidth());
                    height = Math.max(height, image.getHeight());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = combined.getGraphics();
        for (File file : listOfFiles) {
            if (file.isFile() && (file.getName().endsWith(".png") || file.getName().endsWith(".jpg"))) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    g.drawImage(image, 0, 0, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        g.dispose();
    }

    return combined;
}

    public void updateBounds() {
        if (texture != null) {
            bounds = new Rectangle(x, y, texture.getWidth(null), texture.getHeight(null));
        }
    }

    public void updatePosition() {
        bounds.x += delta.x * vel;
        bounds.y += delta.y * vel;
        if (bounds.x < 0) {
            bounds.x = 0;
            delta.x *= -1;
        }
        if (bounds.x + bounds.width > 800) {
            bounds.x = 800 - bounds.width;
            delta.x *= -1;
        }
        if (bounds.y < 0) {
            bounds.y = 0;
            delta.y *= -1;
        }
        if (bounds.y + bounds.height > 600) {
            bounds.y = 600 - bounds.height;
            delta.y *= -1;
        }

        updateBounds();
    }

    public void moveLeft() {
        x -= vel;
        bounds.x = x;
        angle = Math.toRadians(-90);
    }

    public void moveRight() {
        x += vel;
        bounds.x = x;
        angle = Math.toRadians(90);
    }

    public void moveUp() {
        y -= vel;
        bounds.y = y;
        angle = 0.0;
    }

    public void moveDown() {
        y += vel;
        bounds.y = y;
        angle = Math.toRadians(180);
    }

    public void moveUpRight() {
        x += vel;
        y -= vel;
        angle = Math.toRadians(45);
        updateBounds();
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        g2d.rotate(angle, x, y);
        if (texture != null) {
            g2d.drawImage(texture, x, y, null);
        }

        if (collision != null) {
            g2d.setColor(new Color(255, 0, 0, 128));
            g2d.fill(collision);
        }

        g2d.setColor(Color.RED);
        g2d.draw(bounds);
        g2d.setTransform(old);
    }

    protected Rectangle getCollision(Rectangle rect1, Rectangle rect2) {
        Area a1 = new Area(rect1);
        Area a2 = new Area(rect2);
        a1.intersect(a2);
        return a1.getBounds();
    }

    public boolean collision(int x, int y, Entity other) {
        boolean collision = false;
        int spiderPixel = texture.getRGB(x - bounds.x, y - bounds.y);
        int flyPixel = other.texture.getRGB(x - other.bounds.x, y - other.bounds.y);
        if (((spiderPixel >> 24) & 0xFF) < 255 && ((flyPixel >> 24) & 0xFF) < 255) {
            collision = true;
        }
        return collision;
    }

    public void detectCollision(Entity other) {
        collision = null;
        if (bounds.intersects(other.bounds)) {
            Rectangle cbounds = getCollision(bounds, other.bounds);
            if (!cbounds.isEmpty()) {
                for (int x = cbounds.x; x < cbounds.x + cbounds.width; x++) {
                    for (int y = cbounds.y; y < cbounds.y + cbounds.height; y++) {
                        if (collision(x, y, other)) {
                            collision = cbounds;
                            break;
                        }
                    }
                }
            }
        }
    }

}