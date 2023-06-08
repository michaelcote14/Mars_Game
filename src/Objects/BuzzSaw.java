package Objects;

import Utilities.GameObject;
import Utilities.ID;
import Utilities.ImageHandler;
import Utilities.ObjectHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BuzzSaw extends GameObject {
    private ObjectHandler oHandler;
    private BufferedImage image;
    private int rotation = 15;
    private int rotationCounter = 0;

    public BuzzSaw(float x, float y, ID id, ObjectHandler oHandler) {
        super(x, y, id);
        this.oHandler = oHandler;
        this.image = oHandler.imageGrabber("/Objects/buzzSaw.png", 4, 4);

        velX = 2;

    }
    @Override
    public void tick() {
        x += velX;
        y += velY;

        // Make the saw go left and right
        if(velX == 0) {
            velX = 2;
            if(velX > 0) velX += 0.005f;
            else if(velX < 0) velX -= 0.005f;
        }
        // Collision
        for(int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);

            // This makes the enemy bounce off of the block boundaries
            if (tempObject.getId() == ID.Block) {
                if (getBoundsBig().intersects(tempObject.getBounds())) {
                    x += (velX * 5) * -1;
                    y += (velY * 5) * -1;
                    velX *= -1;
                    velY *= -1;
                }
            }
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
        g2d.drawImage(image, -25, -25, 50, 50, null);
        g2d.dispose();
        if(rotationCounter > 3) {
            rotation += 1;
            rotationCounter = 0;
        }
        rotationCounter++;
    }
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 48, 48); // this is the size of the hit box
    }

    public Rectangle getBoundsBig() {
        return new Rectangle((int)x , (int)y, 48, 48); // 32, 32 is the size of the player
    }
}