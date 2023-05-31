package Main;

import java.io.*;

public class SaveOrLoad {

    public void save(String saveName) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(saveName)));

            SaveDataStorage data = new SaveDataStorage();

            data.maxHealth = (int) HUD.maxHealth;
            data.currentHealth = (int) HUD.currentHealth;
            data.speed = Player.speed;
            data.fireRate = Player.fireRate;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void load(String saveName) {
        try {
            ObjectInputStream oos = new ObjectInputStream(new FileInputStream(new File(saveName)));

            // Read the DataStorage object from the file
            SaveDataStorage data = (SaveDataStorage) oos.readObject();

            HUD.maxHealth = data.maxHealth;
            HUD.currentHealth = data.currentHealth;
            Player.speed = data.speed;
            Player.fireRate = data.fireRate;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
