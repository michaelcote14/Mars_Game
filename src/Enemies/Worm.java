package Enemies;

import Main.HUD;
import Main.Player;
import Objects.HealthPack;
import Utilities.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Worm extends GameObject {
    private ObjectHandler oHandler;
    private HUD hud;
    private GameObject player;
    private BufferedImage[] images;
    Animation fightingAnim, divingAnim, surfacingAnim;
    private int randNum;
    int hp = 1;
    Random rand = new Random();

    boolean didBulletCollide = false;
    int collisionCounter = 0;
    int bulletCollisionX, bulletCollisionY;
    private boolean hittingPlayer;
    private boolean inRange;
    private boolean hasDived;
    private int hasSurfacedCounter;
    private int hasDivedCounter;

    public Worm(float x, float y, ID id, ObjectHandler oHandler) {
        super(x, y, id);
        this.oHandler = oHandler;
        this.divingAnim = getImages(3, "wormDive");
//        this.surfacingAnim = getImages(3, "wormSurface");
        this.fightingAnim = getImages(6, "wormAttack");

        this.randNum = rand.nextInt(4);
        this.hp = hud.wave;

        this.hittingPlayer = false;
        this.inRange = false;
        this.hasSurfacedCounter = 0;
        this.hasDived = false;
        this.hasDivedCounter = 0;

        // Finds the player so that it can track him
        for(int i = 0; i < oHandler.object.size(); i++) {
            if(oHandler.object.get(i).getId() == ID.Player) player = oHandler.object.get(i);
        }
    }
    public Animation getImages(int imageCount, String imageName) {
        BufferedImage[] images = new BufferedImage[imageCount];

        for(int i = 0; i < imageCount; i++) {
            images[i] = ImageHandler.images.get(imageName + (i+1));
        }
        Animation animation = new Animation(400, images);

        return animation;
    }
    @Override
    public void tick() {
        this.inRange = true;
        this.hittingPlayer = false;

        x += velX;
        y += velY;

        // the 8 below is because we want to track the center of the object, you will have to adjust this depending on the object
        float diffX = x - player.getX() - 16;
        float diffY = y - player.getY() - 16;
        float distance = (float)Math.sqrt((x- player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()));

        velX = (float) ((-1.0 / distance) * diffX);
        velY = (float) ((-1.0 / distance) * diffY);

        if(distance > 300) {
            velX = 0;
            velY = 0;
            this.inRange = false;
        }

        for(int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);
            // Bullet collision
            if(tempObject.getId() == ID.Bullet) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    this.hp -= Player.basicAttackDamage;
                    Player.currentHealth += Player.lifeStealRate;
                    didBulletCollide = true;
                    bulletCollisionX = (int)x;
                    bulletCollisionY = (int)y;
                    oHandler.removeObject(tempObject);
                }
            }
            else if(tempObject.getId() == ID.Player) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    this.hittingPlayer = true;
                    velX = 0;
                    velY = 0;
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
            Player.currentXp += 20;
            HUD.killTracker += 1;
            dropLoot();
        }
        this.hasDivedCounter ++;
        this.hasSurfacedCounter ++;

    }
    @Override
    public void render(Graphics g) {
        if(this.hasDivedCounter > 100) {
            this.hasDived = true;
            this.hasDivedCounter = 0;
        }
        if(this.inRange == false) {
            g.drawImage(ImageHandler.images.get("wormAttack4"), (int)x, (int)y, 30, 30, null);
            this.hasDived = false;
        }
        else if (this.inRange == true && this.hasDived == false) {
            g.drawImage(ImageHandler.images.get("wormDive1"), (int)x, (int)y, 30, 30, null);
        }
        if(this.inRange == true && (this.hittingPlayer == true)){
            this.fightingAnim.runAnimation();
            this.fightingAnim.drawAnimation(g, (int)x, (int)y, 0, 30, 30);
        }
        else if(this.inRange == true && this.hittingPlayer == false && this.hasSurfacedCounter > 200) {
            g.drawImage(ImageHandler.images.get("wormAttack4"), (int)x, (int)y, 30, 30, null);
        }
        if(didBulletCollide == true) {
            g.drawImage(oHandler.bulletExplosionImage, (int)x, (int)y, 30, 30,null);
        }
        if(collisionCounter > 200) { // the time of how long the explosion lasts
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

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }
}
