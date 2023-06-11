package Utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class ImageHandler {
    public static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();

    public ImageHandler()
    {
        // Maps
        images.put("map0", imageLoader("/Maps/Levels/map0.png"));
        images.put("map1", imageLoader("/Maps/Levels/map1.png"));

        // Structures
        images.put("jailWall", imageLoader("/Maps/Structures/jailWall.png"));
        images.put("jailFloor", imageLoader("/Maps/Structures/jailFloor.png"));
        images.put("teleporter", imageLoader("/Maps/Structures/teleporter.png", 100, 100));
        images.put("computer", imageLoader("/Objects/computer.png", 32, 32));
        images.put("droppedItemsConverter", imageLoader("/Objects/droppedItemsConverter.png", 120, 150));

        // Player
        images.put("gamblerDown1", imageLoader("/Classes/Gambler/down1.png", 32, 42));
        images.put("rouletteWheel", imageLoader("/Classes/Gambler/rouletteWheel.png", 100, 100));
        images.put("casinoChip", imageLoader("/Classes/Gambler/casinoChip.png", 40, 40));
        images.put("cardThrow", imageLoader("/Classes/Gambler/cardThrow.png", 40, 40));
        images.put("diceRoll", imageLoader("/Classes/Gambler/diceRoll/diceRoll1.png", 30, 30));
        multipleImageLoader("diceRoll", "/Classes/Gambler/diceRoll/diceRoll", 6, 40, 40);
        multipleImageLoader("diceRollNum", "/Classes/Gambler/diceRoll/diceRollNum",6);

        // Enemies
        multipleImageLoader("spiderBossDown", "/Enemies/Martians/SpiderBoss/down", 3, 200, 200);
        multipleImageLoader("spiderBossLeft", "/Enemies/Martians/SpiderBoss/left", 3, 200, 200);
        multipleImageLoader("spiderBossUp", "/Enemies/Martians/SpiderBoss/up", 3, 200, 200);
        images.put("spiderBossProjectile", imageLoader("/Enemies/Martians/SpiderBoss/projectile.png"));
        images.put("spiderBossCannon", imageLoader("/Enemies/Martians/SpiderBoss/cannon.png"));

        images.put("arachnidMageBullet", imageLoader("/Enemies/Martians/Arachnid_Mage/arachnidMageBullet.png", 16, 16));
        multipleImageLoader("arachnidMage", "/Enemies/Martians/Arachnid_Mage/arachnidMage", 8, 50, 50);
        multipleImageLoader("flyingEye", "/Enemies/Martians/Flying_Eye/flyingEye", 6, 32, 32);
        multipleImageLoader("enemySpawnerPortal", "/Enemies/Martians/EnemySpawnerPortals/newPortal/portal", 24, 90, 120);

        images.put("wormAttack1", imageLoader("/Enemies/Martians/Worm/surface1.png"));
        images.put("wormAttack2", imageLoader("/Enemies/Martians/Worm/surface2.png"));
        images.put("wormAttack3", imageLoader("/Enemies/Martians/Worm/surface3.png"));
        images.put("wormAttack4", imageLoader("/Enemies/Martians/Worm/attacking1.png"));
        images.put("wormAttack5", imageLoader("/Enemies/Martians/Worm/attacking2.png"));
        images.put("wormAttack6", imageLoader("/Enemies/Martians/Worm/attacking3.png"));
        images.put("wormDive1", imageLoader("/Enemies/Martians/Worm/dive1.png"));
        images.put("wormDive2", imageLoader("/Enemies/Martians/Worm/dive2.png"));
        images.put("wormDive3", imageLoader("/Enemies/Martians/Worm/dive3.png"));

        // Objects
        images.put("healthPack", imageLoader("/Objects/healthPack.png", 24, 24));
        images.put("explosiveBarrel", imageLoader("/Objects/explosiveBarrel.png", 48, 48));
        images.put("buzzSaw", imageLoader("/Objects/buzzSaw.png", 50, 50));
        multipleImageLoader("fireExplosion", "/Miscellaneous/fireExplosion/fireExplosion", 18, 210, 210);
        multipleImageLoader("chest", "/Objects/Chest/chest", 5, 44, 32);

        // Drops
        images.put("spiderBossLeg", imageLoader("/Enemies/Martians/SpiderBoss/leg.png"));

        // Other
        multipleImageLoader("levelUp", "/Miscellaneous/levelUp/level_up", 8, 150, 150);

    }
    public BufferedImage imageLoader(String imagePath, int width, int height) {
        BufferedImage scaledImage = null;
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            // Scale the image
            scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            scaledImage.getGraphics().drawImage(image, 0, 0, width, height, null);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading the image: " + imagePath);
        }
        return scaledImage;
    }
    public BufferedImage imageLoader(String imagePath) {
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
    private void multipleImageLoader(String imageName, String imagePath, int numberOfImages, int width, int height) {
        for (int i = 1; i <= numberOfImages; i++) {
            images.put(imageName + i, imageLoader(imagePath + i + ".png", width, height));
        }
    }
    private void multipleImageLoader(String imageName, String imagePath, int numberOfImages) {
        for (int i = 1; i <= numberOfImages; i++) {
            images.put(imageName + i, imageLoader(imagePath + i + ".png"));
        }
    }
    public static BufferedImage[] createImageArray(String imageName, int numberOfImages) {
        BufferedImage[] imageArray = new BufferedImage[numberOfImages];
        for (int i = 0; i < numberOfImages; i++) {
            imageArray[i] = images.get(imageName + (i + 1));
        }
        return imageArray;
    }
}
