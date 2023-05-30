package Main;

import Enemies.BasicEnemy;
import Utilities.BufferedImageLoader;
import Utilities.ID;
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
                System.out.println("play button");
                game.gameState = Game.STATE.Game;


            }
            // Help Button
            if (isMouseOver(mouseX, mouseY, 210, 250, 200, 64)) {
                System.out.println("help button");
                game.gameState = Game.STATE.Help;
            }
            // Quit Button
            if (isMouseOver(mouseX, mouseY, 210, 350, 200, 64)) {
                System.exit(1);
            }
        }
        // Back button for help
        if (game.gameState == Game.STATE.Help && isMouseOver(mouseX, mouseY, 210, 350, 200, 64)) {
            System.out.println("Back button");
            game.gameState = Game.STATE.Menu;
        }

        // Try Again button for end
        if (game.gameState == Game.STATE.Death && isMouseOver(mouseX, mouseY, 210, 350, 200, 64)) {
            game.gameState = Game.STATE.Game;
            BufferedImageLoader imageLoader = new BufferedImageLoader();
            levelImage = imageLoader.loadImage("/wizard_level.png");
            game.loadLevel(levelImage);
//            hud.setLevel(1);
//            hud.setScore(0);
//            oHandler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.Player, oHandler));
//            oHandler.clearEnemies();
//            oHandler.addObject(new BasicEnemy(rand.nextInt(Game.WIDTH - 50), rand.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, oHandler));
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
    public void render(Graphics g) {
        if (game.gameState == Game.STATE.Menu) {
            Font fnt = new Font("arial", 1, 50);
            Font fnt2 = new Font("arial", 1, 30);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Menu", 240, 70);

            g.setFont(fnt2);
            g.drawRect(210, 150, 200, 64);
            g.drawString("Play", 270, 190);

            g.drawRect(210, 250, 200, 64);
            g.drawString("Help", 270, 290);

            g.drawRect(210, 350, 200, 64);
            g.drawString("Quit", 270, 390);
        } else if (game.gameState == Game.STATE.Help) {
            Font fnt = new Font("arial", 1, 50);
            Font fnt2 = new Font("arial", 1, 30);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Help", 240, 70);

            g.setFont(fnt2);
            g.drawString("I don't fucking know how to help you.", 20, 200);

            g.setFont(fnt2);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Back", 270, 390);
        }
        else if (game.gameState == Game.STATE.Death) {
            Font fnt = new Font("arial", 1, 50);
            Font fnt2 = new Font("arial", 1, 30);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Game Over", 180, 70);

            g.setFont(fnt2);
            g.drawString("You lost with a score of: " + hud.getScore(), 175, 200);

            g.setFont(fnt2);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Try Again", 245, 390);
        }
    }
    public void tick(){

    }
}