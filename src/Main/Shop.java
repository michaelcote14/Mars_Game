package Main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Utilities.ObjectHandler;

public class Shop extends MouseAdapter {
    ObjectHandler oHandler;
    HUD hud;
    Game game;

    private int B1 = 1;
    private int B2 = 1;
    private int B3 = 1;

    public Shop(ObjectHandler oHandler, HUD hud, Game game) {
        this.oHandler = oHandler;
        this.hud = hud;
        this.game = game;
    }

    public void mousePressed(MouseEvent mouseEvent) {
        if(game.gameState != Game.STATE.Shop) return;
        int mouseX = mouseEvent.getX();
        int mouseY = mouseEvent.getY();

        // Box 1
        if(mouseX >= 100 && mouseX <= 200) {
            if(mouseY >= 100 && mouseY <= 180) {
                // You've selected Box 1
                if(hud.getScore() >= B1) {
                    hud.setScore(hud.getScore() - B1);
                    B1 += 1; // makes the next upgrade cost more
                    HUD.maxHealth += 10;
                    HUD.currentHealth += 10;
                }
            }
        }
        // Box 2
        if(mouseX >= 250 && mouseX <= 350) {
            if(mouseY >= 100 && mouseY <= 180) {
                // You've selected Box 2
                if(hud.getScore() >= B2) {
                    hud.setScore(hud.getScore() - B2);
                    B2 += 1; // makes the next upgrade cost more
                    oHandler.speed++;
                }
            }
        }
        // Box 3
        if(mouseX >= 400 && mouseX <= 500) {
            if(mouseY >= 100 && mouseY <= 180) {
                // You've selected Box 3
                if(hud.getScore() >= B3) {
                    hud.setScore(hud.getScore() - B3);
                    B3 += 1; // makes the next upgrade cost more
                    HUD.currentHealth = HUD.maxHealth;
                }
            }
        }
    }
    public void render(Graphics g) {
        if(game.gameState != Game.STATE.Shop) return;
        g.setColor(Color.white);
        g.setFont(new Font("arial", 0, 48));
        g.drawString("SHOP", Game.WIDTH/2 - 100, 50);

        // Box 1
        g.setFont(new Font("arial", 0, 12));
        g.drawString("Upgrade Health", 110, 120);
        g.drawString("Cost: " + B1, 110, 140);
        g.drawRect(100, 100, 100, 80);


        // Box 2
        g.drawString("Upgrade Speed", 260, 120);
        g.drawString("Cost: " + B2, 260, 140);
        g.drawRect(250, 100, 100, 80);


        // Box 3
        g.drawString("Refill Health", 410, 120);
        g.drawString("Cost: " + B3, 410, 140);
        g.drawRect(400, 100, 100, 80);

        // My Money
        g.drawString("Money: " + hud.getScore(), Game.WIDTH/2 - 50, 300);
        // Go back
        g.drawString("Press SPACE to go back", Game.WIDTH/2 - 50, 330);
    }

}