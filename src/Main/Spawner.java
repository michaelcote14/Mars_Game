package Main;

import Enemies.Worm;
import Utilities.Camera;
import Utilities.ID;
import Utilities.ImageHandler;
import Utilities.ObjectHandler;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Spawner {
    private ObjectHandler oHandler;
    private HUD hud;
    private Camera camera;
    private ImageHandler iSheet;
    private BufferedImage levelImage;
    Player player;
    Random randNum = new Random();

    public Spawner(ObjectHandler oHandler, HUD hud, Camera camera, ImageHandler iSheet, BufferedImage levelImage) {
        this.oHandler = oHandler;
        this.hud = hud;
        this.camera = camera;
        this.iSheet = iSheet;
        this.levelImage = levelImage;
    }
    public void tick() {
        if(hud.isNewLevel == true) {
            // AutoSave
            SaveOrLoad.save("Save1", player);

            int w = levelImage.getWidth();
            int h = levelImage.getHeight();

            for(int xx = 0; xx < w; xx++) {
                for(int yy = 0; yy < h; yy++) {
                    int pixel = levelImage.getRGB(xx, yy);
                    int red = (pixel >> 16) & 0xff; // shifting the bits over to get the red value
                    int green = (pixel >> 8) & 0xff; // shifting the bits over to get the green value
                    int blue = (pixel) & 0xff; // shifting the bits over to get the blue value

                    if(red == 00 && green == 255 && blue == 0) {
                        oHandler.addObject(new Worm(xx*32, yy*32, ID.WormEnemy, oHandler, iSheet));

//                        if((hud.getLevel() + 2) % 3 == 0) {
//                            if(randNum.nextInt(2) == 0) {oHandler.addObject(new FlyingEye(xx*32, yy*32, ID.BasicEnemy, oHandler, iSheet));}
//                            else {oHandler.addObject(new worm(xx*32, yy*32, ID.TrackerEnemy, oHandler, iSheet));}
//                        }
//                        else if(hud.getLevel() % 2 == 0) {
//                            oHandler.addObject(new arachnidMage(xx * 32, yy * 32, ID.ShooterEnemy, oHandler, iSheet));
//                        }
//                        oHandler.addObject(new Enemies.FastEnemy(xx*32, yy*32, Utilities.ID.BasicEnemy, oHandler, iSheet));

                    }
                    else if(red == 255 && green == 0 && blue == 255) {
                        oHandler.addObject(new Worm(xx*32, yy*32, ID.WormEnemy, oHandler, iSheet));
                    }

//                    else if(green == 255 && blue == 255) {
//                        oHandler.addObject(new HealthPack(xx * 32, yy * 32, ID.Crate, oHandler, iSheet));
//                    }
                }
            }
            hud.isNewLevel = false;
        }
    }
}

