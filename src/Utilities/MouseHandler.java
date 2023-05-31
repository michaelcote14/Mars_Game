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
        this.camera = camera;
        this.game = game;
        this.imageSheet = imageSheet;
    }

    public void mousePressed(MouseEvent e) {
        int mx = (int) (e.getX() + camera.getCameraX());
        int my = (int) (e.getY() + camera.getCameraY());

        for(int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);

            if(tempObject.getId() == ID.Player && HUD.ammo > 0) {
                oHandler.addObject(new Bullet(tempObject.getX() + 16, tempObject.getY() + 24, ID.Bullet, oHandler, mx, my, imageSheet));
                HUD.ammo--;
            }
        }
    }

}

