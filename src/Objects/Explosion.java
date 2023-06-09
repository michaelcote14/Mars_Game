package Objects;

import Utilities.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Explosion extends GameObject {
    private ObjectHandler oHandler;
    private BufferedImage[] explosionImages;
    private Animation explosionAnim;

    public Explosion(float x, float y, int width, int height, ID id, ObjectHandler oHandler) {
        super(x, y, id);
        this.oHandler = oHandler;
        explosionImages = ImageHandler.createImageArray("fireExplosion", 18);
        explosionAnim = new Animation(80, explosionImages);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
            explosionAnim.runAnimation();
            explosionAnim.drawAnimation(g, (int) x-10, (int) y-65, 50, 210, 210);
            if(explosionAnim.getCount() == 18) {
                oHandler.removeObject(this);
            }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x-60, (int) y-70, 210, 210);
    }

}

