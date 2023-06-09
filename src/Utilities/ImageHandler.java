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
        images.put("jailWall", imageLoader("/Maps/jailWall.png"));
        images.put("jailFloor", imageLoader("/Maps/jailFloor.png"));

        // Player
        images.put("gamblerDown1", imageLoader("/Classes/Gambler/down1.png"));
        images.put("rouletteWheel", imageLoader("/Classes/Gambler/rouletteWheel.png"));
        images.put("casinoChip", imageLoader("/Classes/Gambler/casinoChip.png"));
        images.put("cardThrow", imageLoader("/Classes/Gambler/cardThrow.png"));
        images.put("diceRoll", imageLoader("/Classes/Gambler/diceRoll/diceRoll1.png"));
        multipleImageLoader("diceRoll", "/Classes/Gambler/diceRoll/diceRoll", 6);
        multipleImageLoader("diceRollNum", "/Classes/Gambler/diceRoll/diceRollNum",6);

        // Enemies
        images.put("arachnidMageBullet", imageLoader("/Enemies/Martians/Arachnid_Mage/arachnidMageBullet.png"));
        multipleImageLoader("arachnidMage", "/Enemies/Martians/Arachnid_Mage/arachnidMage", 8);
        multipleImageLoader("flyingEye", "/Enemies/Martians/Flying_Eye/flyingEye", 6);
        multipleImageLoader("enemySpawnerPortal", "/Enemies/Martians/EnemySpawnerPortals/newPortal/portal", 24);

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
        images.put("healthPack", imageLoader("/Objects/healthPack.png"));
        images.put("explosiveBarrel", imageLoader("/Objects/explosiveBarrel.png"));
        multipleImageLoader("fireExplosion", "/Miscellaneous/fireExplosion/fireExplosion", 18);
        multipleImageLoader("chest", "/Objects/Chest/chest", 5);

        // Other
        multipleImageLoader("levelUp", "/Miscellaneous/levelUp/level_up", 8);

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
