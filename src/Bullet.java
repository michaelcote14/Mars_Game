import java.awt.*;

public class Bullet extends GameObject {
    private ObjectHandler oHandler;
    private double direction;
    private int speed = 10;

    public Bullet(int x, int y, ID id, ObjectHandler oHandler, int mouseX, int mouseY, ImageSheet imageSheet) {
        super(x, y, id, imageSheet);
        this.oHandler = oHandler;

        int deltaX = mouseX - x;
        int deltaY = mouseY - y;
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
    public void calculateVelocity(int fromX, int fromY, int toX, int toY)
    {
        double distance = Math.sqrt(Math.pow((toX - fromX), 2) + Math.pow((toY - fromY), 2));
        double speed = 10; //set the speed in [2,n)  n should be < 20 for normal speed
        //find Y
        velY = (float)((toY - fromY) * speed / distance);
        //find X
        velX = (float)((toX - fromX) * speed / distance);
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(x, y, 8, 8);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 8, 8);
    }
}