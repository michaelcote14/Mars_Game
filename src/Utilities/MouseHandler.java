package Utilities;

import Main.Game;
import Objects.Bullet;
import Main.HUD;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    private ObjectHandler oHandler;
    private Game game;
    private ImageSheet imageSheet;

    private boolean mouseClicked = false;


    public MouseHandler(ObjectHandler oHandler, Camera camera, ImageSheet imageSheet, Game game) {
        this.oHandler = oHandler;
        this.game = game;
        this.imageSheet = imageSheet;
    }

    public void mousePressed(MouseEvent mouseEvent) {setMouseClicked(true);
    }
    public static void shoot(int mouseX, int mouseY, ObjectHandler oHandler, ImageSheet imageSheet, Camera camera) {
        int mouseX2 = (int)(mouseX + camera.getPlayerX() -(Game.WIDTH/2 - 30));
        int mouseY2 = (int)(mouseY + camera.getPlayerY() -(Game.HEIGHT/2 - 10));

        for (int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);
            if (tempObject.getId() == ID.Player && HUD.ammo > 0) {
                oHandler.addObject(new Bullet(tempObject.getX() + 16, tempObject.getY() + 24, ID.Bullet, oHandler, mouseX2, mouseY2, imageSheet));
            }
        }
    }
    public void mouseReleased(MouseEvent mouseEvent) {setMouseClicked(false);
    }
    public boolean setMouseClicked(boolean mouseClicked) {return this.mouseClicked = mouseClicked;}
    public boolean isMouseClicked() {return mouseClicked;}
}

