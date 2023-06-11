package Objects;

import Main.MapHandler;
import Main.Player;
import Utilities.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Computer extends GameObject {
    private BufferedImage computerImage, droppedItemsConverterImage;
    private ObjectHandler oHandler;
    private KeyHandler keyHandler;
    private MapHandler mapHandler;
    private boolean engagedWithComputer = false;
    private int cursorLocation = 0;
    private int rowFlashCounter = 0;
    private int screenSelectionDelay = 0;
    public int mapSelected = 0;
    private String state = "mainMenu";

    public Computer(float x, float y, ID id, ObjectHandler oHandler, KeyHandler keyHandler, MapHandler mapHandler) {
        super(x, y, id);
        this.oHandler = oHandler;
        this.keyHandler = keyHandler;
        this.mapHandler = mapHandler;
        computerImage = ImageHandler.images.get("computer");
        droppedItemsConverterImage = ImageHandler.images.get("droppedItemsConverter");
    }

    public void tick() {
        for (int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);

            if (tempObject.getId() == ID.Player) {
                if (getBoundsBig().intersects(tempObject.getBounds())) {
                    if(this.keyHandler.isEnterPressed()) {
                        engagedWithComputer = true;
                    }

                    if(engagedWithComputer == true) {
                        // Main Menu
                        if(state == "mainMenu") {
                            if(keyHandler.isEnterPressed() == true && screenSelectionDelay > 10) {
                                screenSelectionDelay = 0;

                                if (cursorLocation == 0) {
                                    state = "portalControllerMenu";
                                } else if (cursorLocation == 1) {
                                    state = "itemExtractorMenu";
                                } else if (cursorLocation == 2) {
                                    engagedWithComputer = false;
                                    keyHandler.setEnterPressed(false);
                                    cursorLocation = 0;
                                }
                            }
                        }
                    }

                    if(this.keyHandler.isDownPressed()) {
                        cursorLocation++;
                        if(cursorLocation > 5) {
                            cursorLocation = 0;
                        }
                        this.keyHandler.setDownPressed(false);
                    }
                    else if(this.keyHandler.isUpPressed()) {
                        cursorLocation--;
                        if(cursorLocation < 0) {
                            cursorLocation = 5;
                        }
                        this.keyHandler.setUpPressed(false);
                    }

                    if(state == "portalControllerMenu") {
                        if(this.keyHandler.isEnterPressed() && screenSelectionDelay > 10) {
                            if(cursorLocation != 2) {
                                mapSelected = cursorLocation;
                                engagedWithComputer = false;
                                this.keyHandler.setEnterPressed(false);
                            }
                            state = "mainMenu";
                            screenSelectionDelay = 0;
                        }
                    }else if(state == "itemExtractorMenu") {
                        if(this.keyHandler.isEnterPressed() && screenSelectionDelay > 10) {
                            screenSelectionDelay = 0;
                            if(cursorLocation == 2) {
                                state = "mainMenu";
                                screenSelectionDelay = 0;
                            }
                            else if(cursorLocation == 0) {
                                System.out.println(cursorLocation);
                            }
                            else if(cursorLocation == 1) {
                                System.out.println(cursorLocation);
                            }
                        }
                    }

                }
                else { engagedWithComputer = false; }
            }
        }
        rowFlashCounter++;
        if(rowFlashCounter > 30) { rowFlashCounter = 0; }
        if(engagedWithComputer == true) {
            Player.isInteracting = true;
            screenSelectionDelay++;
        } else { Player.isInteracting = false; }
    }

    public void render(Graphics g) {
        g.drawImage(computerImage, (int)x, (int)y,null);
        g.drawImage(droppedItemsConverterImage, (int)x-10, (int)y-130, 120, 150, null);

        if(engagedWithComputer == true) {
            displayMenu(g, 0);
            if(state == "portalControllerMenu") {
                displayMenu(g, 1);
            } else if(state == "itemExtractorMenu") {
                displayMenu(g, 2);
            }
        }
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 22));
        g.drawString("Level: " + mapSelected, (int)x + 117, (int)y - 60);
    }

    private void displayMenu(Graphics g, int menuNumber) {
        // outline
        g.setColor(Color.white);
        g.drawRect((int)x-61, (int)y-81, 161, 81);
        // screen
        g.setColor(Color.BLACK);
        g.fillRect((int)x-60, (int)y-80, 160, 80);
        if(rowFlashCounter > 20) {
            // row color
            g.setColor(Color.white);
            g.fillRect((int) x - 58, (int) (y - 74) + (cursorLocation * 13), 5, 8);
        }
        // text
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 11));
        if(menuNumber == 0) {
            g.drawString("Portal Controller", (int) x - 48, (int) y - 66);
            g.drawString("Loot Extractor", (int) x - 48, (int) y - 53);
            g.drawString("Exit", (int) x - 48, (int) y - 40);
        } else if(menuNumber == 1) {
            g.drawString("Random Dungeon", (int)x-48, (int)y-66);
            g.drawString("Dungeon 1", (int)x-48, (int)y-53);
            g.drawString("Back", (int) x - 48, (int) y - 40);
        } else if(menuNumber == 2) {
            g.drawString("Loot Extractor", (int)x-48, (int)y-66);
            g.drawString("None", (int)x-48, (int)y-53);
            g.drawString("Back", (int) x - 48, (int) y - 40);
        }

    }
    private void displayPortalControllerMenu(Graphics g) {

    }
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 28);
    }
    public Rectangle getBoundsBig() {
        return new Rectangle((int)x, (int)y, 42, 56);
    }

}
