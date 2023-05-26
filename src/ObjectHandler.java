import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class ObjectHandler {
    ArrayList<GameObject> object = new ArrayList<GameObject>();

    private boolean upPressed, downPressed, leftPressed, rightPressed = false;

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


}
