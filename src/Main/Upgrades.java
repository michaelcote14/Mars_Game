package Main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Abilities.Abilities;
import Utilities.ImageHandler;
import Utilities.ObjectHandler;

import javax.swing.*;

public class Upgrades extends MouseAdapter {
    private ObjectHandler oHandler;
    private HUD hud;
    private Game game;
    private Player player;
    private MapHandler mapHandler;

    private String abilityName = "";
    private int abilityRowClicked = 0;
    private boolean isAbilityRowClicked = false;
    private boolean isKeyBindChangerClicked = false;

    int spacer = 20;

    public Upgrades(ObjectHandler oHandler, HUD hud, Game game, MapHandler mapHandler) {
        this.oHandler = oHandler;
        this.mapHandler = mapHandler;
        this.hud = hud;
        this.game = game;
    }
    public void mousePressed(MouseEvent mouseEvent) {
        if(game.gameState != Game.STATE.Shop) return;
        int mouseX = mouseEvent.getX();
        int mouseY = mouseEvent.getY();

        if(mouseX >= Game.WIDTH - (spacer*2)-10 && mouseX <= Game.WIDTH - (spacer*2)+10) {
            for(int row = 1; row <= Abilities.characteristicsList.size(); row++) {
                int rowSpace = row * 2 - 1;
                if (mouseY >= Game.HEIGHT / 12 + (spacer * rowSpace) - 15 && mouseY <= Game.HEIGHT / 12 + (spacer * rowSpace) + 5) {
                    if (Player.characteristicPoints >= 1 && !Abilities.immediateBlockedCharacteristics.contains(Abilities.characteristicsList.get(row - 1))
                        && !Abilities.blockedCharacteristics.contains(Abilities.characteristicsList.get(row - 1))) {
                        Player.characteristicPoints -= 1;
                        int currentPoints = Abilities.characteristicPointsBook.get(Abilities.characteristicsList.get(row - 1));
                        Abilities.characteristicPointsBook.put(Abilities.characteristicsList.get(row - 1), currentPoints + 1);
                        Abilities.immediateBlockedCharacteristics.add(Abilities.characteristicsList.get(row - 1));
                    }
                }
            }
            SaveOrLoad.save("Save1");
        }
        else if(mouseX >= 5 && mouseX <= 284) {
            isAbilityRowClicked = true;
            abilityRowClicked = abilityRowClickedFinder(mouseY);
        }

        if(isAbilityRowClicked == true) {
            if(mouseX >= 665 && mouseX <= 685 && mouseY >= 170 && mouseY <= 190) {
                if(Player.abilityPoints > 0) {
                    Player.abilityPoints--;
                    abilityName = Abilities.nameList.get(abilityRowClicked-1);
                    int currentLevel = Abilities.levelBook.get(abilityName);
                    Abilities.levelBook.replace(abilityName, currentLevel + 1);
                    SaveOrLoad.save("Save1");
                }
            }
            else if(mouseX >= 565 && mouseX <= 565+125 && mouseY >= 145 && mouseY <= 145+20 && mapHandler.getMapNumber() == 0) {
                isKeyBindChangerClicked = true;
            }
            if(isKeyBindChangerClicked) {
                String keyEntered = JOptionPane.showInputDialog("Enter a key to bind to this ability");
                int num = Integer.parseInt(keyEntered);
                abilityName = Abilities.nameList.get(abilityRowClicked-1);
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
                isKeyBindChangerClicked = false;
                SaveOrLoad.save("Save1");
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

        for(int i = 1; i < Abilities.characteristicsList.size()+1; i++) {
            String characteristicName = Abilities.characteristicsList.get(i-1);
            if(Abilities.blockedCharacteristics.contains(characteristicName) || Abilities.immediateBlockedCharacteristics.contains(characteristicName)) {
                g.setColor(Color.gray);
            }
            else {
                g.setColor(Color.white);
            }
            int j = i * 2 - 1;
            g.drawString(characteristicName, Game.WIDTH - (spacer * 14) - 5, Game.HEIGHT / 12 + (spacer * j));
            g.drawString(String.valueOf(Abilities.characteristicPointsBook.get(characteristicName)), Game.WIDTH - (spacer * 6), Game.HEIGHT / 12 + (spacer * j));
            g.drawRect(Game.WIDTH - (spacer * 2) - 10, Game.HEIGHT / 12 + (spacer * j) - 15, spacer, spacer);
            g.drawLine(Game.WIDTH - (spacer * 2) - 5, Game.HEIGHT / 12 + (spacer * j) - 5, Game.WIDTH - (spacer * 2) + 5, Game.HEIGHT / 12 + (spacer * j) - 5);
            g.drawLine(Game.WIDTH - (spacer * 2), Game.HEIGHT / 12 + (spacer * j) - 10, Game.WIDTH - (spacer * 2), Game.HEIGHT / 12 + (spacer * j));
        }

        g.setColor(Color.white);
        for(int i = 0; i < Abilities.nameList.size(); i++) {
            abilityName = Abilities.nameList.get(i);
            g.drawString(abilityName, 15, 65 + (i * 45));
            g.drawString("Level: " + Abilities.levelBook.get(abilityName), 172, 65 + (i * 45));
            g.drawImage(ImageHandler.images.get(abilityName.replaceFirst(abilityName.substring(0, 1), abilityName.toLowerCase().substring(0, 1))),
                    240, 40 + (i * 45), 45, 45, null);
            g.drawRect(240, 40 + (i * 45), 44, 45);
            g.drawLine(5, 85 + (i * 45), 284, 85 + (i * 45));
        }

        g.drawString("Ability Points: " + Player.abilityPoints, 85, 510);
        g.drawString("Characteristic Points: " + Player.characteristicPoints, Game.WIDTH - (spacer*11)-5, 510);
        g.drawString("Press SPACE to go back", Game.WIDTH/2 - 90, 450);

        // ability description box
        // todo make an "are you sure you want to buy this" section
        if(isAbilityRowClicked && abilityRowClicked <= Abilities.nameList.size()) {
            g.setColor(Color.black);
            g.fillRect(290, 10, 405, 190);
            g.setColor(Color.white);
            g.drawString("Description", 447, 35);
            g.drawLine(290, 40, 695, 40);

            g.drawString("Upgrade", 597, 189);
            g.drawRect(665, 172, 20, 20);
            g.drawLine(670, 182, 680, 182);
            g.drawLine(675, 175, 675, 187);

            abilityName = Abilities.nameList.get(abilityRowClicked - 1);
            g.drawString("Damage: " + Abilities.damageBook.get(abilityName), 300, 160);
            g.drawString("Duration: " + Abilities.durationBook.get(abilityName), 300, 190);
            g.drawString("Cooldown: " + Abilities.cooldownBook.get(abilityName), 430, 190);

            if(mapHandler.getMapNumber() == 0) {
                g.drawString("Change KeyBind", 568, 162);
                g.drawRect(565, 145, 125, 20);
            }
        }
    }
    private int abilityRowClickedFinder(int mouseY) {
        int i = 40;
        for(int j = 0; j < 10; j += 1) {
            if (mouseY >= i + (j * (i + 5)) && mouseY <= 85 + (j * (i + 5))) {
                return j + 1;
            }
        }
        return 20;
    }
}
