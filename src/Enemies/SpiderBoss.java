package Enemies;

import Main.HUD;
import Main.Player;
import Objects.DroppedItems;
import Objects.HealthPack;
import Utilities.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SpiderBoss extends GameObject {
    private ObjectHandler oHandler;
    private HUD hud;
    private GameObject player;
    private BufferedImage[] downImages, leftImages, upImages;
    private BufferedImage projectile, cannon;
    private int legHitCounter = 0;
    private int headHitCounter = 0;
    private int abdomenHitCounter = 0;
    private String bodyPartToFlash = "";
    Animation anim;
    boolean isShooting = false;

    Random rand = new Random();
    private int randNum;

    private int changeDirectionCounter = 1;
    int directionChoice = 0;
    int hp;

    private float bulletVelX, bulletVelY;

    boolean didBulletCollide = false;
    int collisionCounter = 0;
    int bulletCollisionX, bulletCollisionY;

    public SpiderBoss(float x, float y, ID id, ObjectHandler oHandler) {
        super(x, y, id);
        this.oHandler = oHandler;
        this.downImages = ImageHandler.createImageArray("spiderBossDown", 3);
        this.leftImages = ImageHandler.createImageArray("spiderBossLeft", 3);
        this.upImages = ImageHandler.createImageArray("spiderBossUp", 3);
        projectile = ImageHandler.images.get("spiderBossProjectile");
        cannon = ImageHandler.images.get("spiderBossCannon");

        anim = new Animation(400, this.downImages);
        this.hp = 20;
        velX = 1;
        velY = 1;

        // Finds the player so that it can track him
        for(int i = 0; i < oHandler.object.size(); i++) {
            if(oHandler.object.get(i).getId() == ID.Player) {
                player = oHandler.object.get(i);
            }
        }
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
            if(tempObject.getId() == ID.BlockObject) {
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
            else if(tempObject.getId().toString().contains("Ability") || tempObject.getId() == ID.Bullet) {
                if(getBoundsHead().intersects(tempObject.getBounds())) {
                    hitCountAdder(tempObject, "head");
                    bodyPartToFlash = "head";
                }
                else if(getBoundsAbdomen().intersects(tempObject.getBounds())) {
                    hitCountAdder(tempObject, "abdomen");
                    bodyPartToFlash = "abdomen";
                }
                else if(getBoundsLeftLegs().intersects(tempObject.getBounds()) || getBoundsRightLegs().intersects(tempObject.getBounds())) {
                    hitCountAdder(tempObject, "legs");
                    bodyPartToFlash = "legs";
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
            Player.currentXp += 20;
            HUD.killTracker += 1;
            dropLoot();
        }

        if(directionChoice == 0) {
            velX = (rand.nextInt(4 - -4) + -4);
            velY = (rand.nextInt(4 - -4) + -4);
        }

        // the 16 below is because we want to track the center of the object, you will have to adjust this depending on the object
        float diffX = x - player.getX() - 16;
        float diffY = y - player.getY() - 16;
        float distance = (float)Math.sqrt((x- player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()));

        bulletVelX = (float)((-1.0/distance) * diffX * 3); // 3 is the speed of the bullets
        bulletVelY = (float)((-1.0/distance) * diffY * 3);

        if(isShooting == true && distance < 450) { // range of the enemy
            oHandler.addObject(new EnemyBullet((int)x + 8, (int)y + 8, bulletVelX, bulletVelY, ID.EnemyBullet, oHandler, projectile));
            isShooting = false;
        }
    }
    private void hitCountAdder(GameObject tempObject, String bodySection) {
        System.out.println("leg hit counter: " + legHitCounter);
//        System.out.println("head hit counter: " + headHitCounter);
//        System.out.println("abdomen hit counter: " + abdomenHitCounter);
        if(tempObject.getId().toString().startsWith(Player.ability1Name)) {
            this.hp -= Player.ability1Damage;
            if(bodySection.equals("head")) headHitCounter+= Player.ability1Damage;
            else if(bodySection.equals("legs")) legHitCounter+= Player.ability1Damage;
            else if(bodySection.equals("abdomen")) abdomenHitCounter+= Player.ability1Damage;
        }
        else if(tempObject.getId().toString().startsWith(Player.ability2Name)) {
            this.hp -= Player.ability2Damage;
            if(bodySection.equals("head")) headHitCounter+= Player.ability2Damage;
            else if(bodySection.equals("legs")) legHitCounter+= Player.ability2Damage;
            else if(bodySection.equals("abdomen")) abdomenHitCounter+= Player.ability2Damage;
        }
        else if(tempObject.getId().toString().startsWith(Player.ability3Name)) {
            this.hp -= Player.ability3Damage;
            if(bodySection.equals("head")) headHitCounter+= Player.ability3Damage;
            else if(bodySection.equals("legs")) legHitCounter+= Player.ability3Damage;
            else if(bodySection.equals("abdomen")) abdomenHitCounter+= Player.ability3Damage;
        }
        else if(tempObject.getId() == ID.Bullet) {
            this.hp -= Player.basicAttackDamage;
            Player.currentHealth += Player.lifeStealRate;
            didBulletCollide = true;
            bulletCollisionX = (int)x;
            bulletCollisionY = (int)y;
            oHandler.removeObject(tempObject);
            if(bodySection.equals("head")) headHitCounter+= Player.basicAttackDamage;
            else if(bodySection.equals("legs")) legHitCounter+= Player.basicAttackDamage;
            else if(bodySection.equals("abdomen")) abdomenHitCounter+= Player.basicAttackDamage;
        }
    }
    @Override
    public void render(Graphics g) {
//        if((anim.getCount() == 3) && isShooting == false) {
//            isShooting = true;
//        }
//        else {isShooting = false;}
        if(!bodyPartToFlash.isEmpty()) {
        }

        anim.runAnimation();
        anim.drawAnimation(g, (int)x, (int)y, 15);

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
        return new Rectangle(
                (int)x-15, (int)y, 200, 200);
    }
    public void dropLoot() {
        if(legHitCounter > abdomenHitCounter && legHitCounter > headHitCounter) {
            oHandler.addObject(new DroppedItems(x + 100, y + 100, ID.DroppedItems, oHandler, "spiderBossLeg"));
        }
        if(rand.nextInt(20) == 0) { // this means there is a 1 in 10 chance of dropping a crate
            oHandler.addObject(new HealthPack(x + 100, y + 100, ID.HealthPack, oHandler));
        }
    }
    // this makes sure the collision box is slightly bigger than the enemy
    public Rectangle getBoundsBig() {return new Rectangle((int)x - 16, (int)y - 16, 64, 64);}

    public Rectangle getBoundsLeftLegs() {
        return new Rectangle((int)x-20, (int)y+80, 60, 110); // 16, 16 is the size of the enemy
    }
    public Rectangle getBoundsRightLegs() {
        return new Rectangle((int)x+130, (int)y+80, 60, 110); // 16, 16 is the size of the enemy
    }
    public Rectangle getBoundsAbdomen() {
        return new Rectangle((int)x + 18, (int)y, 140, 145); // 16, 16 is the size of the enemy
    }
    public Rectangle getBoundsHead() {
        return new Rectangle((int)x + 70, (int)y + 145, 38, 38); // 16, 16 is the size of the enemy
    }

}
