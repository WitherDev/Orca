package dev.wither.orca.bukkit.creation.leather;

import dev.wither.orca.bukkit.creation.ItemCreator;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ArmorCreator extends ItemCreator {

    public ArmorCreator(ArmorType type) {

        super(type.getMaterial());

    }

    public ArmorCreator setColor(Color color) {

        LeatherArmorMeta meta = (LeatherArmorMeta) getItem().getItemMeta();
        meta.setColor(color);
        getItem().setItemMeta(meta);

        return this;

    }

    @Override
    public ArmorCreator setType(Material type) {

        return this;

    }

}
