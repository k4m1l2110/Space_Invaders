package objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Entity {
    protected int x, y, vel;
    protected double angle;
    protected BufferedImage texture;
    protected Rectangle boundingRectangle;

    public Entity(int x, int y, int initVel, String path, Point delta) {
        this.x = x;
        this.y = y;
        this.vel = initVel;
        this.angle = 0.0;
        this.texture = loadTextures(path);
        this.boundingRectangle = calculateBoundingRectangle();
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

    private Rectangle calculateBoundingRectangle() {
        int minX = texture.getWidth();
        int minY = texture.getHeight();
        int maxX = 0;
        int maxY = 0;

        for (int y = 0; y < texture.getHeight(); y++) {
            for (int x = 0; x < texture.getWidth(); x++) {
                int pixel = texture.getRGB(x, y);
                if ((pixel>>24) != 0x00) {
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
            }
        }

        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    public void moveLeft() {
        x -= vel;
        angle = Math.toRadians(-90);
        updateBoundingRectangle();
    }

    public void moveRight() {
        x += vel;
        angle = Math.toRadians(90);
        updateBoundingRectangle();
    }

    public void moveUp() {
        y -= vel;
        angle = 0.0;
        updateBoundingRectangle();
    }

    public void moveDown() {
        y += vel;
        angle = Math.toRadians(180);
        updateBoundingRectangle();
    }

    private void updateBoundingRectangle() {
        boundingRectangle.setLocation(x+(texture.getWidth()-boundingRectangle.width)/2, y+(texture.getHeight()-boundingRectangle.height)/2);
    }

    public boolean detectCollision(Entity other) {
        return boundingRectangle.intersects(other.boundingRectangle);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();

        int centerX = x + texture.getWidth() / 2;
        int centerY = y + texture.getHeight() / 2;

        g2d.rotate(angle, centerX, centerY);

        if (texture != null) {
            g2d.drawImage(texture, x, y, null);
        }

//        g2d.setColor(Color.RED);
//        g2d.draw(boundingRectangle);
//
//        g.setColor(Color.WHITE);
//        g.drawString("Cursor X: " + x + ", Y: " + y, x, y);

        g2d.setTransform(old);
    }



    public Rectangle getBounds() {
        return boundingRectangle;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}