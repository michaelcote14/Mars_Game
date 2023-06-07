package Main;

import java.io.Serializable;

public class SaveDataStorage implements Serializable {

    // Player Stats
    int level;
    int fireRate;
    int maxHealth;
    float maxXp;
    float currentXp;
    int speed;
    int money;

    // Abilities
     String ability1Name;
     String ability2Name;
     String ability3Name;

     int unspentAbilityPoints;
     int ability1Level;
     int ability2Level;
     int ability3Level;

}
