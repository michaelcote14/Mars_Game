package Utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class ImageHandler {
    public static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();

    public ImageHandler()
    {
        images.put("alienBullet", imageLoader("/Enemies/Martians/Arachnid_Mage/alienBullet.png"));

        images.put("alien1", imageLoader("/Enemies/Martians/Arachnid_Mage/alien1.png"));
        images.put("alien2", imageLoader("/Enemies/Martians/Arachnid_Mage/alien2.png"));
        images.put("alien3", imageLoader("/Enemies/Martians/Arachnid_Mage/alien3.png"));
        images.put("alien4", imageLoader("/Enemies/Martians/Arachnid_Mage/alien4.png"));
        images.put("alien5", imageLoader("/Enemies/Martians/Arachnid_Mage/alien5.png"));
        images.put("alien6", imageLoader("/Enemies/Martians/Arachnid_Mage/alien6.png"));
        images.put("alien7", imageLoader("/Enemies/Martians/Arachnid_Mage/alien7.png"));
        images.put("alien8", imageLoader("/Enemies/Martians/Arachnid_Mage/alien8.png"));

        images.put("wormAttack1", imageLoader("/Enemies/Martians/Worm/surface1.png"));
        images.put("wormAttack2", imageLoader("/Enemies/Martians/Worm/surface2.png"));
        images.put("wormAttack3", imageLoader("/Enemies/Martians/Worm/surface3.png"));
        images.put("wormAttack4", imageLoader("/Enemies/Martians/Worm/attacking1.png"));
        images.put("wormAttack5", imageLoader("/Enemies/Martians/Worm/attacking2.png"));
        images.put("wormAttack6", imageLoader("/Enemies/Martians/Worm/attacking3.png"));
        images.put("wormDive1", imageLoader("/Enemies/Martians/Worm/dive1.png"));
        images.put("wormDive2", imageLoader("/Enemies/Martians/Worm/dive2.png"));
        images.put("wormDive3", imageLoader("/Enemies/Martians/Worm/dive3.png"));


        images.put("healthPack", imageLoader("/Objects/healthPack.png"));

        images.put("gamblerDown1", imageLoader("/Classes/Gambler/down1.png"));

        images.put("jailWall", imageLoader("/Map/jailWall.png"));
        images.put("jailFloor", imageLoader("/Map/jailFloor.png"));

        images.put("flyingEyeDown1", imageLoader("/Enemies/Martians/Flying_Eye/down1.png"));
        images.put("flyingEyeDown2", imageLoader("/Enemies/Martians/Flying_Eye/down2.png"));
        images.put("flyingEyeDown3", imageLoader("/Enemies/Martians/Flying_Eye/down3.png"));
        images.put("flyingEyeDown4", imageLoader("/Enemies/Martians/Flying_Eye/down4.png"));
        images.put("flyingEyeDown5", imageLoader("/Enemies/Martians/Flying_Eye/down5.png"));
        images.put("flyingEyeDown6", imageLoader("/Enemies/Martians/Flying_Eye/down6.png"));

        images.put("rouletteWheel", imageLoader("/Classes/Gambler/rouletteWheel.png"));
        images.put("casinoChip", imageLoader("/Classes/Gambler/casinoChip.png"));
        images.put("cardThrow", imageLoader("/Classes/Gambler/cardThrow.png"));


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
}
