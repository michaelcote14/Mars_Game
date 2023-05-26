import java.awt.*;
import java.awt.image.BufferedImage;

public class Crate extends GameObject {
    private BufferedImage crateImage;


    public Crate(int x, int y, ID id, ImageSheet imageSheet) {
        super(x, y, id, imageSheet);

        crateImage = imageSheet.grabImage(6, 2, 32, 32);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(crateImage, x, y, null);

    }

    @Override
    public Rectangle getBounds() {return new Rectangle(x, y, 32, 32);}
}
