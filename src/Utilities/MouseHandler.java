package Utilities;

import Main.Game;
import Objects.Bullet;
import Main.HUD;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    private ObjectHandler oHandler;
    private Camera camera;
    private Game game;
    private ImageSheet imageSheet;

    public MouseHandler(ObjectHandler oHandler, Camera camera, ImageSheet imageSheet, Game game) {
        this.oHandler = oHandler;
        this.game = game;
        this.imageSheet = imageSheet;
    }

    public void mousePressed(MouseEvent mouseEvent) {
        oHandler.setMouseClicked(true);
    }
    public static void shoot(int mouseX, int mouseY, ObjectHandler oHandler, ImageSheet imageSheet, Camera camera) {

        System.out.println("\nmouse X/Y: " + mouseX + "/" + mouseY);
        System.out.println("camera X/Y: " + camera.getPlayerX() + "/" + camera.getPlayerY());
        int mouseX2 = mouseX;
        int mouseY2 = mouseY;

        for (int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);
            // todo grab all the array list players by name instead of this for loop

            if (tempObject.getId() == ID.Player && HUD.ammo > 0) {
                oHandler.addObject(new Bullet(tempObject.getX() + 16, tempObject.getY() + 24, ID.Bullet, oHandler, mouseX2, mouseY2, imageSheet));
                HUD.ammo--;
            }
        }
    }
    public void mouseReleased(MouseEvent mouseEvent) {
        oHandler.setMouseClicked(false);
    }

}

