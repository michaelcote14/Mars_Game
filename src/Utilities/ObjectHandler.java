package Utilities;

import java.awt.*;
import java.util.ArrayList;

public class ObjectHandler {
    public ArrayList<GameObject> object = new ArrayList<GameObject>();

    private boolean upPressed, downPressed, leftPressed, rightPressed = false;
    private boolean mouseClicked = false;

    public void tick() {
        for(int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.tick();
        }
    }
    public void render(Graphics g) {
        for(int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.render(g);
        }
    }
    public void clearObjects() {
        for(int i = 0; i < this.object.size(); i++){
            GameObject tempObject = this.object.get(i);

            if(tempObject.getId() == ID.Player) {
                this.removeObject(tempObject);
                i--;
            }
            if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy
                    || tempObject.getId() == ID.TrackerEnemy) {
                this.removeObject(tempObject);
                i--;
            }
        }
    }
    public void addObject(GameObject object) {
        this.object.add(object);
    }
    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }
    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }
    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }
    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }
    public boolean isUpPressed() {
        return upPressed;
    }
    public boolean isDownPressed() {
        return downPressed;
    }
    public boolean isLeftPressed() {
        return leftPressed;
    }
    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean setMouseClicked(boolean mouseClicked) {return this.mouseClicked = mouseClicked;}
    public boolean isMouseClicked() {return mouseClicked;}

}
