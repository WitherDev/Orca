package dev.wither.orca.bukkit.creation;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class HeadCreator extends ItemCreator {

    public HeadCreator() {

        super(Material.SKULL);

    }

    public HeadCreator setTexture(String url) {

        if (url.isEmpty()) return this;

        SkullMeta skullMeta = (SkullMeta) getItem().getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {
            Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(skullMeta, profile);
            mtd.setAccessible(false);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }

        getItem().setItemMeta(skullMeta);

        return this;

    }

    @Override
    public HeadCreator setType(Material type) {

        return this;

    }

    public static ItemStack getSkull(String url) {

        ItemStack head = new ItemStack(Material.SKULL);
        if (url.isEmpty()) return head;

        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {
            Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(skullMeta, profile);
            mtd.setAccessible(false);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }

        head.setItemMeta(skullMeta);
        return head;
    }

}
