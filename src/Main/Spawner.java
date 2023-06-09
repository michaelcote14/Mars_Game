package Main;

import Enemies.EnemySpawnerPortal;
import Enemies.Worm;
import Enemies.arachnidMage;
import Objects.HealthPack;
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
    public static boolean isNewLevel = false;

    public Spawner(ObjectHandler oHandler, HUD hud, Camera camera) {
        this.oHandler = oHandler;
        this.hud = hud;
        this.camera = camera;
        this.iSheet = iSheet;
        this.levelImage = ImageHandler.images.get("map1");
    }
    public void tick() {
        if(isNewLevel == true) {
            // AutoSave
            SaveOrLoad.save("Save1", player);

            int imageWidth = levelImage.getWidth();
            int imageHeight = levelImage.getHeight();

            for(int x = 0; x < imageWidth; x++) {
                for(int y = 0; y < imageHeight; y++) {
                    int pixel = levelImage.getRGB(x, y);
                    int red = (pixel >> 16) & 0xff; // shifting the bits over to get the red value
                    int green = (pixel >> 8) & 0xff; // shifting the bits over to get the green value
                    int blue = (pixel) & 0xff; // shifting the bits over to get the blue value

                    if(red == 255 && green == 0 && blue == 0) {
                        int randNum = new Random().nextInt(0, 4);
                        if(randNum == 0) {oHandler.addObject(new Worm(x * 32, y * 32, ID.WormEnemy, oHandler));}
                        else if(randNum == 1) {oHandler.addObject(new EnemySpawnerPortal(x*32, y*32, ID.FlyingEyeEnemy, oHandler));}
                        else if(randNum == 2) {oHandler.addObject(new Worm(x*32, y*32, ID.WormEnemy, oHandler));}
                        else {oHandler.addObject(new arachnidMage(x * 32, y * 32, ID.ArachnidMageEnemy, oHandler));}
                    }

//                    else if(green == 255 && blue == 255) {
//                        oHandler.addObject(new HealthPack(x * 32, y * 32, ID.Crate, oHandler, iSheet));
//                    }
                }
            }
            isNewLevel = false;
        }
    }
}

