package Objects;

import Utilities.GameObject;
import Utilities.ID;
import Utilities.ImageSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject {
    private BufferedImage blockImage;

    public Block(float x, float y, ID id, ImageSheet imageSheet) {
        super(x, y, id, imageSheet);
        blockImage = imageSheet.grabImage(5, 2, 32, 32);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(blockImage, (int)x, (int)y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }
}
