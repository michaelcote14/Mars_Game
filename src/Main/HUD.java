package Main;

import Abilities.CasinoChip;
import Abilities.RouletteWheel;
import Utilities.ImageHandler;
import Utilities.KeyHandler;

import java.awt.*;
import java.util.Random;

public class HUD {
    public static int scoreTracker = 0;
    public static int level = 1;
    private final KeyHandler keyHandler;
    public boolean isNewLevel = true;
    private Random rand = new Random();
    private ImageHandler imageHandler;

    public HUD(ImageHandler imageHandler, KeyHandler keyHandler) {
        this.imageHandler = imageHandler;
        this.keyHandler = keyHandler;
    }

    public void tick() {
        // Make the level increase every 10 points
        if (scoreTracker >= 10) {
            scoreTracker = 0;
            level++;
            isNewLevel = true;
        }
        //             todo death animation here
//            todo Fade screen to black
    }
    public void render(Graphics g) {
        // Health Bar Background
        g.setColor(Color.gray);
        g.fillRect(15, 15, (int) Player.maxHealth, 10);
        // Health Bar
        if (Player.currentHealth > Player.maxHealth / 2)
            g.setColor(new Color(90, 255, 20));
        else if (Player.currentHealth > Player.maxHealth / 4)
            g.setColor(new Color(255, 255, 20));
        else
            g.setColor(new Color(255, 20, 20));
        g.fillRect(15, 15, (int) Player.currentHealth, 10);
        // Outline
        g.setColor(Color.white);
        g.drawRect(15, 15, (int) Player.maxHealth, 10);

        // todo make the ability bar show the ability's cooldown
        // Spell Bar
        g.fillRect(Game.WIDTH / 2 - 60, 30, 120, 40);

        if(Player.ability1Cooldown <= 0) {
            g.setColor(new Color(255, 215, 140));
        }
        else
            g.setColor(Color.darkGray);
        // Show a red background if the key of the ability is activated
        if(this.keyHandler.isOnePressed()) {
            g.setColor(new Color(255, 20, 20));
        }
        g.fillRect(Game.WIDTH/2 - 60, 30, 40, 40);

        if(Player.ability2Cooldown <= 0) {
            g.setColor(new Color(255, 215, 140));
        }
        else
            g.setColor(Color.darkGray);
        if(this.keyHandler.isTwoPressed()) {
            g.setColor(new Color(255, 20, 20));
        }
        g.fillRect(Game.WIDTH/2 - 20, 30, 40, 40);

        if(Player.ability3Cooldown <= 0) {
            g.setColor(new Color(255, 215, 140));
        }
        else
            g.setColor(Color.darkGray);
        if(this.keyHandler.isThreePressed()) {
            g.setColor(new Color(255, 20, 20));
        }

        g.fillRect(Game.WIDTH/2 + 20, 30, 40, 40);

        g.drawImage(ImageHandler.images.get(Player.ability1Name.replaceFirst(Player.ability1Name.substring(0,1), Player.ability1Name.toLowerCase().substring(0,1))),
                Game.WIDTH/2 - 55, 35, 30, 30, null);
        g.drawImage(ImageHandler.images.get(Player.ability2Name.replaceFirst(Player.ability2Name.substring(0,1), Player.ability2Name.toLowerCase().substring(0,1))),
                Game.WIDTH/2 - 15, 35, 30, 30, null);
        g.drawImage(ImageHandler.images.get(Player.ability3Name.replaceFirst(Player.ability3Name.substring(0,1), Player.ability3Name.toLowerCase().substring(0,1))),
                Game.WIDTH/2 + 25, 35, 30, 30, null);

        g.setColor(Color.white);
        g.drawRect(Game.WIDTH/2 - 60, 30, 80, 40);
        g.drawLine(Game.WIDTH/2 - 20, 30, Game.WIDTH/2 - 20, 70);
        g.drawLine(Game.WIDTH/2 + 20, 30, Game.WIDTH/2 + 20, 70);
        g.setFont(new Font("arial", 1, 12));
        g.drawString("1", Game.WIDTH/2 - 58, 68);
        g.drawString("2", Game.WIDTH/2 - 18, 68);
        g.drawString("3", Game.WIDTH/2 + 22, 68);

        // Score
        g.setFont(new Font("arial", 1, 15));
        g.drawString("Money: " + Player.money, 15, 55);
        g.drawString("Level : " + this.level, 15, 70);
        g.drawString("Space for Shop", 15, 85);
    }
    public void setLevel(int level) {this.level = level;}
    public int getLevel() {return this.level;}
}
