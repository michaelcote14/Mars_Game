package Abilities;

import Objects.FireExplosion;
import Utilities.Animation;
import Utilities.ID;
import Utilities.ImageHandler;
import Utilities.ObjectHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class DiceRoll extends AbilityObject {
    ObjectHandler oHandler;
    BufferedImage[] diceRollImages;
    BufferedImage[] diceRollNumImages;
    BufferedImage diceRollNumImage;
    Animation diceRollAnimation;
    int abilityDuration = (70 * 2); // 10 seconds
    Random random = new Random();
    private int randNum = random.nextInt(0, 6);
    private int rollTimer = 0;
    public static float cooldown = 0;
    public DiceRoll(float x, float y, ID id, ObjectHandler oHandler) {
        super(x, y, id);
        this.oHandler = oHandler;
        diceRollImages = ImageHandler.createImageArray("diceRoll", 6);
        diceRollNumImages = ImageHandler.createImageArray("diceRollNum", 6);
        diceRollNumImage = diceRollNumImages[randNum];
        diceRollAnimation = new Animation(50, diceRollImages);
        this.cooldown = 0;
    }
    @Override
    public void tick() {
        abilityDuration--;
        if(abilityDuration <= 0) {
            this.oHandler.removeObject(this);
            oHandler.addObject(new FireExplosion(x-17, y-15, 20 * randNum, 20 * randNum, ID.Explosion, oHandler));
        }
        rollTimer++;
    }
    @Override
    public void render(Graphics g) {
        if(rollTimer < 120) {
            diceRollAnimation.runAnimation();
            diceRollAnimation.drawAnimation(g, (int) x+15, (int) y, 20);
        }
        else {
            g.drawImage(diceRollNumImage, (int) x+3, (int) y+5, null);
        }
    }
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x-15, (int)y-30, 25, 25);
    }
}
