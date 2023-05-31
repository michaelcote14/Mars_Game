package Utilities;

import java.awt.*;
import java.util.ArrayList;

public class ObjectHandler {
    public ArrayList<GameObject> object = new ArrayList<GameObject>();

    private boolean upPressed, downPressed, leftPressed, rightPressed = false;
    private boolean mouseClicked = false;
    public int speed = 5;

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
    public void clearEnemies() {
        for(int i = 0; i < this.object.size(); i++){
            System.out.println(this.object.size());
            GameObject tempObject = this.object.get(i);

            if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy
                    || tempObject.getId() == ID.TrackerEnemy) {
                System.out.println("temp object: " + tempObject.getId() + " removed");
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
