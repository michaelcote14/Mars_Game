package Abilities;

import Utilities.ID;
import Utilities.ImageHandler;
import Utilities.ObjectHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RouletteWheel extends AbilityObject {
    ObjectHandler oHandler;
    BufferedImage image;
    int rotation = 15;
    int rotationCounter = 0;
    int spellDuration = (60 * 10); // 10 seconds
    int imageX;
    int imageY;
    public static float cooldown = 0;
    public RouletteWheel(float x, float y, ID id, ObjectHandler oHandler) {
        super(x, y, id);
        this.oHandler = oHandler;
        this.image = ImageHandler.images.get("rouletteWheel");
        this.cooldown = 0;
    }
    public RouletteWheel(ImageHandler imageHandler) {
        super(0,0, ID.valueOf("RouletteWheelSpell"));
        this.cooldown = 0;

    }
    @Override
    public void tick() {
        spellDuration--;
        if(spellDuration <= 0) {
            this.oHandler.removeObject(this);
        }
    }
    @Override
    public void render(Graphics g) {
        // Rotate the image continuously
        Graphics2D g2d = (Graphics2D) g.create();
        int centerX = (int) x + 32;
        int centerY = (int) y + 32;
        g2d.translate(centerX, centerY);
        g2d.rotate(Math.toRadians(rotation));
        imageX = -this.image.getWidth() / 10;
        imageY = -this.image.getHeight() / 10;

        g2d.drawImage(this.image, imageX, imageY, 100, 100, null);

        g2d.dispose();
        if(rotationCounter > 3) {
            rotation += 10;
            rotationCounter = 0;
        }
        rotationCounter++;
    }
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 100, 100);
    }
}
