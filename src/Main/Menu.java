package Main;

import Utilities.ObjectHandler;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Menu extends MouseAdapter {
    private Game game;
    private ObjectHandler oHandler;
    private HUD hud;
    private Random rand = new Random();

    private BufferedImage levelImage = null;
    String deathComment = "";

    public Menu(Game game, ObjectHandler oHandler, HUD hud) {
        this.game = game;
        this.oHandler = oHandler;
        this.hud = hud;

    }
    public void mousePressed(MouseEvent mEvent) {
        int mouseX = mEvent.getX();
        int mouseY = mEvent.getY();

        if(game.gameState == Game.STATE.Menu) {
            // Play Button
            if (isMouseOver(mouseX, mouseY, 210, 150, 200, 64)) {
                game.gameState = Game.STATE.Game;
                this.deathComment = getDeathComment();
            }
            // Help Button
            if (isMouseOver(mouseX, mouseY, 210, 250, 200, 64)) {
                game.gameState = Game.STATE.Help;
            }
            // Quit Button
            if (isMouseOver(mouseX, mouseY, 210, 350, 200, 64)) {
                System.exit(1);
            }
        }
        // Back button for help
        if (game.gameState == Game.STATE.Help && isMouseOver(mouseX, mouseY, 210, 350, 200, 64)) {
            game.gameState = Game.STATE.Menu;
        }

        // Try Again button for death
        if (game.gameState == Game.STATE.Death && isMouseOver(mouseX, mouseY, 210, 350, 200, 64)) {
            game.gameState = Game.STATE.Game;
            oHandler.clearObjects();
            game.wasMapLoaded = false;
            hud.isNewLevel = true;
            hud.setWave(1);
            deathComment = getDeathComment();
        }
    }
    public void mouseReleased(MouseEvent mEvent) {}

    private boolean isMouseOver(int mouseX, int mouseY, int x, int y, int width, int height) {
        if(mouseX > x && mouseY < x + width) {
            if(mouseY > y && mouseY < y + height) {return true;}
            else {return false;}
        }
        else {return false;}
    }
    public void tick(){
    }
    public void render(Graphics g) {
        if (game.gameState == Game.STATE.Menu) {
            Font fnt = new Font("arial", 1, 50);
            Font fnt2 = new Font("arial", 1, 30);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Menu", 420, 70);

            g.setFont(fnt2);
            g.drawRect(390, 150, 200, 64);
            g.drawString("Play", 450, 190);

            g.drawRect(390, 250, 200, 64);
            g.drawString("Help", 450, 290);

            g.drawRect(390, 350, 200, 64);
            g.drawString("Quit", 450, 390);
        }
        else if (game.gameState == Game.STATE.Help) {
            Font fnt = new Font("arial", 1, 50);
            Font fnt2 = new Font("arial", 1, 30);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Help", 40, 70);

            g.setFont(fnt2);
            g.drawString("Figure it out by your damn self.", 80, 200);

            g.setFont(fnt2);
            g.drawRect(390, 350, 200, 64);
            g.drawString("Back", 450, 390);
        }
        else if (game.gameState == Game.STATE.Death) {
            Font font = new Font("arial", 1, 20);
            g.setColor(new Color(0, 0, 0, 250));
            g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

            g.setColor(Color.white);
            g.setFont(font);
            g.drawString(this.deathComment, 20, 200);
            g.setColor(Color.red);

            g.setFont(new Font("arial", 1, 18));
            g.drawString("Tip: Dying will decrease your stats each time.", 20, 260);
            g.drawString("So, I would stop dying. Idiot.", 20, 285);

            g.setColor(Color.white);
            Font font2 = new Font("arial", 1, 30);
            g.setFont(font2);
            g.drawRect(Game.WIDTH/2 - 80, Game.HEIGHT/2 + 85, 200,50);
            g.drawString("Play Again", Game.WIDTH/2 - 50, Game.HEIGHT/2 + 125);
        }
    }
    public String getDeathComment() {

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
                "At this rate, this game might take you a whole year to beat",
                "I bet you're the type of person to get a participation trophy",
                "You're obviously going to need a friend to help level you.",
                "Don't leave a bad review just because you suck.",
                "If you leave a bad review, we WILL find you.. and we WILL hurt you."};
        return deathComments[rand.nextInt(0, deathComments.length - 1)];
    }

}