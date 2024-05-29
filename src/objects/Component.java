package objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.Map;

public class Component {
    private Image texture;
    public Map<String, Integer> stats;
    public Component(String path,Map<String, Integer> stats) {
        Image texture = null;
        try {
            texture = ImageIO.read(new File
                    (path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.texture = texture;
        this.stats = stats;
    }

    public Image getTexture() {
        return texture;
    }
}
