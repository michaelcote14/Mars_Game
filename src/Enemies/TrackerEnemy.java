package Enemies;

import Main.Game;
import Main.HUD;
import Main.Player;
import Utilities.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class TrackerEnemy extends GameObject {
    private ObjectHandler oHandler;
    private GameObject player;
    private BufferedImage image1, image2, image3, image4;
    private BufferedImage[] images = new BufferedImage[4];
    private int animationTimeCounter = 0;
    private int randNum;
    int hp = 100;
    Random rand = new Random();


    public TrackerEnemy(float x, float y, ID id, ObjectHandler oHandler, ImageSheet imageSheet) {
        super(x, y, id, null);
        this.oHandler = oHandler;
        this.images = getImages();
        this.randNum = rand.nextInt(4);

        // Finds the player so that it can track him
        for(int i = 0; i < oHandler.object.size(); i++) {
            if(oHandler.object.get(i).getId() == ID.Player) player = oHandler.object.get(i);
        }
    }

    public BufferedImage[] getImages() {
        // todo re-crop the images
        // Put them all into an array
        BufferedImage[] images = new BufferedImage[4];
        images[0] = oHandler.imageGrabber("/Enemies/ghost1.png", 32, 32);
        images[1] = oHandler.imageGrabber("/Enemies/ghost2.png", 32, 32);
        images[2] = oHandler.imageGrabber("/Enemies/ghost3.png", 32, 32);
        images[3] = oHandler.imageGrabber("/Enemies/ghost4.png", 32, 32);

        return images;
    }
    @Override
    public void tick() {
        x += velX;
        y += velY;

        // the 8 below is because we want to track the center of the object, you will have to adjust this depending on the object
        float diffX = x - player.getX() - 16;
        float diffY = y - player.getY() - 16;
        float distance = (float)Math.sqrt((x- player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()));

        velX = (float)((-1.0/distance) * diffX);
        velY = (float)((-1.0/distance) * diffY);

        if(x <= 0 || x >= Game.WIDTH - 32) velX *= -1;
        if(y <= 0 || y >= Game.HEIGHT - 16) velY *= -1;


        if(animationTimeCounter > 20) {
            animationTimeCounter = 0;
            this.randNum = rand.nextInt(4);
        }
        else animationTimeCounter++;

        for(int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);
            // Bullet collision
            if(tempObject.getId() == ID.Bullet) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    hp -= 50;
                    oHandler.removeObject(tempObject);
                }
            }
        }
        if(hp <= 0) {
            oHandler.removeObject(this);
            Player.money += 1;
            HUD.scoreTracker += 1;
//            dropLoot();
        }
    }
    @Override
    public void render(Graphics g) {
        g.drawImage(this.images[this.randNum], (int) x, (int) y, 40, 40, null);
    }

    // this makes sure the collision box is slightly bigger than the enemy
    public Rectangle getBoundsBig() {return new Rectangle((int)x - 16, (int)y - 16, 64, 64);}
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 16, 16); // 16, 16 is the size of the enemy
    }
}
