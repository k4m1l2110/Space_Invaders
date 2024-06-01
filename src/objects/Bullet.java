package objects;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Bullet extends Entity{

    public Bullet(int x, int y, int vel, double angle) {
        super(x, y, vel, new ArrayList<>(Arrays.asList(
                        new Component(
                                "res/bullet/lvl1/body.png",Map.of(
                                "health", 50,
                                "speed", 3,
                                "agility", 10,
                                "attack", 2))

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