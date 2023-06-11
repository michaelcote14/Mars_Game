package Objects;

import Utilities.GameObject;
import Utilities.ID;
import Utilities.ImageHandler;
import Utilities.ObjectHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DroppedItems extends GameObject {
    private ObjectHandler oHandler;
    private BufferedImage image;

    public DroppedItems(float x, float y, ID id, ObjectHandler oHandler, String itemName) {
        super(x, y, id);
        this.oHandler = oHandler;
        this.image = ImageHandler.images.get(itemName);

    }
    @Override
    public void tick() {
    }
    @Override
    public void render(Graphics g) {
        g.drawImage(this.image, (int)x, (int)y, 24, 24,null);
    }
    @Override
    public Rectangle getBounds() {return new Rectangle((int)x, (int)y, 24, 24);}
}
