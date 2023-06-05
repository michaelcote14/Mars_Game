package Main;

import Abilities.Abilities;
import Abilities.CasinoChip;
import Abilities.RouletteWheel;
import Utilities.*;
import Utilities.MouseHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    ObjectHandler oHandler;
    private BufferedImage playerImage;
    Game game;
    Camera camera;
    MouseHandler mouseHandler;
    KeyHandler keyHandler;

    Animation anim;

    // Stats
    public static float currentHealth = 1000;
    public static float maxHealth = 1000;
    public static float basicAttackDamage = 1;
    public static int fireRate = 30;
    public static int walkSpeed = 4;
    public static int lifeStealRate = 1;
    public static int money = 10000;
    private boolean hasDeathBeenHandled = false;

    // Abilities
    public static String ability1Name, ability2Name, ability3Name;
    public static float ability1Cooldown, ability2Cooldown, ability3Cooldown;

    int fireRateCounter = 0;

    public Player(float x, float y, ID id, ObjectHandler oHandler, Game game, ImageHandler imageHandler, Camera camera, KeyHandler keyHandler, MouseHandler mouseHandler) {
        super(x, y, id, imageHandler);
        this.oHandler = oHandler;
        this.game = game;
        this.camera = camera;
        this.mouseHandler = mouseHandler;
        this.keyHandler = keyHandler;

        this.ability1Name = Abilities.abilityBook.get(0);
        this.ability1Cooldown = Abilities.abilityCooldowns.get(this.ability1Name);
        this.ability2Name = Abilities.abilityBook.get(1);
        this.ability2Cooldown = Abilities.abilityCooldowns.get(this.ability2Name);
        this.ability3Name = Abilities.abilityBook.get(2);
        this.ability3Cooldown = Abilities.abilityCooldowns.get(this.ability3Name);

        this.playerImage = ImageHandler.images.get("gamblerDown1");

//        anim = new Animation(3, playerImage);
    }
    public void deathStatDecrease() {
        if(fireRate > 1) fireRate -= 1;
        if(walkSpeed > 1) walkSpeed -= 1;
        if(maxHealth > 50) maxHealth = maxHealth - 10; // you lose 10 max health when you die
    }
    private void collision() {
        for (int i = 0; i < oHandler.object.size(); i++) {
            GameObject tempObject = oHandler.object.get(i);

            if (tempObject.getId() == ID.Block) {
                if (!is_room_to_move((int) (x + velX), (int)y, getBounds(), tempObject.getBounds())) {
                    velX = 0;
                }
                else if (!is_room_to_move((int)x, (int) (y + velY), getBounds(), tempObject.getBounds())) {
                    velY = 0;
                }
            }
            else if(tempObject.getId().toString().contains("Enemy")) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    currentHealth -= 2;
                }
            }
            else if(tempObject.getId().toString().contains("Trap")) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    currentHealth -= 2;
                }
            }
            else if(tempObject.getId() == ID.HealthPack) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    Player.currentHealth = Player.maxHealth;
                    oHandler.removeObject(tempObject);
                }
            }
        }
        if (Player.currentHealth <= 0 && hasDeathBeenHandled == false) {
            Game.gameState = Game.STATE.Death;
            deathStatDecrease();
            // Save the game
            SaveOrLoad.save("Save1", this);
            hasDeathBeenHandled = true;
        }
    }
    public void tick() {
        collision();

        x += velX;
        y += velY;

        if(keyHandler.isDownPressed()) velY = 3 + walkSpeed /10;
        else if(!keyHandler.isUpPressed()) velY = 0;

        if(keyHandler.isUpPressed()) velY = -3 - walkSpeed /10;
        else if(!keyHandler.isDownPressed()) velY = 0;

        if(keyHandler.isRightPressed()) velX = 3 + walkSpeed /10;
        else if(!keyHandler.isLeftPressed()) velX = 0;

        if(keyHandler.isLeftPressed()) velX = -3 - walkSpeed /10;
        else if(!keyHandler.isRightPressed()) velX = 0;

        if(keyHandler.isOnePressed() && this.ability1Cooldown <= 0) {
            if(this.ability1Name.equals("RouletteWheel")) {
                oHandler.addObject(new RouletteWheel(x, y, ID.valueOf("RouletteWheelAbility"), oHandler, imageHandler));
            }
            this.ability1Cooldown = 1; // 30 seconds //todo make this more accurate
        }

        if(keyHandler.isTwoPressed() && mouseHandler.isMouseClicked() && this.ability2Cooldown <= 0) {
            PointerInfo a = MouseInfo.getPointerInfo();
            Point b = a.getLocation();
            // todo change below line string to abilityname
            MouseHandler.shoot(b.x, b.y, oHandler, imageHandler, camera, "CasinoChip");
            keyHandler.setTwoPressed(false);
            this.ability2Cooldown = 1; // 30 seconds
        }
        else if (keyHandler.isThreePressed() && mouseHandler.isMouseClicked() && this.ability3Cooldown <= 0) {
            System.out.println("Three activated");
            PointerInfo a = MouseInfo.getPointerInfo();
            Point b = a.getLocation();
            MouseHandler.shoot(b.x, b.y, oHandler, imageHandler, camera, "CardThrow");
            keyHandler.setThreePressed(false);
            this.ability3Cooldown = 1; // 30 seconds
        }
        // base attack
        else if(mouseHandler.isMouseClicked()) {
            if(fireRateCounter > 100 / ((fireRate / 3) + 1)) {
                PointerInfo a = MouseInfo.getPointerInfo();
                Point b = a.getLocation();
                MouseHandler.shoot(b.x, b.y, oHandler, imageHandler, camera, "Bullet");
                fireRateCounter = 0;
            }
        }
        if(this.ability1Cooldown > 0) {this.ability1Cooldown -= 0.01;}
        if(this.ability2Cooldown > 0) {this.ability2Cooldown-= 0.01;}
        if(this.ability3Cooldown > 0) {this.ability3Cooldown-= 0.01;}

        if(fireRate > 100) fireRate = 100; // this is the max fire rate //todo put this somewhere else

//        anim.runAnimation();
        fireRateCounter++;
    }

    public void render(Graphics g) {
        g.drawImage(this.playerImage, (int)x, (int)y, 32, 42, null);
//        if(velX == 0 && velY == 0)
//            g.drawImage(this.playerImage, (int)x, (int)y, null);
//        else
//            anim.drawAnimation(g, x, y, 0, 32, 48);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 42); // 32, 48 is the size of the wizard
    }

    public boolean is_room_to_move(int x, int y, Rectangle myRect, Rectangle otherRect) {
        myRect.x = x;
        myRect.y = y;
        if(myRect.intersects(otherRect)) {
            return false;
        }
        return true;
    }
}

