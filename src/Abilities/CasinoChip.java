package Abilities;

import Utilities.GameObject;
import Utilities.ID;
import Utilities.ImageHandler;
import Utilities.ObjectHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CasinoChip extends AbilityObject {
    ObjectHandler oHandler;
    BufferedImage image;
    private double direction;
    private int speed = 10;
    int spellDuration = (60 * 5); // 5 seconds

    public static float cooldown = 0;
    public CasinoChip(float x, float y, ID id, ObjectHandler oHandler, int mouseX, int mouseY) {
        super(x, y, id);
        this.oHandler = oHandler;
        this.image = ImageHandler.images.get("casinoChip");

        float deltaX = mouseX - x;
        float deltaY = mouseY - y;
        direction = Math.atan2(deltaY, deltaX); // equivalent to Math.atan(deltaY/deltaX) but with an extra div.by.0 check
    }
    @Override
    public void tick() {
        spellDuration--;
        if(spellDuration <= 0) {
            this.oHandler.removeObject(this);
        }

        x = (int)(x + (speed * Math.cos(direction)));
        y = (int)(y + (speed * Math.sin(direction)));

        for(int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);

            if(tempObject.getId() == ID.BlockObject) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    // Make the chip bounce off the block
                    x = (int)(x - (speed * Math.cos(direction)));
                    y = (int)(y - (speed * Math.sin(direction)));
                    direction = Math.atan2(-Math.sin(direction), Math.cos(direction));
                    // Make it not get stuck in the block
                    x = (int)(x + (speed * Math.cos(direction)));
                    y = (int)(y + (speed * Math.sin(direction)));

                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.image, (int)x, (int)y,null);
    }
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 40, 40);
    }
    public Rectangle getBoundsBig() {
        return new Rectangle((int)x - 16, (int)y - 16, 64, 64);
    }
}
