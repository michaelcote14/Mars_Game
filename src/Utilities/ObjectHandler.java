package Utilities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ObjectHandler {
    public ArrayList<GameObject> object = new ArrayList<GameObject>();

    public BufferedImage bulletExplosionImage = imageGrabber("/Objects/bulletExplosion.png", 1, 1);

    public void tick() {
        for(int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.tick();
        }
    }
    public void render(Graphics g) {
        for(int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.render(g);
        }
    }
    public void clearObjects() {this.object.clear();}
    public void addObject(GameObject object) {
        this.object.add(object);
    }
    public void removeObject(GameObject object) {
        this.object.remove(object);
    }
    public BufferedImage imageGrabber(String imagePath, int width, int height) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading the image: " + imagePath);
        }
        return image;
    }
}
