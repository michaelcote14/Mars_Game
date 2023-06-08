package Abilities;

import java.util.ArrayList;
import java.util.HashMap;

public class Abilities {
    public static ArrayList<String> nameList = new ArrayList<>();
    public static HashMap<String, Integer> levelBook = new HashMap<>();
    public static HashMap<String, Float> damageBook = new HashMap<>();
    public static HashMap<String, Float> durationBook = new HashMap<>();
    public static HashMap<String, Float> cooldownBook = new HashMap<>();

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
    }

}
