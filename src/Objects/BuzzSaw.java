package Objects;

import Main.Game;
import Utilities.GameObject;
import Utilities.ID;
import Utilities.ImageSheet;
import Utilities.ObjectHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BuzzSaw extends GameObject {
    private ObjectHandler oHandler;
    private BufferedImage buzzSawImage;

    // todo make this rotate
    public BuzzSaw(float x, float y, ID id, ObjectHandler oHandler, ImageSheet imageSheet) {
        super(x, y, id, imageSheet);
        this.oHandler = oHandler;
        this.buzzSawImage = oHandler.imageGrabber("/buzzSaw.png", 4, 4);

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
        // Make the saw spin
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.rotate(Math.toRadians(45), x + 8, y + 8);
//        g.fillRect((int)x, (int)y, 16, 16);
        g.drawImage(this.buzzSawImage, (int)x, (int)y, 48, 48,null);
//        g2d.rotate(Math.toRadians(-45), x + 8, y + 8);
    }
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 48, 48); // this is the size of the hit box
    }
    public Rectangle getBoundsBig() {
        return new Rectangle((int)x , (int)y, 48, 48); // 32, 32 is the size of the player
    }
}