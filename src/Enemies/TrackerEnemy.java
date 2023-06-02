package Enemies;

import Main.HUD;
import Main.Player;
import Utilities.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class TrackerEnemy extends GameObject {
    private ObjectHandler oHandler;
    private GameObject player;
    private BufferedImage[] images;
    private int animationTimeCounter = 0;
    private int randNum;
    int hp = 5;
    Random rand = new Random();

    boolean didBulletCollide = false;
    int collisionCounter = 0;
    int bulletCollisionX, bulletCollisionY;

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
        // Put them all into an array
        BufferedImage[] images = new BufferedImage[4];
        images[0] = oHandler.imageGrabber("/Enemies/ghost1.png", 15, 17);
        images[1] = oHandler.imageGrabber("/Enemies/ghost2.png", 15, 17);
        images[2] = oHandler.imageGrabber("/Enemies/ghost3.png", 15, 17);
        images[3] = oHandler.imageGrabber("/Enemies/ghost4.png", 15, 17);

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

        velX = (float) ((-1.0 / distance) * diffX);
        velY = (float) ((-1.0 / distance) * diffY);
        if(distance > 400) {
            velX = 0;
            velY = 0;
        }

        for(int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);
            // Bullet collision
            if(tempObject.getId() == ID.Bullet) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    hp -= Player.basicAttackDamage;
                    didBulletCollide = true;
                    bulletCollisionX = (int)x;
                    bulletCollisionY = (int)y;
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
        g.drawImage(this.images[this.randNum], (int) x, (int) y, 30, 30, null);
        if(didBulletCollide == true) {
            g.drawImage(oHandler.bulletExplosionImage, (int)x, (int)y, 30, 30,null);
        }
        if(collisionCounter > 200) { // the time of how long the explosion lasts
            didBulletCollide = false;
            collisionCounter = 0;
        }
        collisionCounter ++;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x+8, (int)y+4, 23, 31);
    }
}
