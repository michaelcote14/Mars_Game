package Main;

import java.io.*;


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

            data.ability1Name = player.ability1Name;
            data.ability2Name = player.ability2Name;
            data.ability3Name = player.ability3Name;

            data.unspentAbilityPoints = player.unspentAbilityPoints;
            data.ability1Level = player.ability1Level;
            data.ability2Level = player.ability2Level;
            data.ability3Level = player.ability3Level;

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

            Player.ability1Name = data.ability1Name;
            Player.ability2Name = data.ability2Name;
            Player.ability3Name = data.ability3Name;

            Player.unspentAbilityPoints = data.unspentAbilityPoints;
            Player.ability1Level = data.ability1Level;
            Player.ability2Level = data.ability2Level;
            Player.ability3Level = data.ability3Level;

            // todo find a way to scale these so that they are not too overpowered
            Player.ability1Damage = Player.basicAttackDamage * Player.ability1Level;
            Player.ability2Damage = Player.basicAttackDamage * Player.ability2Level;
            Player.ability3Damage = Player.basicAttackDamage * Player.ability3Level;
        }
        catch (EOFException e) {}
        catch (Exception e) {
            System.out.println("Load Exception: " + e);
        }
    }
}
