package Objects;

import Utilities.GameObject;
import Utilities.ID;
import Utilities.ImageHandler;
import Utilities.ObjectHandler;

import java.awt.*;

public class Bullet extends GameObject {
    private ObjectHandler oHandler;
    private double direction;
    private int speed = 10;

    public Bullet(float x, float y, ID id, ObjectHandler oHandler, int mouseX, int mouseY, ImageHandler imageHandler) {
        super(x, y, id, imageHandler);
        this.oHandler = oHandler;

        float deltaX = mouseX - x;
        float deltaY = mouseY - y;
        direction = Math.atan2(deltaY, deltaX); // equivalent to Math.atan(deltaY/deltaX) but with an extra div.by.0 check
    }
    public void tick() {
        x = (int)(x + (speed * Math.cos(direction)));
        y = (int)(y + (speed * Math.sin(direction)));

        for(int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);

            if(tempObject.getId() == ID.Block) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    oHandler.removeObject(this);
                }
            }
        }

    }
    public void render(Graphics g) {
        g.setColor(Color.blue);
        g.fillOval((int)x, (int)y, 8, 8);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 8, 8);
    }
}
