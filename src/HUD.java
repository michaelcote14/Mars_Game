import java.awt.*;

public class HUD {
    public static int health = 100;
    public static int ammo = 1000;
    private int greenHealthColorValue = 255;

    public static int score = 0;
    private int level = 1;
    public boolean isNewLevel = true;

    public void tick() {
        health = Game.clamp(health, 0, 100);
        Game.clamp(greenHealthColorValue, 0, 255);
        greenHealthColorValue = health*2;

        // Make the level increase every 10 points
        if(score > 5) {
            level = 2;
        }

//        // Make death happen when health is 0
//        if(health <= 0) {
//            g.setColor(Color.white);
//            g.drawString("Game Over", 100, 100);
//        }
    }
    public void render(Graphics g) {
        // Health Bar Background
        g.setColor(Color.gray);
        g.fillRect(15, 15, 200, 10);
        // Health Bar
        g.setColor(new Color(90, greenHealthColorValue, 20));
        g.fillRect(15, 15, health*2, 10);
        // Outline
        g.setColor(Color.white);
        g.drawRect(15, 15, 200, 10);

        // Score
        g.drawString("Score: " + score, 15, 40);
        g.drawString("Level : " + level, 15, 55);
        // ammo bar
        g.setColor(Color.white);
        g.drawString("Ammo: " + ammo, 5, 70);

        if(health <= 0) {
            g.setColor(Color.red);
            g.drawString("Game Over", Game.WIDTH/2, Game.HEIGHT/2);
        }
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return this.level;
    }
}
