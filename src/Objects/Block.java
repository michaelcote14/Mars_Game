package Objects;

import Utilities.GameObject;
import Utilities.ID;
import Utilities.ImageHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject {
    private BufferedImage blockImage;

    public Block(float x, float y, ID id) {
        super(x, y, id);
        blockImage = ImageHandler.images.get("jailWall");
    }

    public void tick() {
    }

    public void render(Graphics g)
    {
        Rectangle rect = getBounds(); // this is how to show the hit box
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        g.drawImage(blockImage, (int)x, (int)y, 32, 32, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }

}
