package Utilities;

import Utilities.GameObject;
import Utilities.ID;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {

    ObjectHandler oHandler;

    public KeyHandler(ObjectHandler oHandler){
        this.oHandler = oHandler;
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
