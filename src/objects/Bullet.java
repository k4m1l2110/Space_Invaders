package objects;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Bullet extends Entity{

    public Bullet(int x, int y, int vel, double angle) {
        super(x, y, vel, new ArrayList<>(Arrays.asList(
                        new Component(
                                "res/bullet/lvl1/body.png", Map.of(
                                "health", 100,
                                "armor", 0,
                                "speed", 5,
                                "agility", 5,
                                "strength", 5))

                ))
                , new Point(0, -1));
        this.angle = angle - Math.PI / 2;
    }

    public void move() {
        x += vel * Math.cos(angle);
        y += vel * Math.sin(angle);
        updateBoundingRectangle();
    }
}