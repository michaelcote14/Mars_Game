package Main;

import Main.Game;
import Main.HUD;
import Utilities.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    ObjectHandler oHandler;
    private BufferedImage[] playerImage = new BufferedImage[3];
    Game game;


    Animation anim;

    public Player(float x, float y, ID id, ObjectHandler oHandler, Game game, ImageSheet imageSheet) {
        super(x, y, id, imageSheet);
        this.oHandler = oHandler;
        this.game = game;

        playerImage[0] = imageSheet.grabImage(1, 1, 32, 48);
        playerImage[1] = imageSheet.grabImage(2, 1, 32, 48);
        playerImage[2] = imageSheet.grabImage(3, 1, 32, 48);

        anim = new Animation(3, playerImage);
    }


    public void tick() {
        collision();

        x += velX;
        y += velY;

        if(oHandler.isDownPressed()) velY = 5;
        else if(!oHandler.isUpPressed()) velY = 0;

        if(oHandler.isUpPressed()) velY = -5;
        else if(!oHandler.isDownPressed()) velY = 0;

        if(oHandler.isRightPressed()) velX = 5;
        else if(!oHandler.isLeftPressed()) velX = 0;

        if(oHandler.isLeftPressed()) velX = -5;
        else if(!oHandler.isRightPressed()) velX = 0;

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
                    HUD.health -= 2;
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

