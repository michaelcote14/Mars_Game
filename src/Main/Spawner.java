package Main;

import Enemies.BasicEnemy;
import Utilities.Camera;
import Utilities.ID;
import Utilities.ImageSheet;
import Utilities.ObjectHandler;

import java.awt.image.BufferedImage;

public class Spawner {
    private ObjectHandler oHandler;
    private HUD hud;
    private Camera camera;
    private ImageSheet iSheet;
    private BufferedImage levelImage;
    Player player;

    public Spawner(ObjectHandler oHandler, HUD hud, Camera camera, ImageSheet iSheet, BufferedImage levelImage) {
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
                    int green = (pixel >> 8) & 0xff; // shifting the bits over to get the green value
                    int blue = (pixel) & 0xff; // shifting the bits over to get the blue value

                    if(green == 255 && blue == 0) {
                        oHandler.addObject(new BasicEnemy(xx*32, yy*32, ID.BasicEnemy, oHandler, iSheet));
//                        oHandler.addObject(new Enemies.FastEnemy(xx*32, yy*32, Utilities.ID.BasicEnemy, oHandler, iSheet));
//                        oHandler.addObject(new Enemies.TrackerEnemy(xx*32, yy*32, Utilities.ID.TrackerEnemy, oHandler, iSheet));

                    }
                    else if(green == 255 && blue == 255) {
                        oHandler.addObject(new Objects.Crate(xx * 32, yy * 32, Utilities.ID.Crate, iSheet));
                    }
                }
            }
            hud.isNewLevel = false;
        }
    }
}

