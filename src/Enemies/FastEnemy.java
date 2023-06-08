package Enemies;

import java.awt.*;

import Utilities.*;
import Main.Game;

public class FastEnemy extends GameObject {
    private ObjectHandler oHandler;

    public FastEnemy(float x, float y, ID id, ObjectHandler oHandler) {
        super(x, y, id);

        this.oHandler = oHandler;

        velX = 2;
        velY = 9;

    }
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 16, 16); // 16, 16 is the size of the enemy
    }
    @Override
    public void tick() {
        x += velX;
        y += velY;

        if(x <= 0 || x >= Game.WIDTH  - 32) velX *= -1;
        if(y <= 0 || y >= Game.HEIGHT - 16) velY *= -1;

        oHandler.addObject(new Trail(x, y, ID.Trail, Color.cyan, 16, 16, 0.02f, oHandler));

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect((int)x, (int)y, 16, 16);
    }
}
