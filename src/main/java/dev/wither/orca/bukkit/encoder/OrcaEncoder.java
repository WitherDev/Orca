package dev.wither.orca.bukkit.encoder;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class OrcaEncoder {

    public static String encodeInventory(Inventory inventory) {
        try {
            ByteArrayOutputStream str = new ByteArrayOutputStream();
            BukkitObjectOutputStream data = new BukkitObjectOutputStream(str);
            data.writeInt(inventory.getSize());
            data.writeObject(inventory.getName());
            for (int i = 0; i < inventory.getSize(); i++) {
                data.writeObject(inventory.getItem(i));
            }
            data.close();
            return Base64.getEncoder().encodeToString(str.toByteArray());
        } catch (Exception e) {

            e.printStackTrace();

        }
        return "";
    }

    public static Inventory decodeInventory(String base64) {

        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
            BukkitObjectInputStream data = new BukkitObjectInputStream(stream);
            Inventory inventory = Bukkit.createInventory(null, data.readInt(), data.readObject().toString());
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) data.readObject());
            }
            data.close();
            return inventory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encodeItemStack(ItemStack item) {

        try {

            ByteArrayOutputStream str = new ByteArrayOutputStream();
            BukkitObjectOutputStream data = new BukkitObjectOutputStream(str);
            data.writeObject(item);
            data.close();
            return Base64.getEncoder().encodeToString(str.toByteArray());

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "";

    }

    public static ItemStack decodeItemStack(String base64) {

        try {

            ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
            BukkitObjectInputStream data = new BukkitObjectInputStream(stream);
            ItemStack item = (ItemStack) data.readObject();
            data.close();
            return item;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

}
