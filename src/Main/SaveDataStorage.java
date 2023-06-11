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

    // Abilities
    ArrayList<String> nameList = Abilities.nameList;
    HashMap<String, Integer> levelBook = Abilities.levelBook;
    int abilityPoints;

    // Characteristics
    int characteristicPoints;
    ArrayList<String> characteristicsList = Abilities.characteristicsList;
    HashMap<String, Integer> characteristicPointsBook = Abilities.characteristicPointsBook;
    ArrayList<String> blockedCharacteristics = Abilities.blockedCharacteristics;

    // Other
    HashMap<String, Integer> inventory = Player.inventory;

}
