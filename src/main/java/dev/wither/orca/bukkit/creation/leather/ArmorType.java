package dev.wither.orca.bukkit.creation.leather;

import lombok.Getter;
import org.bukkit.Material;

/**
 * Used primarily by Orca ArmorCreator (ItemCreator implementation) to select the armor slot for a leather armor piece.
 */
public enum ArmorType {

    HELMET(Material.LEATHER_HELMET),
    CHESTPLATE(Material.LEATHER_CHESTPLATE),
    LEGGINGS(Material.LEATHER_LEGGINGS),
    BOOTS(Material.LEATHER_BOOTS);

    @Getter private final Material material;

    ArmorType(Material material) {

        this.material = material;

    }

}
