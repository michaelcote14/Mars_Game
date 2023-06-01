package Objects;

import Utilities.GameObject;
import Utilities.ID;
import Utilities.ImageSheet;
import Utilities.ObjectHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Crate extends GameObject {
    private BufferedImage crateImage;


    public Crate(float x, float y, ID id, ObjectHandler oHandler, ImageSheet imageSheet) {
        super(x, y, id, imageSheet);

        crateImage = imageSheet.grabImage(6, 2, 32, 32);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(crateImage, (int)x, (int)y, null);

    }

    @Override
    public Rectangle getBounds() {return new Rectangle((int)x, (int)y, 32, 32);}
}
