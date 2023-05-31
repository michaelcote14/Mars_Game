package Main;

import Main.Game;

import java.awt.*;
import java.util.Random;

public class HUD {
    public static float currentHealth = 100;
    public static float maxHealth = 100;
    public static int ammo = 1000;

    public static int score = 0;
    public static int scoreTracker = 0;
    private int level = 1;
    public boolean isNewLevel = true;
    Font font = new Font("arial", 1, 50);
    private Random rand = new Random();

    public void tick() {
//        health = Game.clamp(health, 0, 100 + (healthBounds/2));
//        greenHealthColorValue = health * 2;
//        Game.clamp(greenHealthColorValue, 0, 255);

        // Make the level increase every 10 points
        if (scoreTracker >= 5) {
            scoreTracker = 0;
            score++;
            level++;
            isNewLevel = true;
        }

        if (currentHealth <= 0) {
//             todo death animation here
//             todo add a restart button
//            todo Fade screen to black
//             todo fix the alignment of the death comments
            Game.gameState = Game.STATE.Death;
        }
    }
    public void render(Graphics g) {
        // Health Bar Background
        g.setColor(Color.gray);
        g.fillRect(15, 15, (int)maxHealth, 10);
        // Health Bar
        g.setColor(new Color(90, 255, 20));
        g.fillRect(15, 15, (int)currentHealth, 10);
        // Outline
        g.setColor(Color.white);
        g.drawRect(15, 15, (int)maxHealth, 10);

        // Score
        g.setFont(new Font("arial", 1, 15));
        g.drawString("Score: " + score, 15, 40);
        g.drawString("Level : " + level, 15, 55);
        // ammo bar
        g.setColor(Color.white);
        g.drawString("Ammo: " + ammo, 5, 70);

        // Score
        g.drawString("Score: " + score, 15, 90);
        g.drawString("Level : " + level, 15, 110);
        g.drawString("Space for Shop", 15, 130);
    }

    public void setScore(int score) {this.score = score;}
    public int getScore() {return score;}
    public void setLevel(int level) {this.level = level;}
    public int getLevel() {return this.level;}
}
