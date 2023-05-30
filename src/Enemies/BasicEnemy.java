package Enemies;

import Utilities.GameObject;
import Utilities.ObjectHandler;
import Utilities.ImageSheet;
import Utilities.Animation;
import Utilities.ID;

import Main.HUD;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BasicEnemy extends GameObject {
    private ObjectHandler oHandler;
    Random rand = new Random();
    int directionChoice = 0;
    int hp = 100;

    private BufferedImage[] enemyImage = new BufferedImage[3];
    Animation anim;

    public BasicEnemy(float x, float y, ID id, ObjectHandler oHandler, ImageSheet imageSheet) {
        super(x, y, id, imageSheet);
        this.oHandler = oHandler;

        if(imageSheet != null) {
            enemyImage[0] = imageSheet.grabImage(4, 1, 32, 32);
            enemyImage[1] = imageSheet.grabImage(5, 1, 32, 32);
            enemyImage[2] = imageSheet.grabImage(6, 1, 32, 32);

            anim = new Animation(3, enemyImage);
        }
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        directionChoice = rand.nextInt(10);

        for(int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);


            // This makes the enemy bounce off of the block boundaries
            if(tempObject.getId() == ID.Block) {
                if(getBoundsBig().intersects(tempObject.getBounds())) {
                    x += (velX * 5) * -1;
                    y += (velY * 5) * -1;
                    velX *= -1;
                    velY *= -1;
                }
                else if(directionChoice == 0) {
                    velX = (rand.nextInt(4 - -4) + -4);
                    velY = (rand.nextInt(4 - -4) + -4);
                }
            }
            else if(tempObject.getId() == ID.Bullet) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    hp -= 50;
                    oHandler.removeObject(tempObject);
                }
            }
        }
        if(hp <= 0) {
            oHandler.removeObject(this);
            HUD.score += 1;
            HUD.scoreTracker += 1;
        }

        if(directionChoice == 0) {
            velX = (rand.nextInt(4 - -4) + -4);
            velY = (rand.nextInt(4 - -4) + -4);
        }
    }

    @Override
    public void render(Graphics g) {
        anim.runAnimation();
        anim.drawAnimation(g, x, y, 0);
    }

    @Override
    public Rectangle getBounds() {return new Rectangle((int)x, (int)y, 32, 32);}

    // this makes sure the collision box is slightly bigger than the enemy
    public Rectangle getBoundsBig() {return new Rectangle((int)x - 16, (int)y - 16, 64, 64);}
}
