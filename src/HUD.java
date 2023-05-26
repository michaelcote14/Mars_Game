import java.awt.*;

public class HUD {
    public static int health = 100;
    public static int ammo = 100;
    private int greenHealthColorValue = 255;

    private int score = 0;
    private int level = 1;

    public void tick() {
        health = Game.clamp(health, 0, 100);
        Game.clamp(greenHealthColorValue, 0, 255);
        greenHealthColorValue = health*2;
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
        return level;
    }
}
