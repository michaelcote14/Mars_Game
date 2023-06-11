package Main;

import java.io.*;
import Abilities.Abilities;


public class SaveOrLoad {
    public static void save(String saveName) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(saveName + ".dat")));

            SaveDataStorage data = new SaveDataStorage();

            data.level = Player.level;
            data.maxHealth = (int) Player.maxHealth;
            data.maxXp = (int) Player.maxXp;
            data.currentXp = (int) Player.currentXp;
            data.speed = Player.walkSpeed;
            data.fireRate = Player.fireRate;

            data.nameList = Abilities.nameList;
            data.levelBook = Abilities.levelBook;
            data.abilityPoints = Player.abilityPoints;
            data.characteristicPoints = Player.characteristicPoints;
            data.characteristicsList = Abilities.characteristicsList;
            data.characteristicPointsBook = Abilities.characteristicPointsBook;
            data.blockedCharacteristics = Abilities.blockedCharacteristics;

            data.inventory = Player.inventory;

            // Write the DataStorage object to the file
            oos.writeObject(data);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void load(String saveName) {
        try {
            ObjectInputStream oos = new ObjectInputStream(new FileInputStream(new File(saveName + ".dat")));

            // Read the DataStorage object from the file
            SaveDataStorage data = (SaveDataStorage)oos.readObject();

            Player.level = data.level;
            Player.maxHealth = data.maxHealth;
            Player.currentHealth = Player.maxHealth;
            Player.maxXp = data.maxXp;
            Player.currentXp = data.currentXp;
//            Player.walkSpeed = data.speed;
            Player.walkSpeed = 70;
            Player.fireRate = data.fireRate;

            Abilities.nameList = data.nameList;
            Abilities.levelBook = data.levelBook;
            Player.abilityPoints = data.abilityPoints;
            Player.characteristicPoints = data.characteristicPoints;
            Abilities.characteristicsList = data.characteristicsList;
            Abilities.characteristicPointsBook = data.characteristicPointsBook;
            Abilities.blockedCharacteristics = data.blockedCharacteristics;

            Player.inventory = data.inventory;

        }
        catch (EOFException e) {}
        catch (Exception e) {
            System.out.println("Load Exception: " + e);
        }
    }
}
