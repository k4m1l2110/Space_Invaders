package objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Entity {
    protected int x, y, vel;
    protected double angle;
    protected Image texture;
    protected Rectangle bounds;
    protected Point delta;

    public Entity(int x, int y, int initVel, String path, Point delta) {
        this.x = x;
        this.y = y;
        this.vel = initVel;
        this.angle = 0.0;
        this.texture = loadTextures(path);
        this.delta = delta;
        updateBounds();
    }

    private Image loadTextures(String path) {
    File folder = new File(path);
    File[] listOfFiles = folder.listFiles();
    BufferedImage combined = null;

    if (listOfFiles != null) {
        int width = 0;
        int height = 0;
        for (File file : listOfFiles) {
            if (file.isFile() && (file.getName().endsWith(".png") || file.getName().endsWith(".jpg"))) {
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
    }

    public void moveLeft() {
        x -= vel;
        angle = Math.toRadians(-90);
        updateBounds();
    }

    public void moveRight() {
        x += vel;
        angle = Math.toRadians(90);
        updateBounds();
    }

    public void moveUp() {
        y -= vel;
        angle = 0.0;
        updateBounds();
    }

    public void moveDown() {
        y += vel;
        angle = Math.toRadians(180);
        updateBounds();
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
        g2d.setTransform(old);
    }

    public boolean isColliding(Entity other) {
        Rectangle intersection = this.bounds.intersection(other.bounds);

        for (int x = intersection.x; x < intersection.x + intersection.width; x++) {
            for (int y = intersection.y; y < intersection.y + intersection.height; y++) {
                int thisColor = ((BufferedImage) this.texture).getRGB(x - this.bounds.x, y - this.bounds.y);
                int otherColor = ((BufferedImage) other.texture).getRGB(x - other.bounds.x, y - other.bounds.y);

                if ((thisColor >>> 24) != 0 && (otherColor >>> 24) != 0) {
                    return true;
                }
            }
        }

        return false;
    }
}