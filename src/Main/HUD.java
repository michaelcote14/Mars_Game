package Main;

import Main.Game;

import java.awt.*;
import java.util.Random;

public class HUD {
    public static float health = 100;
    public static int ammo = 1000;
    private float greenHealthColorValue = 255;

    public static int score = 0;
    public static int scoreTracker = 0;
    private int level = 1;
    public boolean isNewLevel = true;
    Font font = new Font("arial", 1, 50);
    private Random rand = new Random();

    public void tick() {
        health = Game.clamp(health, 0, 100);
        Game.clamp(greenHealthColorValue, 0, 255);
        greenHealthColorValue = health*2;

        // Make the level increase every 10 points
        if(scoreTracker >= 5) {
            scoreTracker = 0;
            score++;
            level++;
            isNewLevel = true;
        }
    }
    public void render(Graphics g) {
        // Health Bar Background
        g.setColor(Color.gray);
        g.fillRect(15, 15, 200, 10);
        // Health Bar
        g.setColor(new Color(90, (int)greenHealthColorValue, 20));
        g.fillRect(15, 15, (int)health*2, 10);
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
            // todo death animation here
            // todo add a restart button
            //todo Fade screen to black
            // todo fix the alignment of the death comments
            Font font = new Font("arial", 1, 30);
            g.setColor(new Color(0, 0, 0, 250));
            g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

            String[] deathComments = new String[] {
                    "Wow, you suck.",
                    "Were you even trying?",
                    "Maybe you should retire.",
                    "Does your mom know you're a loser?",
                    "Jesus is watching, and I bet he's disappointed.",
                    "You're a disgrace to your family.",
                    "I would try a different game if I were you.",
                    "Oh you bought this game just so you could die over and over? That's cool",
                    "I hope you're good in school, because obviously video games aren't gonna take you anywhere",
                    "No amount of YouTube tutorials are going to help you get better at this game, I can already tell",
                    "At this rate, this game might take you a whole year to beat"};

            g.setColor(Color.white);
            g.setFont(font);
            g.drawString(deathComments[rand.nextInt(0, deathComments.length)], Game.WIDTH/9, Game.HEIGHT/2);
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
