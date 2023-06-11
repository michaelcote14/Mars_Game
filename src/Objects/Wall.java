package Objects;

import Utilities.GameObject;
import Utilities.ID;
import Utilities.ImageHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends GameObject {
    private BufferedImage wallImage;

    public Wall(float x, float y, ID id) {
        super(x, y, id);
        wallImage = ImageHandler.images.get("jailWall");
    }

    public void tick() {
    }

    public void render(Graphics g)
    {
        g.drawImage(wallImage, (int)x, (int)y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }

}
