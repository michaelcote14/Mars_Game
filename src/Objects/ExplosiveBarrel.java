package Objects;

import Utilities.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ExplosiveBarrel extends GameObject {
    private ObjectHandler oHandler;
    private BufferedImage explosiveBarrelImage;
    private boolean destroyed = false;

    public ExplosiveBarrel(float x, float y, ID id, ObjectHandler oHandler) {
        super(x, y, id);
        this.oHandler = oHandler;
        this.explosiveBarrelImage = ImageHandler.images.get("explosiveBarrel");
    }

    @Override
    public void tick() {
        for (int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);

            if (tempObject.getId() == ID.Bullet || tempObject.getId().toString().contains("Ability")) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    destroyed = true;
                }
            }
        }
    }
    @Override
    public void render(Graphics g) {
        if(destroyed == true) {
            oHandler.addObject(new Explosion(x, y, 210, 210, ID.Explosion, oHandler));
            destroyed = false;
            oHandler.removeObject(this);
        }
        else {
            g.drawImage(this.explosiveBarrelImage, (int) x, (int) y, 48, 48, null);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 48, 48);
    }

}

