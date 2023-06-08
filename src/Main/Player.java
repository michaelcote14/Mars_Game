package Main;

import Abilities.Abilities;
import Abilities.DiceRoll;
import Abilities.RouletteWheel;
import Utilities.*;
import Utilities.MouseHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    ObjectHandler oHandler;
    private BufferedImage playerImage;
    private BufferedImage[] levelUpImages;
    private Animation levelUpAnim;
    Game game;
    Camera camera;
    MouseHandler mouseHandler;
    KeyHandler keyHandler;

    Animation anim;

    // Stats
    public static int level = 1;
    public static float currentXp = 0;
    public static float maxXp = 100;
    public static float currentHealth = 1000;
    public static float maxHealth = 1000;
    public static float basicAttackDamage = 1;
    public static int fireRate = 30;
    private int fireRateCounter = 0;
    public static int walkSpeed = 4;
    public static int lifeStealRate = 1;
    public static int money = 10000;
    private boolean didLevelUp;
    private int levelUpCounter = 0;
    private boolean hasDeathBeenHandled = false;

    // Abilities
    public static String ability1Name, ability2Name, ability3Name;
    public static float ability1Cooldown, ability2Cooldown, ability3Cooldown;
    public static float ability1Damage, ability2Damage, ability3Damage;
    public static int unspentAbilityPoints = 10;

    public Player(float x, float y, ID id, ObjectHandler oHandler, Game game, Camera camera, KeyHandler keyHandler, MouseHandler mouseHandler) {
        super(x, y, id);
        this.oHandler = oHandler;
        this.game = game;
        this.camera = camera;
        this.mouseHandler = mouseHandler;
        this.keyHandler = keyHandler;

        ability1Name = Abilities.nameList.get(0);
        ability1Cooldown = Abilities.cooldownBook.get(ability1Name);
        ability2Name = Abilities.nameList.get(1);
        ability2Cooldown = Abilities.cooldownBook.get(ability2Name);
        ability3Name = Abilities.nameList.get(2);
        ability3Cooldown = Abilities.cooldownBook.get(ability3Name);

        this.didLevelUp = false;

        this.playerImage = ImageHandler.images.get("gamblerDown1");
        levelUpImages = ImageHandler.createImageArray("levelUp", 8);
        this.levelUpAnim = new Animation(180, levelUpImages);
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
            else if(tempObject.getId().toString().contains("Enemy") || tempObject.getId().toString().contains("Trap")) {
                if(getBounds().intersects(tempObject.getBounds())) {
                    currentHealth -= 2;
                }
            }
            else if(tempObject.getId() == ID.Explosion) {
                if(getBounds().intersects(tempObject.getBounds())) {
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
            if(abilityActivator(ability1Name) == true) {
                keyHandler.setOnePressed(false);
                this.ability1Cooldown = 1; // 30 seconds //todo make this more accurate
            }
        }
        else if(keyHandler.isTwoPressed() && this.ability2Cooldown <= 0) {
            if(abilityActivator(ability2Name) == true) {
                keyHandler.setTwoPressed(false);
                this.ability2Cooldown = 1; // 30 seconds
            }
        }
        else if (keyHandler.isThreePressed() && this.ability3Cooldown <= 0) {
            if(abilityActivator(ability3Name) == true) {
                keyHandler.setThreePressed(false);
                this.ability3Cooldown = 1; // 30 seconds
            }
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

        if(currentXp >= maxXp) {
            level++;
            currentXp = 0;
            maxXp = (int) (maxXp * 1.5);
            currentHealth = maxHealth;
            this.didLevelUp = true;
            unspentAbilityPoints++;
        }

//        anim.runAnimation();
        fireRateCounter++;
    }

    public void render(Graphics g) {
        g.drawImage(this.playerImage, (int)x, (int)y, 32, 42, null);
        if(this.didLevelUp == true) {
            this.levelUpAnim.runAnimation();
            this.levelUpAnim.drawAnimation(g, (int)x-34, (int)y-52, 20, 150, 150);
            if(this.levelUpAnim.getCount() == 7 && levelUpCounter > 2300) {
                this.levelUpAnim.setCount(0);
                this.didLevelUp = false;
            }
            else {levelUpCounter++;}
        }
//        if(velX == 0 && velY == 0)
//            g.drawImage(this.playerImage, (int)x, (int)y, null);
//        else
//            anim.drawAnimation(g, x, y, 0, 32, 48);
    }
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 42); // 32, 48 is the size of the wizard
    }
    public float getX() {
        return (int)x;
    }
    public float getY() {
        return (int)y;
    }

    public boolean is_room_to_move(int x, int y, Rectangle myRect, Rectangle otherRect) {
        myRect.x = x;
        myRect.y = y;
        if(myRect.intersects(otherRect)) {
            return false;
        }
        return true;
    }
    private boolean abilityActivator(String abilityName) {
        if(abilityName.equals("RouletteWheel")) {
            oHandler.addObject(new RouletteWheel(x, y, ID.valueOf("RouletteWheelAbility"), oHandler));
        }
        else if(abilityName.equals("DiceRoll")) {
            oHandler.addObject(new DiceRoll(x, y, ID.valueOf("DiceRollAbility"), oHandler));
        }
        else if(abilityName.equals("CardThrow")) {
            if(mouseHandler.isMouseClicked()) {
                PointerInfo a = MouseInfo.getPointerInfo();
                Point b = a.getLocation();
                MouseHandler.shoot(b.x, b.y, oHandler, imageHandler, camera, "CardThrow");
            } else {return false;}
        }
        else if(abilityName.equals("CasinoChip")) {
            if(mouseHandler.isMouseClicked()) {
                PointerInfo a = MouseInfo.getPointerInfo();
                Point b = a.getLocation();
                MouseHandler.shoot(b.x, b.y, oHandler, imageHandler, camera, "CasinoChip");
            } else {return false;}

        }
        return true;
    }

}

