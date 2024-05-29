package objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Entity {
    protected int x, y, vel;
    int health, maxHealth = 10, agility;
    protected double angle;
    protected BufferedImage texture;
    private ArrayList<Component> components;
    protected Rectangle boundingRectangle;

    public Entity(int x, int y, int initVel, ArrayList<Component> components, Point delta) {
        this.x = x;
        this.y = y;
        this.vel = initVel;
        this.angle = 0.0;
        this.components = components;
        this.texture = loadComponents();
        health=maxHealth;
        //changecolor(texture, 200);
        this.boundingRectangle = calculateBoundingRectangle();

    }


    private BufferedImage loadComponents() {
        int maxWidth = 0;
        int maxHeight = 0;
        for (Component component : components) {
            Image texture = component.getTexture();
            maxHealth+=component.stats.get("health");
            vel+=component.stats.get("speed");
            agility+=component.stats.get("agility");
            maxWidth = Math.max(maxWidth, texture.getWidth(null));
            maxHeight = Math.max(maxHeight, texture.getHeight(null));
        }


        BufferedImage combined = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = combined.getGraphics();

        for (Component component : components) {
            Image texture = component.getTexture();
            g.drawImage(texture, 0, 0, null);
        }

        g.dispose();
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

    protected void updateBoundingRectangle() {
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