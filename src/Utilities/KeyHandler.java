package Utilities;

import Main.Game;
import Utilities.GameObject;
import Utilities.ID;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {
    ObjectHandler oHandler;
    Game game;

    public KeyHandler(ObjectHandler oHandler, Game game) {
        this.oHandler = oHandler;
        this.game = game;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode(); // this returns a key code for the key that was pressed

        for(int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);

            if(tempObject.getId() == ID.Player) {
                // Key events for player 1
                if(key == KeyEvent.VK_W) oHandler.setUpPressed(true);
                if(key == KeyEvent.VK_S) oHandler.setDownPressed(true);
                if(key == KeyEvent.VK_D) oHandler.setRightPressed(true);
                if(key == KeyEvent.VK_A) oHandler.setLeftPressed(true);

                if(key == KeyEvent.VK_ESCAPE) System.exit(1);
                if(key == KeyEvent.VK_P) {
                    if (Game.gameState == Game.STATE.Game) {
                        if (Game.paused) Game.paused = false;
                        else Game.paused = true;
                    }
                }
                if(key == KeyEvent.VK_SPACE) {
                    if(Game.gameState == Game.STATE.Game) {Game.gameState = Game.STATE.Shop;}
                    else if(Game.gameState == Game.STATE.Shop) {Game.gameState = Game.STATE.Game;}
                }
            }
        }
    }
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode(); // this returns a key code for the key that was pressed

        for(int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);

            if(tempObject.getId() == ID.Player) {
                // Key events for player 1
                if(key == KeyEvent.VK_W) oHandler.setUpPressed(false);
                if(key == KeyEvent.VK_S) oHandler.setDownPressed(false);
                if(key == KeyEvent.VK_D) oHandler.setRightPressed(false);
                if(key == KeyEvent.VK_A) oHandler.setLeftPressed(false);
            }
        }
    }
}
