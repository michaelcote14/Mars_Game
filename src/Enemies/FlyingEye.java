package Enemies;

import Main.Player;
import Objects.Chest;
import Objects.HealthPack;
import Utilities.GameObject;
import Utilities.ObjectHandler;
import Utilities.ImageHandler;
import Utilities.Animation;
import Utilities.ID;

import Main.HUD;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class FlyingEye extends GameObject {
    private ObjectHandler oHandler;
    private HUD hud;
    private BufferedImage[] images;
    Animation anim;

    Random rand = new Random();
    int directionChoice = 0;
    int hp = 1;

    boolean didBulletCollide = false;
    int collisionCounter = 0;
    int bulletCollisionX, bulletCollisionY;

    public FlyingEye(float x, float y, ID id, ObjectHandler oHandler) {
        super(x, y, id);
        this.oHandler = oHandler;
        this.images = ImageHandler.createImageArray("flyingEye", 6);
        anim = new Animation(100, images);
        this.hp = hud.wave;
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
            // Bullet collision
            else if(tempObject.getId() == ID.Bullet) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    this.hp -= Player.basicAttackDamage;
                    Player.currentHealth += Player.lifeStealRate;
                    didBulletCollide = true;
                    bulletCollisionX = (int)x;
                    bulletCollisionY = (int)y;
                    oHandler.removeObject(tempObject);
                }
            }
            else if(tempObject.getId().toString().contains("Ability")) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    if(tempObject.getId().toString().startsWith(Player.ability1Name)) {
                        this.hp -= Player.ability1Damage;
                    }
                    else if(tempObject.getId().toString().startsWith(Player.ability2Name)) {
                        this.hp -= Player.ability2Damage;
                    }
                    else if(tempObject.getId().toString().startsWith(Player.ability3Name)) {
                        this.hp -= Player.ability3Damage;
                    }
                }
            }
            else if(tempObject.getId() == ID.Explosion) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    this.hp -= 20;
                }
            }
        }
        if(this.hp <= 0) {
            oHandler.removeObject(this);
            Player.money += 1;
            Player.currentXp += 1;
            HUD.scoreTracker += 1;
            dropLoot();
        }

        if(directionChoice == 0) {
            velX = (rand.nextInt(4 - -4) + -4);
            velY = (rand.nextInt(4 - -4) + -4);
        }
    }

    @Override
    public void render(Graphics g) {
//        Rectangle rect = getBounds(); // this is how to show the hit box
//        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        anim.runAnimation();
        anim.drawAnimation(g, x, y, 0, 32, 32);

        if(didBulletCollide == true) {
            g.drawImage(oHandler.bulletExplosionImage, (int)x, (int)y, 30, 30,null);
        }
        if(collisionCounter > 200) {
            didBulletCollide = false;
            collisionCounter = 0;
        }
        collisionCounter ++;
    }

    public void dropLoot() {
        if(rand.nextInt(20) == 0) { // this means there is a 1 in 10 chance of dropping a crate
            oHandler.addObject(new HealthPack(x, y, ID.HealthPack, oHandler));
        }
    }

    @Override
    public Rectangle getBounds() {return new Rectangle((int)x, (int)y+2, 30, 28);} // this defines the hitbox
    public Rectangle getBoundsBig() {return new Rectangle((int)x - 16, (int)y - 16, 64, 64);}

}
