package Main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Abilities.Abilities;
import Utilities.ImageHandler;
import Utilities.ObjectHandler;

import javax.swing.*;

public class Upgrades extends MouseAdapter {
    ObjectHandler oHandler;
    HUD hud;
    Game game;
    Player player;

    private int R1 = 1;
    private int R2 = 1;
    private int R3 = 1;
    private int R4 = 1;
    private int R5 = 1;
    private String abilityName = "";
    private int abilityRowClicked = 0;
    private boolean isAbilityRowClicked = false;
    private boolean isKeyBindChangerClicked = false;

    int spacer = 20;
    // todo put spaces between ability names that are displayed

    public Upgrades(ObjectHandler oHandler, HUD hud, Game game, Player player, ImageHandler imageHandler) {
        this.oHandler = oHandler;
        this.hud = hud;
        this.game = game;
    }
    public void mousePressed(MouseEvent mouseEvent) {
        if(game.gameState != Game.STATE.Shop) return;
        int mouseX = mouseEvent.getX();
        int mouseY = mouseEvent.getY();

        if(mouseX >= Game.WIDTH - (spacer*2)-10 && mouseX <= Game.WIDTH - (spacer*2)+10) {
            // todo create a hashmap for this too
            //todo put a y on this because low clicks will crash program
            characteristicPointAdder(mouseY, 1, R1);
            characteristicPointAdder(mouseY, 2, R2);
            characteristicPointAdder(mouseY, 3, R3);
            characteristicPointAdder(mouseY, 4, R4);

            // Save the upgrades to the save file
            SaveOrLoad.save("Save1", player);
        }
        else if(mouseX >= 5 && mouseX <= 284) {
            isAbilityRowClicked = true;
            abilityRowClicked = abilityRowClickedFinder(mouseY);
        }

        if(isAbilityRowClicked == true) {
            if(mouseX >= 665 && mouseX <= 685 && mouseY >= 170 && mouseY <= 190) {
                if(Player.unspentAbilityPoints > 0) {
                    Player.unspentAbilityPoints--;
                    abilityName = Abilities.nameList.get(abilityRowClicked-1);
                    int currentLevel = Abilities.levelBook.get(abilityName);
                    Abilities.levelBook.replace(abilityName, currentLevel + 1);
                    SaveOrLoad.save("Save1", player);
                    SaveOrLoad.load("Save1");
                }
            }
            else if(mouseX >= 565 && mouseX <= 565+125 && mouseY >= 145 && mouseY <= 145+20) {
                isKeyBindChangerClicked = true;
            }
            if(isKeyBindChangerClicked) {
                String keyEntered = JOptionPane.showInputDialog("Enter a key to bind to this ability");
                int num = Integer.parseInt(keyEntered);
                abilityName = Abilities.nameList.get(abilityRowClicked-1);
                String tempName = abilityName;
                if(num == 1) {
                    Abilities.nameList.set(3, Player.ability1Name);
                    Abilities.nameList.set((num - 1), abilityName);
                    Player.ability1Name = abilityName;
                    Player.ability1Cooldown = Abilities.cooldownBook.get(Player.ability1Name);
                }
                else if(num == 2) {
                    Abilities.nameList.set(3, Player.ability2Name);
                    Abilities.nameList.set((num - 1), abilityName);
                    Player.ability2Name = abilityName;
                    Player.ability2Cooldown = Abilities.cooldownBook.get(Player.ability2Name);
                }
                else if(num == 3) {
                    Abilities.nameList.set(3, Player.ability3Name);
                    Abilities.nameList.set((num - 1), abilityName);
                    Player.ability3Name = abilityName;
                    Player.ability3Cooldown = Abilities.cooldownBook.get(Player.ability3Name);
                }
                SaveOrLoad.save("Save1", player);
                SaveOrLoad.load("Save1");
                isKeyBindChangerClicked = false;
            }
        }
    }
    public void render(Graphics g) {
        // todo change up the fonts and make them look better
        //todo put white outline around all big black boxes
        if(game.gameState != Game.STATE.Shop) return;

        // Big Boxes
        g.setColor(Color.black);
        g.fillRect(Game.WIDTH/2 - 495, 10, 280, 510);
        g.fillRect(Game.WIDTH/2 + 200, 10, 280, 510);
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


        for(int i = 0; i < Abilities.nameList.size(); i++) {
            abilityName = Abilities.nameList.get(i);
            g.drawString(abilityName, 15, 65 + (i * 45));
            g.drawString("Level: " + Abilities.levelBook.get(abilityName), 172, 65 + (i * 45));
            g.drawImage(ImageHandler.images.get(abilityName.replaceFirst(abilityName.substring(0, 1), abilityName.toLowerCase().substring(0, 1))),
                    240, 40 + (i * 45), 45, 45, null);
            g.drawRect(240, 40 + (i * 45), 44, 45);
            g.drawLine(5, 85 + (i * 45), 284, 85 + (i * 45));
        }

        g.drawString("Ability Points: " + Player.unspentAbilityPoints, 85, 510);
        g.drawString("Money: " + Player.money, Game.WIDTH - (spacer*10)-5, 510);
        g.drawString("Press SPACE to go back", Game.WIDTH/2 - 90, 450);

        // ability description box
        // todo make an "are you sure you want to buy this" section
        if(isAbilityRowClicked) {
            g.setColor(Color.black);
            g.fillRect(290, 10, 405, 190);
            g.setColor(Color.white);
            g.drawString("Description", 447, 35);
            g.drawLine(290, 40, 695, 40);

            g.drawString("Upgrade", 597, 189);
            g.drawRect(665, 172, 20, 20);
            g.drawLine(670, 182, 680, 182);
            g.drawLine(675, 175, 675, 187);

            g.drawString("Change KeyBind", 568, 162);
            g.drawRect(565, 145, 125, 20);

            abilityName = Abilities.nameList.get(abilityRowClicked-1);
            g.drawString("Damage: " + Abilities.damageBook.get(abilityName), 300, 160);
            g.drawString("Duration: " + Abilities.durationBook.get(abilityName), 300, 190);
            g.drawString("Cooldown: " + Abilities.cooldownBook.get(abilityName), 430, 190);
        }
    }
    private int abilityRowClickedFinder(int mouseY) {
        int i = 40;
        for(int j = 0; j < 8; j += 1) {
            if (mouseY >= i + (j * (i + 5)) && mouseY <= 85 + (j * (i + 5))) {
                return j + 1;
            }
        }
        return 0;
    }
    private void characteristicsPainter(String characteristicName, int characteristicPoint, int rowNumber, Graphics g) {
        rowNumber = rowNumber * 2 - 1;
        g.drawString(characteristicName, Game.WIDTH - (spacer*14)-5, Game.HEIGHT/12 + (spacer*rowNumber));
        g.drawString(String.valueOf(characteristicPoint), Game.WIDTH - (spacer*6), Game.HEIGHT/12 + (spacer*rowNumber));
        g.drawRect(Game.WIDTH - (spacer*2)-10, Game.HEIGHT/12 + (spacer*rowNumber)-15, spacer, spacer);
        g.drawLine(Game.WIDTH - (spacer*2)-5, Game.HEIGHT/12 + (spacer*rowNumber)-5, Game.WIDTH - (spacer*2)+5, Game.HEIGHT/12 + (spacer*rowNumber)-5);
        g.drawLine(Game.WIDTH - (spacer*2), Game.HEIGHT/12 + (spacer*rowNumber)-10, Game.WIDTH - (spacer*2), Game.HEIGHT/12 +(spacer*rowNumber));
    }
    private void characteristicPointAdder(int mouseY, int rowNumber, int upgradeNumber) {
        int rowSpace = rowNumber * 2 - 1;
        if(mouseY >= Game.HEIGHT/12 + (spacer*rowSpace)-15 && mouseY <= Game.HEIGHT/12 + (spacer*rowSpace) + 5) {
            if(Player.money >= upgradeNumber) {
                Player.money -= upgradeNumber;
                if(rowNumber == 1) {
                    player.maxHealth += 10;
                    player.currentHealth += 10;
                }
                else if(rowNumber == 2) {
                    Player.walkSpeed++;
                }
                else if(rowNumber == 3) {
                    Player.fireRate++;
                }
                else if(rowNumber == 4) {
                    Player.lifeStealRate++;
                }
            }
        }
    }
}
