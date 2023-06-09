package Main;

import Objects.*;
import Utilities.*;

import java.awt.image.BufferedImage;
import java.util.Random;

public class MapHandler {
    private ObjectHandler oHandler;
    private Player player;
    private Game game;
    private Camera camera;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private int randNum;
    private float playerX, playerY;
    private boolean wasMapLoaded = false;

    public MapHandler(ObjectHandler oHandler, Game game, Camera camera, KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.oHandler = oHandler;
        this.game = game;
        this.camera = camera;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;

        loadMap(ImageHandler.images.get("map0"));

    }
    public void tick(GameObject object) {
        playerX = object.getX();
        playerY = object.getY();

        if(playerX > 2000 && !wasMapLoaded) {
            player = loadMap(ImageHandler.images.get("map1"));
            Spawner.isNewLevel = true;
            wasMapLoaded = true;
            object.setX((int)player.getX()-3);
            object.setY((int) player.getY()-3);
        }
    }
    public Player loadMap(BufferedImage mapImage) {
        int w = mapImage.getWidth();
        int h = mapImage.getHeight();

        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                int pixel = mapImage.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff; // shifting the bits over to get the red value
                int green = (pixel >> 8) & 0xff; // shifting the bits over to get the green value
                int blue = (pixel) & 0xff; // shifting the bits over to get the blue value

                if (red == 255 && green == 255 && blue == 255) {
                    oHandler.addObject(new Block(xx * 32, yy * 32, ID.BlockObject));
                }
                else if (red == 00 && green == 00 && blue == 255) {
                    SaveOrLoad.load("Save1");
                    oHandler.removeObject(player);
                    player = new Player(xx * 32, yy * 32, ID.Player, oHandler, game, camera, keyHandler, mouseHandler);
                    player.setX(xx * 32);
                    player.setY(yy * 32);
                    oHandler.addObject(player);
                }
                else if (red == 255 && green == 216 && blue == 0) {
                    randNum = new Random().nextInt(0,2);
                    if(randNum == 0) {
                        oHandler.addObject(new HealthPack(xx * 32, yy * 32, ID.HealthPack, oHandler));
                    }
                    else {
                        oHandler.addObject(new Chest(xx * 32, yy * 32, ID.ChestObject, oHandler));
                    }
                }
                else if(red == 255 && green == 0 && blue == 220) {
                    if(randNum == 0) {
                        oHandler.addObject(new BuzzSaw(xx * 32, yy * 32, ID.BuzzSawTrap, oHandler));
                    }
                    else if(randNum == 1) {
                        oHandler.addObject(new ExplosiveBarrel(xx * 32, yy * 32, ID.ExplosiveBarrelObject, oHandler));
                    }
                }
            }
        }
        return player;
    }
}
