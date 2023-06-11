package Enemies;

import Main.Player;
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

public class EnemySpawnerPortal extends GameObject {
    private ObjectHandler oHandler;
    private GameObject player;
    private HUD hud;
    private BufferedImage[] images;
    private BufferedImage image;
    Animation anim;
    private int enemySpawnTimer = 0;
    private int enemiesSpawned = 0;
    private boolean inRange;

    Random rand = new Random();
    int hp = 10;

    boolean didBulletCollide = false;
    int collisionCounter = 0;
    int bulletCollisionX, bulletCollisionY;

    public EnemySpawnerPortal(float x, float y, ID id, ObjectHandler oHandler) {
        super(x, y, id);
        this.oHandler = oHandler;
        this.images = ImageHandler.createImageArray("enemySpawnerPortal", 24);
        anim = new Animation(60, images);
        this.hp = hud.wave * hp;
        velX = 0;
        velY = 0;

        // Finds the player so that it can track him
        for(int i = 0; i < oHandler.object.size(); i++) {
            if(oHandler.object.get(i).getId() == ID.Player) {
                player = oHandler.object.get(i);
            }
        }

    }
    @Override
    public void tick() {
        inRange = true;

        // the 8 below is because we want to track the center of the object, you will have to adjust this depending on the object
        float diffX = x - player.getX() - 16;
        float diffY = y - player.getY() - 16;
        float distance = (float)Math.sqrt((x- player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()));

        velX = (float) ((-1.0 / distance) * diffX);
        velY = (float) ((-1.0 / distance) * diffY);

        if(distance > 400) {
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
            Player.currentXp += 1;
            HUD.killTracker += 1;
            dropLoot();
        }
        if(enemySpawnTimer > 160 && inRange == true && enemiesSpawned < 30) {
            oHandler.addObject(new FlyingEye((int)x+40, (int)y+100, ID.FlyingEyeEnemy, oHandler));
            enemySpawnTimer = 0;
        }

        enemySpawnTimer ++;
    }
    @Override
    public void render(Graphics g) {
        if(this.inRange == true) {
            anim.runAnimation();
            anim.drawAnimation(g, x, y, 0);
        }

        if(didBulletCollide == true) {
            // scale this bullet explosion image somewhere it wont be continuously drawn
            g.drawImage(oHandler.bulletExplosionImage, (int)x+30, (int)y+50, 30, 30,null);
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
    public Rectangle getBounds() {return new Rectangle((int)x, (int)y+2, 90, 120);} // this defines the hitbox
    public Rectangle getBoundsBig() {return new Rectangle((int)x - 16, (int)y - 16, 64, 64);}

}
