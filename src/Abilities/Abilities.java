package Abilities;

import java.util.ArrayList;
import java.util.HashMap;

public class Abilities {
    public static ArrayList<String> abilityBook = new ArrayList<>();
    public static HashMap<String, Float> abilityCooldowns = new HashMap<>();

    public Abilities() {
        abilityBook.add("RouletteWheel");
        abilityBook.add("CasinoChip");
        abilityBook.add("CardThrow");

        abilityCooldowns.put("RouletteWheel", 1.0f);
        abilityCooldowns.put("CasinoChip", 1.0f);
        abilityCooldowns.put("CardThrow", 1.0f);
    }

}
