package Main;

import Utilities.*;
import Utilities.MouseHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    ObjectHandler oHandler;
    private BufferedImage[] playerImage = new BufferedImage[3];
    Game game;
    Camera camera;
    MouseHandler mouseHandler;

    Animation anim;

    public static int fireRate = 1;
    int fireRateCounter = 0;
    public static int speed = 4;

    public Player(float x, float y, ID id, ObjectHandler oHandler, Game game, ImageSheet imageSheet, Camera camera) {
        super(x, y, id, imageSheet);
        this.oHandler = oHandler;
        this.game = game;
        this.camera = camera;
        this.mouseHandler = mouseHandler;

        // Stats
        this.fireRate = 5;
        this.speed = 4;

        playerImage[0] = imageSheet.grabImage(1, 1, 32, 48);
        playerImage[1] = imageSheet.grabImage(2, 1, 32, 48);
        playerImage[2] = imageSheet.grabImage(3, 1, 32, 48);

        anim = new Animation(3, playerImage);
    }
    public void deathStatDecrease() {
        this.fireRate -= 10;
        this.speed -= 10;
        HUD.maxHealth = HUD.maxHealth - 100; // you lose 10 max health when you die
    }

    public void tick() {
        collision();

        x += velX;
        y += velY;

        if(oHandler.isDownPressed()) velY = 3 + speed/10;
        else if(!oHandler.isUpPressed()) velY = 0;

        if(oHandler.isUpPressed()) velY = -3 - speed/10;
        else if(!oHandler.isDownPressed()) velY = 0;

        if(oHandler.isRightPressed()) velX = 3 + speed/10;
        else if(!oHandler.isLeftPressed()) velX = 0;

        if(oHandler.isLeftPressed()) velX = -3 - speed/10;
        else if(!oHandler.isRightPressed()) velX = 0;

        if(oHandler.isMouseClicked()) {
            if(fireRateCounter % (10 - this.fireRate) == 0) {
                PointerInfo a = MouseInfo.getPointerInfo();
                Point b = a.getLocation();
                MouseHandler.shoot(b.x, b.y, oHandler, imageSheet, camera);
            }
            fireRateCounter++;
        }
        anim.runAnimation();
    }

    private void collision() {
        for (int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);

            if (tempObject.getId() == ID.Block) {
                if (!is_room_to_move((int) (x + velX), (int)y, getBounds(), tempObject.getBounds())) {
                    velX = 0;
                }
                else if (!is_room_to_move((int)x, (int) (y + velY), getBounds(), tempObject.getBounds())) {
                    velY = 0;
                }
            }
            else if(tempObject.getId() == ID.BasicEnemy) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    HUD.currentHealth -= 2;
                }
            }
            else if(tempObject.getId() == ID.Crate) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    HUD.ammo += 10;
                    oHandler.removeObject(tempObject);
                }
            }
        }
    }

    public void render(Graphics g) {
        if(velX == 0 && velY == 0)
            g.drawImage(playerImage[0], (int)x, (int)y, null);
        else
            anim.drawAnimation(g, x, y, 0);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 48); // 32, 48 is the size of the wizard
    }

    public boolean is_room_to_move(int x, int y, Rectangle myRect, Rectangle otherRect) {
        myRect.x = x;
        myRect.y = y;
        if(myRect.intersects(otherRect)) {
            return false;
        }
        return true;
    }
}

