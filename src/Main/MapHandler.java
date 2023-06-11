package Main;

import Enemies.SpiderBoss;
import Enemies.EnemySpawnerPortal;
import Enemies.Worm;
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
    private BufferedImage mapImage = null;
    private int currentMapNum = 0;

    public MapHandler(ObjectHandler oHandler, Game game, Camera camera, KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.oHandler = oHandler;
        this.game = game;
        this.camera = camera;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;

        loadMap(0);

    }
    public void tick(GameObject object) {
        playerX = object.getX();
        playerY = object.getY();

        if(playerX > 1890 && playerX < 1920 && playerY > 605 && playerY < 635 && !wasMapLoaded) {
            player = loadMap(1);
            wasMapLoaded = true;
            object.setX((int)player.getX()-3);
            object.setY((int) player.getY()-3);
        }
    }
    public Player loadMap(int mapNumber) {
        currentMapNum = mapNumber;

        mapImage = ImageHandler.images.get("map" + mapNumber);
        wasMapLoaded = true;
        oHandler.clearObjects();
        int w = mapImage.getWidth();
        int h = mapImage.getHeight();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int pixel = mapImage.getRGB(x, y);
                int red = (pixel >> 16) & 0xff; // shifting the bits over to get the red value
                int green = (pixel >> 8) & 0xff; // shifting the bits over to get the green value
                int blue = (pixel) & 0xff; // shifting the bits over to get the blue value

                if (red == 255 && green == 255 && blue == 255) {
                    oHandler.addObject(new Wall(x * 32, y * 32, ID.BlockObject));
                }
                else if (red == 00 && green == 00 && blue == 255) {
                    // AutoSave
                    oHandler.removeObject(player);
                    SaveOrLoad.load("Save1");
                    SaveOrLoad.save("Save1");
                    player = new Player(x * 32, y * 32, ID.Player, oHandler, game, camera, keyHandler, mouseHandler);
                    player.setX(x * 32);
                    player.setY(y * 32);
                    oHandler.addObject(player);
                }
                else if (red == 255 && green == 216 && blue == 0) {
                    randNum = new Random().nextInt(0,2);
                    if(randNum == 0) {
                        oHandler.addObject(new HealthPack(x * 32, y * 32, ID.HealthPack, oHandler));
                    }
                    else {
                        oHandler.addObject(new Chest(x * 32, y * 32, ID.ChestObject, oHandler));
                    }
                }
                else if(red == 255 && green == 0 && blue == 220) {
                    if(randNum == 0) {
                        oHandler.addObject(new BuzzSaw(x * 32, y * 32, ID.BuzzSawTrap, oHandler));
                    }
                    else if(randNum == 1) {
                        oHandler.addObject(new ExplosiveBarrel(x * 32, y * 32, ID.ExplosiveBarrelObject, oHandler));
                    }
                }
                else if(red == 0 && green == 148 && blue == 255) {
                    if(mapNumber == 0) {
                        Computer computer = new Computer(x * 32 - 120, y * 32 + 40, ID.ComputerObject, oHandler, keyHandler, this);
                        oHandler.addObject(computer);
                        oHandler.addObject(new Teleporter(x * 32, y * 32, ID.Teleporter, computer, oHandler, this));
                    } else {oHandler.addObject(new Teleporter(x * 32, y * 32, ID.Teleporter, null, oHandler, this));}

                }
            }
        }
        // Enemies
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int pixel = mapImage.getRGB(x, y);
                int red = (pixel >> 16) & 0xff; // shifting the bits over to get the red value
                int green = (pixel >> 8) & 0xff; // shifting the bits over to get the green value
                int blue = (pixel) & 0xff; // shifting the bits over to get the blue value

                if (red == 255 && green == 0 && blue == 0) {
                    int randNum = new Random().nextInt(0, 4);
                    if (randNum == 0) {
                        oHandler.addObject(new Worm(x * 32, y * 32, ID.WormEnemy, oHandler));
                    } else if (randNum == 1) {
                        oHandler.addObject(new EnemySpawnerPortal(x * 32, y * 32, ID.FlyingEyeEnemy, oHandler));
                    } else if (randNum == 2) {
                        oHandler.addObject(new Worm(x * 32, y * 32, ID.WormEnemy, oHandler));
                    } else {
                        oHandler.addObject(new SpiderBoss(x * 32, y * 32, ID.ArachnidMageEnemy, oHandler));
                    }
                }
                else if (red == 255 && green == 106 && blue == 0) {
                    oHandler.addObject(new SpiderBoss(x * 32, y * 32, ID.SpiderBoss, oHandler));
                }
            }
        }
        return player;
    }
    public int getMapNumber() {
        return currentMapNum;
    }
}
