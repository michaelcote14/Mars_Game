package Main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Utilities.ImageHandler;
import Utilities.ObjectHandler;

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
    private int abilityRowClicked = 0;
    private boolean isAbilityRowClicked = false;

    int spacer = 20;

    public Upgrades(ObjectHandler oHandler, HUD hud, Game game, Player player, ImageHandler imageHandler) {
        this.oHandler = oHandler;
        this.hud = hud;
        this.game = game;
        this.player = player;
    }
    public void mousePressed(MouseEvent mouseEvent) {
        if(game.gameState != Game.STATE.Shop) return;
        int mouseX = mouseEvent.getX();
        int mouseY = mouseEvent.getY();

        if(mouseX >= Game.WIDTH - (spacer*2)-10 && mouseX <= Game.WIDTH - (spacer*2)+10) {
            pointAdder(mouseY, 1, R1);
            pointAdder(mouseY, 2, R2);
            pointAdder(mouseY, 3, R3);
            pointAdder(mouseY, 4, R4);

            // Save the upgrades to the save file
            SaveOrLoad.save("Save1", player);
        }
        else if(mouseX >= 5 && mouseX <= 284) {
            isAbilityRowClicked = true;
            abilityRowClicked = abilityRowClickedFinder(mouseY);
        }

        if(isAbilityRowClicked == true) {
            if(mouseX >= 665 && mouseX <= 685) {
                if(mouseY >= 170 && mouseY <= 190) {
                    if(Player.unspentAbilityPoints > 0) {
                        Player.unspentAbilityPoints--;
                        if(abilityRowClicked == 1) {
                            Player.ability1Level++;
                        }
                        else if(abilityRowClicked == 2) {
                            Player.ability2Level++;
                        }
                        else if(abilityRowClicked == 3) {
                            Player.ability3Level++;
                        }
                        SaveOrLoad.save("Save1", player);
                        SaveOrLoad.load("Save1");
                    }
                }
            }
        }
    }
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

        // Todo show spells on left side (all spells)
        abilityPainter(Player.ability1Name, 1, g);
        abilityPainter(Player.ability2Name, 2, g);
        abilityPainter(Player.ability3Name, 3, g);

        g.drawString("Ability Points: " + Player.unspentAbilityPoints, 85, 450);
        g.drawString("Money: " + Player.money, Game.WIDTH - (spacer*10)-5, 450);
        g.drawString("Press SPACE to go back", Game.WIDTH/2 - 90, 450);

        // ability description box
        // todo make a set hotkey section
        // todo make an "are you sure you want to buy this" section
        if(isAbilityRowClicked) {
            g.setColor(Color.black);
            g.fillRect(290, 10, 405, 190);
            g.setColor(Color.white);
            g.drawString("Description", 447, 35);
            g.drawLine(290, 40, 695, 40);
            g.drawString("Upgrade", 597, 187);
            g.drawRect(665, 170, 20, 20);
            g.drawLine(670, 180, 680, 180);
            g.drawLine(675, 175, 675, 185);
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
    private void abilityPainter(String abilityName, int rowNumber, Graphics g) {
        // todo find a way to make this more efficient in terms of displaying the levels
        rowNumber = rowNumber - 1;
        g.drawString(abilityName, 15, 65 + (rowNumber * 45));
        if(rowNumber == 0) {
            g.drawString("Level: " + Player.ability1Level, 172, 65 + (rowNumber * 45));
        }
        else if(rowNumber == 1) {
            g.drawString("Level: " + Player.ability2Level, 172, 65 + (rowNumber * 45));
        }
        else if(rowNumber == 2) {
            g.drawString("Level: " + Player.ability3Level, 172, 65 + (rowNumber * 45));
        }
        g.drawImage(ImageHandler.images.get(abilityName.replaceFirst(abilityName.substring(0,1), abilityName.toLowerCase().substring(0,1))),
                240, 40 + (rowNumber * 45), 45, 45, null);
        g.drawRect(240, 40 + (rowNumber * 45), 44, 45);
        g.drawLine(5, 85 + (rowNumber * 45), 284, 85 + (rowNumber * 45));
    }
    private void pointAdder(int mouseY, int rowNumber, int upgradeNumber) {
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
