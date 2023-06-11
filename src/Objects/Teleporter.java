package Objects;

import Main.MapHandler;
import Utilities.GameObject;
import Utilities.ID;
import Utilities.ImageHandler;
import Utilities.ObjectHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Teleporter extends GameObject {
    private Computer computer;
    private ObjectHandler oHandler;
    private MapHandler mapHandler;
    private BufferedImage teleporterImage;
    int rotation = 15;
    int rotationCounter = 0;
    int imageX;
    int imageY;
    public Teleporter(float x, float y, ID id, Computer computer, ObjectHandler oHandler, MapHandler mapHandler) {
        super(x, y, id);
        this.computer = computer;
        this.oHandler = oHandler;
        this.mapHandler = mapHandler;
        teleporterImage = ImageHandler.images.get("teleporter");
    }

    @Override
    public void tick() {
        for (int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);

            if (tempObject.getId() == ID.Player) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    if(mapHandler.getMapNumber() == 0) {
                        mapHandler.loadMap(this.computer.mapSelected);
                    } else {mapHandler.loadMap(0);}
                }
            }
        }

    }
    @Override
    public void render(Graphics g) {
        // Rotate the image continuously
        Graphics2D g2d = (Graphics2D) g.create();
        int centerX = (int) x + 32;
        int centerY = (int) y + 32;
        g2d.translate(centerX, centerY);
        g2d.rotate(Math.toRadians(rotation));
        imageX = -this.teleporterImage.getWidth() / 2; // must be half the width of the image
        imageY = -this.teleporterImage.getHeight() / 2 ; // must be half the height of the image

        g2d.drawImage(this.teleporterImage, imageX, imageY, null);

        g2d.dispose();
        if(rotationCounter > 50) {
            rotation += 10;
            rotationCounter = 0;
        }
        rotationCounter++;
    }
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 30, 20);
    }
}
