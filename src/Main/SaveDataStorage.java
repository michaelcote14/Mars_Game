package Main;

import Abilities.Abilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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
    ArrayList<String> nameList = Abilities.nameList;
    HashMap<String, Integer> levelBook = Abilities.levelBook;
     int unspentAbilityPoints;




}
