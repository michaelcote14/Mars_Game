package Enemies;

import Main.Game;
import Utilities.GameObject;
import Utilities.ID;
import Utilities.ImageSheet;
import Utilities.ObjectHandler;

import java.awt.*;
import java.util.Random;

public class EnemyBullet extends GameObject {
    private ObjectHandler oHandler;
    Random rand = new Random();
    private int bulletDeleteTimer = 100;


    public EnemyBullet(float enemyX, float enemyY, float velX, float velY, ID id, ObjectHandler oHandler, ImageSheet imageSheet) {
        super(enemyX, enemyY, id, imageSheet);
        this.velX = velX;
        this.velY = velY;
        this.bulletDeleteTimer = 40; // this allows you to control the range

        this.oHandler = oHandler;

        // How to shoot randomly in a direction
//        velX = (rand.nextInt(5 - -5) + -5);
//        velY = 5;

    }
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 16, 16); // 16, 16 is the size of the enemy
    }

    @Override
    public void tick() {
        x += this.velX;
        y += this.velY;

        for(int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);

            // This makes the enemy bounce off of the block boundaries
            if (tempObject.getId() == ID.Block) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    x += (velX * 1) * -1;
                    y += (velY * 1) * -1;
//                    velX *= -1; // this will make the bullets bounce off of walls
//                    velY *= -1;
                    oHandler.removeObject(this);
                }
            }
        }
        if(this.bulletDeleteTimer <= 0) oHandler.removeObject(this);
        else bulletDeleteTimer--;

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)y, 16, 16);
    }
}

