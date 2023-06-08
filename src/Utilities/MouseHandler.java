package Utilities;

import Abilities.CardThrow;
import Main.Game;
import Objects.Bullet;
import Abilities.CasinoChip;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    private ObjectHandler oHandler;
    private Game game;
    private ImageHandler imageHandler;
    private KeyHandler keyHandler;

    private boolean mouseClicked = false;


    public MouseHandler(ObjectHandler oHandler, ImageHandler imageHandler, Game game) {
        this.oHandler = oHandler;
        this.game = game;
        this.imageHandler = imageHandler;
    }

    public void mousePressed(MouseEvent mouseEvent) {setMouseClicked(true);
    }
    public static void shoot(int mouseX, int mouseY, ObjectHandler oHandler, ImageHandler imageHandler, Camera camera, String abilityName) {
        int mouseX2 = (int)(mouseX + camera.getPlayerX() -(Game.WIDTH/2 - 30));
        int mouseY2 = (int)(mouseY + camera.getPlayerY() -(Game.HEIGHT/2 - 10));

        for (int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                if(abilityName.equals("Bullet")) {oHandler.addObject(new Bullet(tempObject.getX() + 16, tempObject.getY() + 24, ID.Bullet, oHandler, mouseX2, mouseY2));}
                if(abilityName.equals("CasinoChip")) oHandler.addObject(new CasinoChip(tempObject.getX() + 16, tempObject.getY() + 24, ID.CasinoChipAbility, oHandler, mouseX2, mouseY2));
                if(abilityName.equals("CardThrow")) oHandler.addObject(new CardThrow(tempObject.getX() + 16, tempObject.getY() + 24, ID.CardThrowAbility, oHandler, mouseX2, mouseY2));
            }
        }
    }
    public void mouseReleased(MouseEvent mouseEvent) {setMouseClicked(false);
    }
    public boolean setMouseClicked(boolean mouseClicked) {return this.mouseClicked = mouseClicked;}
    public boolean isMouseClicked() {return mouseClicked;}
}

