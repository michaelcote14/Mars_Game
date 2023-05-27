import java.awt.*;

public abstract class GameObject {
    protected int x, y;
    protected float velX, velY;
    protected ID id;
    protected ImageSheet imageSheet;

    public GameObject(int x, int y, ID id, ImageSheet imageSheet) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.imageSheet = imageSheet;
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
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setId(ID id) {this.id = id;}
    public ID getId() {return id;}

}