import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject{
    private BufferedImage blockImage;

    public Block(int x, int y, ID id, ImageSheet imageSheet) {
        super(x, y, id, imageSheet);

        blockImage = imageSheet.grabImage(5, 2, 32, 32);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(blockImage, x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
