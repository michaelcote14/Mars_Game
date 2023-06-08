package Main;

import java.io.*;
import Abilities.Abilities;


public class SaveOrLoad {
    public static void save(String saveName, Player player) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(saveName + ".dat")));

            SaveDataStorage data = new SaveDataStorage();

            data.level = player.level;
            data.maxHealth = (int) player.maxHealth;
            data.maxXp = (int) player.maxXp;
            data.currentXp = (int) player.currentXp;
            data.speed = player.walkSpeed;
            data.fireRate = player.fireRate;
            data.money = player.money;

            data.nameList = Abilities.nameList;
            data.levelBook = Abilities.levelBook;
            data.unspentAbilityPoints = player.unspentAbilityPoints;


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
            Player.walkSpeed = data.speed;
            Player.fireRate = data.fireRate;
//            Player.money = data.money;
            Player.money = 100000;

            Abilities.nameList = data.nameList;
            Abilities.levelBook = data.levelBook;
            Player.unspentAbilityPoints = data.unspentAbilityPoints;

        }
        catch (EOFException e) {}
        catch (Exception e) {
            System.out.println("Load Exception: " + e);
        }
    }
}
