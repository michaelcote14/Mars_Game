package Utilities;

import Main.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {
    ObjectHandler oHandler;
    Game game;

    private boolean WPressed, SPressed, APressed, DPressed, onePressed, twoPressed,
            threePressed, enterPressed, upPressed, downPressed, leftPressed, rightPressed = false;

    public KeyHandler(ObjectHandler oHandler, Game game) {
        this.oHandler = oHandler;
        this.game = game;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode(); // this returns a key code for the key that was pressed

        for(int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);

            if(tempObject.getId() == ID.Player) {
                if(key == KeyEvent.VK_W) setUpPressed(true);
                if(key == KeyEvent.VK_S) setDownPressed(true);
                if(key == KeyEvent.VK_D) setRightPressed(true);
                if(key == KeyEvent.VK_A) setLeftPressed(true);
                if(key == KeyEvent.VK_ENTER) setEnterPressed(true);

                if(key == KeyEvent.VK_ESCAPE) System.exit(1);

                if(key == KeyEvent.VK_1) {
                    setOnePressed(true);
                    setTwoPressed(false);
                    setThreePressed(false);
                }
                if(key == KeyEvent.VK_2) {
                    if(isTwoPressed() == true) {
                        setTwoPressed(false);
                    } else {
                        setTwoPressed(true);
                        setOnePressed(false);
                        setThreePressed(false);
                    }
                }
                if(key == KeyEvent.VK_3) {
                    if(isThreePressed() == true) {
                        setThreePressed(false);
                    } else {
                        setThreePressed(true);
                        setOnePressed(false);
                        setTwoPressed(false);
                    }
                }

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
                if(key == KeyEvent.VK_W) setUpPressed(false);
                if(key == KeyEvent.VK_S) setDownPressed(false);
                if(key == KeyEvent.VK_D) setRightPressed(false);
                if(key == KeyEvent.VK_A) setLeftPressed(false);
                if(key == KeyEvent.VK_1) setOnePressed(false);
                if(key == KeyEvent.VK_ENTER) setEnterPressed(false);
            }
        }
    }

    public void setOnePressed(boolean onePressed) {this.onePressed = onePressed;}
    public void setTwoPressed(boolean twoPressed) {this.twoPressed = twoPressed;}
    public void setThreePressed(boolean threePressed) {this.threePressed = threePressed;}
    public void setEnterPressed(boolean enterPressed) {this.enterPressed = enterPressed;}
    public void setUpPressed(boolean upPressed) {this.upPressed = upPressed;}
    public void setDownPressed(boolean downPressed) {this.downPressed = downPressed;}
    public void setLeftPressed(boolean leftPressed) {this.leftPressed = leftPressed;}
    public void setRightPressed(boolean rightPressed) {this.rightPressed = rightPressed;}
    public boolean isOnePressed() {return onePressed;}
    public boolean isTwoPressed() {return twoPressed;}
    public boolean isThreePressed() {return threePressed;}
    public boolean isEnterPressed() {return enterPressed;}
    public boolean isUpPressed() {return upPressed;}
    public boolean isDownPressed() {return downPressed;}
    public boolean isLeftPressed() {return leftPressed;}
    public boolean isRightPressed() {return rightPressed;}
}
