package Objects;

import Utilities.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Chest extends GameObject {
    private ObjectHandler oHandler;
    private BufferedImage chestImage1;
    private BufferedImage[] chestImages = new BufferedImage[5];
    private boolean openChest = false;
    private Animation openChestAnimation;
    private int openChestCounter = 0;

    public Chest(float x, float y, ID id, ObjectHandler oHandler) {
        super(x, y, id);
        this.oHandler = oHandler;
        this.chestImage1 = ImageHandler.images.get("chest1");
        this.chestImages = ImageHandler.createImageArray("chest", 5);
        this.openChestAnimation = new Animation(100, this.chestImages);
    }
    @Override
    public void tick() {
        for (int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);

            if (tempObject.getId() == ID.Player) {
                if (getBoundsBig().intersects(tempObject.getBounds())) {
                    openChest = true;
                }
            }
            else if (tempObject.getId() == ID.Explosion) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    oHandler.removeObject(this);
                }
            }
        }
    }
    @Override
    public void render(Graphics g) {
        if(openChest == true) {
            g.drawImage(this.chestImage1, (int) x+2, (int) y-2, 44, 32, null);
            openChestCounter++;
            openChestAnimation.runAnimation();
            openChestAnimation.drawAnimation(g, (int)x+2, (int)y-2, 0);
            if(openChestAnimation.getCount() == 5) {
                oHandler.addObject(new HealthPack(x+13, y, ID.HealthPack, oHandler));
                if(openChestCounter > 250) {
                    oHandler.removeObject(this);
                }
            }
        }
        else {
            g.drawImage(this.chestImage1, (int) x+2, (int) y-2,null);
        }
    }
    @Override
    public Rectangle getBounds() {return new Rectangle((int)x, (int)y, 44, 32);}
    public Rectangle getBoundsBig() {return new Rectangle((int)x-10, (int)y-10, 64, 52);}

}
