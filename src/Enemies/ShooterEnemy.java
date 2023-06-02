package Enemies;

import Main.Game;
import Main.HUD;
import Main.Player;
import Objects.HealthPack;
import Utilities.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static Utilities.ID.EnemyBullet;

public class ShooterEnemy extends GameObject {
    private ObjectHandler oHandler;
    private GameObject player;
    private BufferedImage[] images;
    private int animationTimeCounter = 0;
    private int randNum;
    private int changeDirectionCounter = 1;
    int directionChoice = 0;
    int hp = 5;
    Random rand = new Random();

    private float bulletVelX, bulletVelY;

    boolean didBulletCollide = false;
    int collisionCounter = 0;
    int bulletCollisionX, bulletCollisionY;

    public ShooterEnemy(float x, float y, ID id, ObjectHandler oHandler, ImageSheet imageSheet) {
        super(x, y, id, null);
        this.oHandler = oHandler;
        this.images = getImages();
        this.randNum = rand.nextInt(4);
        velX = 1;
        velY = 1;

        // Finds the player so that it can track him
        for(int i = 0; i < oHandler.object.size(); i++) {
            if(oHandler.object.get(i).getId() == ID.Player) {
                player = oHandler.object.get(i);
            }
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

        // Only change the direction after a certain amount of time
        if(changeDirectionCounter <= 0) {
            directionChoice = rand.nextInt(10);
        }
        else {
            changeDirectionCounter--;
        }

        for(int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);

            // This makes the enemy bounce off of the block boundaries
            if(tempObject.getId() == ID.Block) {
                if(getBoundsBig().intersects(tempObject.getBounds())) {
                    x += (velX * 1) * -1;
                    y += (velY * 1) * -1;
                    velX *= -1;
                    velY *= -1;
                }
                else if(directionChoice == 0) {
                    velX = (rand.nextInt(10 - -10) + -10);
                    velY = (rand.nextInt(10 - -10) + -10);
                }
            }
            // Bullet collision
            else if(tempObject.getId() == ID.Bullet) {
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
            dropLoot();
        }

        if(directionChoice == 0) {
            velX = (rand.nextInt(4 - -4) + -4);
            velY = (rand.nextInt(4 - -4) + -4);
        }

        // todo make a range on this so that it doesn't shoot when the player is too far away

        // the 16 below is because we want to track the center of the object, you will have to adjust this depending on the object
        float diffX = x - player.getX() - 16;
        float diffY = y - player.getY() - 16;
        float distance = (float)Math.sqrt((x- player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()));

        bulletVelX = (float)((-1.0/distance) * diffX * 3); // 3 is the sped of the bullets
        bulletVelY = (float)((-1.0/distance) * diffY * 3);

        int spawnBullet = rand.nextInt(50);
        if(spawnBullet == 0 && distance < 350) {
            oHandler.addObject(new EnemyBullet((int)x + 8, (int)y + 8, bulletVelX, bulletVelY, ID.EnemyBullet, oHandler, null));
        }

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
            dropLoot();
        }
    }
    @Override
    public void render(Graphics g) {
        // Make the image red instead
        g.setColor(Color.orange);
        g.fillRect((int)x, (int)y, 30, 30);
//        g.drawImage(this.images[this.randNum], (int) x, (int) y, 30, 30, null);
        if(didBulletCollide == true) {
            g.drawImage(oHandler.bulletExplosionImage, (int)x, (int)y, 30, 30,null);
        }
        if(collisionCounter > 200) { // the time of how long the explosion lasts
            didBulletCollide = false;
            collisionCounter = 0;
        }
        collisionCounter ++;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x+8, (int)y+4, 23, 31);
    }
    public void dropLoot() {
        if(rand.nextInt(20) == 0) { // this means there is a 1 in 10 chance of dropping a crate
            oHandler.addObject(new HealthPack(x, y, ID.HealthPack, oHandler, imageSheet));
        }
    }
    // this makes sure the collision box is slightly bigger than the enemy
    public Rectangle getBoundsBig() {return new Rectangle((int)x - 16, (int)y - 16, 64, 64);}

}
