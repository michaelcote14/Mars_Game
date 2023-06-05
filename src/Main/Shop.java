package Main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Utilities.ObjectHandler;

public class Shop extends MouseAdapter {
    ObjectHandler oHandler;
    HUD hud;
    Game game;
    Player player;

    private int B1, B2, B3, B4, B5 = 1;
    int boxWidth = 150;
    int boxHeight = 50;
    int boxSpace = 50;

    int spacer = 20;

    public Shop(ObjectHandler oHandler, HUD hud, Game game, Player player) {
        this.oHandler = oHandler;
        this.hud = hud;
        this.game = game;
        this.player = player;
    }
    public void mousePressed(MouseEvent mouseEvent) {
        if(game.gameState != Game.STATE.Shop) return;
        int mouseX = mouseEvent.getX();
        int mouseY = mouseEvent.getY();

        // Row 1 Box 1
        if(mouseX >= boxSpace && mouseX <= boxSpace + boxWidth) {
            if(mouseY >= boxWidth - 50 && mouseY <= boxWidth - 50 + boxHeight) {
                // You've selected Box 1
                if(Player.money >= B1) {
                    Player.money -= B1;
                    B1 += 1; // makes the next upgrade cost more
                    player.maxHealth += 10;
                    player.currentHealth += 10;
                }
            }
        }
        // Row 1 Box 2
        if(mouseX >= (boxSpace * 2) + boxWidth && mouseX <= (boxSpace * 2) + (boxWidth * 2)) {
            if(mouseY >= boxWidth - 50 && mouseY <= boxWidth - 50 + boxHeight) {
                // You've selected Box 2
                if(Player.money >= B2) {
                    Player.money = (Player.money - B2);
                    B2 += 1; // makes the next upgrade cost more
                    Player.walkSpeed++;
                }
            }
        }
        // Row 1 Box 3
        if(mouseX >= (boxSpace * 3) + (boxWidth * 2) && mouseX <= (boxSpace * 3) + (boxWidth * 3)) {
            if(mouseY >= boxWidth - 50 && mouseY <= boxWidth - 50 + boxHeight) {
                // You've selected Box 3
                if(Player.money >= B3) {
                    Player.money = (Player.money - B3);
                    B3 += 1; // makes the next upgrade cost more
                    player.currentHealth = player.maxHealth;
                }
            }
        }
        // Row 1 Box 4
        if(mouseX >= (boxSpace * 4) + (boxWidth * 3) && mouseX <= (boxSpace * 4) + (boxWidth * 4)) {
            if(mouseY >= boxWidth - 50 && mouseY <= boxWidth - 50 + boxHeight) {
                // You've selected Box 4
                if(Player.money >= B4) {
                    Player.money = (Player.money - B4);
                    B4 += 1; // makes the next upgrade cost more
                    Player.fireRate++;
                }
            }
        }
        // Row 2 Box 1
        if(mouseX >= boxSpace && mouseX <= boxSpace + boxWidth) {
            if(mouseY >= boxWidth - 50 + boxHeight + 30 && mouseY <= boxWidth - 50 + (boxHeight*2) + boxSpace) {
                // You've selected Box 4
                if(Player.money >= B5) {
                    Player.money = (Player.money - B5);
                    B5 += 1; // makes the next upgrade cost more
                }
            }
        }
        // Row 2 Box 2
        if(mouseX >= boxSpace && mouseX <= boxSpace + boxWidth) {
            if(mouseY >= boxWidth - 50 + boxHeight + 30 && mouseY <= boxWidth - 50 + (boxHeight*2) + boxSpace) {
                // You've selected Box 4
                if(Player.money >= B4) {
                    Player.money = (Player.money - B4);
                    B5 += 1; // makes the next upgrade cost more
                    Player.fireRate++;
                }
            }
        }
    }
    // todo save the cost of new upgrades to the save file
    // todo put spell xp bar on the left side in abilities
    public void render(Graphics g) {
        if(game.gameState != Game.STATE.Shop) return;

        // Big Boxes
        g.setColor(Color.black);
        g.fillRect(Game.WIDTH/2 - 495, 10, 280, 470);
        g.fillRect(Game.WIDTH/2 + 200, 10, 280, 470);
        g.setFont(new Font("arial", 0, 20));
        g.setColor(Color.white);
        g.drawString("Abilities", Game.WIDTH/2 - 385, 35);
        g.drawString("Characteristics", Game.WIDTH/2 + 280, 35);
        g.drawLine(Game.WIDTH/2 - 495, 40, Game.WIDTH/2 - 216, 40);
        g.drawLine(Game.WIDTH/2 + 200, 40, Game.WIDTH/2 + 479, 40);

        g.setFont(new Font("arial", 0, 16));
        g.setColor(Color.white);

        characteristicsPainter("Health", (int) Player.maxHealth, 1, g);
        characteristicsPainter("Walk Speed", Player.walkSpeed, 2, g);
        characteristicsPainter("Fire Rate", Player.fireRate, 3, g);
        characteristicsPainter("Life Steal", (int)Player.lifeStealRate, 4, g);

        // My Money
        g.drawString("Money: " + Player.money, Game.WIDTH/2 - 50, 300);
        // Go back
        g.drawString("Press SPACE to go back", Game.WIDTH/2 - 50, 330);
    }
    private void characteristicsPainter(String characteristicName, int characteristicPoint, int rowNumber, Graphics g) {
        rowNumber = rowNumber * 2 - 1;
        g.drawString(characteristicName, Game.WIDTH - (spacer*14)-5, Game.HEIGHT/12 + (spacer*rowNumber));
        g.drawString(String.valueOf(characteristicPoint), Game.WIDTH - (spacer*6), Game.HEIGHT/12 + (spacer*rowNumber));
        g.drawRect(Game.WIDTH - (spacer*2)-10, Game.HEIGHT/12 + (spacer*rowNumber)-15, spacer, spacer);
        g.drawLine(Game.WIDTH - (spacer*2)-5, Game.HEIGHT/12 + (spacer*rowNumber)-5, Game.WIDTH - (spacer*2)+5, Game.HEIGHT/12 + (spacer*rowNumber)-5);
        g.drawLine(Game.WIDTH - (spacer*2), Game.HEIGHT/12 + (spacer*rowNumber)-10, Game.WIDTH - (spacer*2), Game.HEIGHT/12 +(spacer*rowNumber));
    }

}
