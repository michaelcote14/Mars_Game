package Objects;

import Utilities.GameObject;
import Utilities.ID;
import Utilities.ImageHandler;
import Utilities.ObjectHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthPack extends GameObject {
    private ObjectHandler oHandler;
    private BufferedImage healthPackImage;

    public HealthPack(float x, float y, ID id, ObjectHandler oHandler, ImageHandler imageHandler) {
        super(x, y, id, imageHandler);
        this.oHandler = oHandler;
        this.healthPackImage = ImageHandler.images.get("healthPack");

    }
    @Override
    public void tick() {
    }
    @Override
    public void render(Graphics g) {
        g.drawImage(this.healthPackImage, (int)x, (int)y, 24, 24,null);
    }
    @Override
    public Rectangle getBounds() {return new Rectangle((int)x, (int)y, 32, 32);}
}
