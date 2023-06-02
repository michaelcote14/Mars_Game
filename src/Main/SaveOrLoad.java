package Main;

import java.io.*;


public class SaveOrLoad {
    Player player;

    public static void save(String saveName, Player player) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(saveName + ".dat")));

            SaveDataStorage data = new SaveDataStorage();

            data.maxHealth = (int) player.maxHealth;
            data.speed = player.speed;
            data.fireRate = player.fireRate;
            data.money = player.money;

            // Write the DataStorage object to the file
            oos.writeObject(data);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void load(String saveName, Player player) {
        try {
            ObjectInputStream oos = new ObjectInputStream(new FileInputStream(new File(saveName + ".dat")));

            // Read the DataStorage object from the file
            SaveDataStorage data = (SaveDataStorage)oos.readObject();

            Player.maxHealth = data.maxHealth;
            Player.currentHealth = Player.maxHealth;
            Player.speed = data.speed;
            Player.fireRate = data.fireRate;
            Player.money = data.money;
        }
        catch (EOFException e) {}
        catch (Exception e) {
            System.out.println("Load Exception: " + e);
        }
    }
}
