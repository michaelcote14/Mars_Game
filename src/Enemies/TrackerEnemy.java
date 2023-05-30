package Enemies;

import Main.Game;
import Utilities.GameObject;
import Utilities.ID;
import Utilities.ImageSheet;
import Utilities.ObjectHandler;
import Utilities.Trail;
import java.awt.*;

public class TrackerEnemy extends GameObject {
    private ObjectHandler oHandler;
    private GameObject player;

    public TrackerEnemy(float x, float y, ID id, ObjectHandler oHandler, ImageSheet imageSheet) {
        super(x, y, id, null);

        this.oHandler = oHandler;

        for(int i = 0; i < oHandler.object.size(); i++) {
            if(oHandler.object.get(i).getId() == ID.Player) player = oHandler.object.get(i);
        }
    }
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 16, 16); // 16, 16 is the size of the enemy
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        // the 8 below is because we want to track the center of the object, you will have to adjust this depending on the object
        float diffX = x - player.getX() - 16;
        float diffY = y - player.getY() - 16;
        float distance = (float)Math.sqrt((x- player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()));

        velX = (float)((-1.0/distance) * diffX);
        velY = (float)((-1.0/distance) * diffY);

        if(x <= 0 || x >= Game.WIDTH - 32) velX *= -1;
        if(y <= 0 || y >= Game.HEIGHT - 16) velY *= -1;

        oHandler.addObject(new Trail(x, y, ID.Trail, Color.green, 16, 16, 0.03f, oHandler));
    }
    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillRect((int)x, (int)y, 11, 16);
    }
}
