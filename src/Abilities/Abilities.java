package Abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Abilities {
    public static ArrayList<String> nameList = new ArrayList<>();
    public static HashMap<String, Integer> levelBook = new HashMap<>();
    public static HashMap<String, Float> damageBook = new HashMap<>();
    public static HashMap<String, Float> durationBook = new HashMap<>();
    public static HashMap<String, Float> cooldownBook = new HashMap<>();
    public static ArrayList<String> characteristicsList = new ArrayList<>();
    public static ArrayList<String> immediateBlockedCharacteristics = new ArrayList<>();
    public static ArrayList<String> blockedCharacteristics = new ArrayList<>();
    public static HashMap<String, Integer> characteristicPointsBook = new HashMap<>();

    public Abilities() {
        if(nameList.isEmpty()) {
            nameList.add("RouletteWheel");
            nameList.add("CasinoChip");
            nameList.add("CardThrow");
            nameList.add("DiceRoll");
        }

        levelBook.put("RouletteWheel", 1);
        levelBook.put("CasinoChip", 1);
        levelBook.put("CardThrow", 1);
        levelBook.put("DiceRoll", 1);

        damageBook.put("RouletteWheel", 1.0f);
        damageBook.put("CasinoChip", 1.0f);
        damageBook.put("CardThrow", 1.0f);
        damageBook.put("DiceRoll", 1.0f);

        durationBook.put("RouletteWheel", 1.0f);
        durationBook.put("CasinoChip", 1.0f);
        durationBook.put("CardThrow", 1.0f);
        durationBook.put("DiceRoll", 1.0f);

        cooldownBook.put("RouletteWheel", 1.0f);
        cooldownBook.put("CasinoChip", 1.0f);
        cooldownBook.put("CardThrow", 1.0f);
        cooldownBook.put("DiceRoll", 1.0f);

        characteristicsList.add("Health");
        characteristicsList.add("LifeSteal");
        characteristicsList.add("WalkSpeed");
        characteristicsList.add("FireRate");
        characteristicsList.add("BasicAttackDamage");
        characteristicsList.add("BasicAttackRange");
        characteristicsList.add("AbilityDamage");
        characteristicsList.add("AbilityCooldownRate");
        characteristicsList.add("AbilityDuration");
        characteristicsList.add("Hunting"); // make a character have to have a minimum amount of hunting to get cerain resources from monsters
        characteristicsList.add("Crafting"); // make a character have to have a minimum amount of crafting to craft certain items

        characteristicPointsBook.put("Health", 1);
        characteristicPointsBook.put("LifeSteal", 1);
        characteristicPointsBook.put("WalkSpeed", 1);
        characteristicPointsBook.put("FireRate", 1);
        characteristicPointsBook.put("BasicAttackDamage", 1);
        characteristicPointsBook.put("BasicAttackRange", 1);
        characteristicPointsBook.put("AbilityDamage", 1);
        characteristicPointsBook.put("AbilityCooldownRate", 1);
        characteristicPointsBook.put("AbilityDuration", 1);
        characteristicPointsBook.put("Hunting", 1);
        characteristicPointsBook.put("Crafting", 1);

        for(int i = 0; i < characteristicsList.size()/2; i++) {
            blockedCharacteristics.add(characteristicsList.get(new Random().nextInt(characteristicsList.size())));
        }
    }

}
