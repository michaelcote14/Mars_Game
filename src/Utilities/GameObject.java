package Utilities;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public abstract class GameObject {
    protected float x, y;
    protected float velX, velY;
    protected ID id;
    protected ImageHandler imageHandler;

    public GameObject(float x, float y, ID id, ImageHandler imageHandler) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.imageHandler = imageHandler;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y= y;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public void setId(ID id) {this.id = id;}
    public ID getId() {return id;}
}
