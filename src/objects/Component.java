package objects;

import java.awt.*;
import java.util.Map;

public class Component {
    private Image texture;
    private Map<String, Integer> stats;
    Component(Image texture, Map<String, Integer> stats) {
        this.texture = texture;
        this.stats = stats;
    }

    public Image getTexture() {
        return texture;
    }
}
