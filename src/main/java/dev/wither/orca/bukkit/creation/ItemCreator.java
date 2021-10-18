package dev.wither.orca.bukkit.creation;

import com.fasterxml.jackson.annotation.JsonValue;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import dev.wither.orca.Orca;
import dev.wither.orca.bukkit.encoder.OrcaEncoder;
import lombok.Getter;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ItemCreator is a container for an ItemStack that allows easy modification of the object.
 * Used as a builder for an ItemStack since each methods returns the target object.
 *
 * External NBT API is used to modify and access the NBT of the object.
 */
public class ItemCreator {

    @Getter private final ItemStack item;
    @Getter private final NBTItem nbt;

    /**
     * Construct an ItemCreator with an ItemStack of the specified type and a quantity of one.
     * @param material material value for the created ItemStack.
     */
    public ItemCreator(@NotNull Material material) {

        item = new ItemStack(material, 1);
        nbt = new NBTItem(item, true);

    }

    /**
     * Construct an ItemCreator with an ItemStack of the specified type and a quantity of one.
     * @param material material value for the created ItemStack.
     * @param amount amount value to be applied to the ItemStack.
     */
    public ItemCreator(@NotNull Material material, int amount) {

        item = new ItemStack(material, amount);
        nbt = new NBTItem(item, true);

    }

    public ItemCreator(@NotNull ItemStack item) {

        this.item = item;
        nbt = new NBTItem(item, true);

    }

    public ItemCreator setName(@NotNull String name) {

        ItemMeta m = item.getItemMeta();
        m.setDisplayName(Orca.addColor(name));
        item.setItemMeta(m);
        return this;

    }

    public ItemCreator setAmount(@NotNull int amount) {

        item.setAmount(amount);
        return this;

    }

    public ItemCreator addLore(String... lines) {

        ItemMeta m = item.getItemMeta();
        val coloredLore = m.getLore();
        for (String line : lines) {

            coloredLore.add(Orca.addColor(line));

        }
        m.setLore(coloredLore);
        item.setItemMeta(m);
        return this;

    }

    public ItemCreator setLore(String... lore) {

        ItemMeta m = item.getItemMeta();

        val coloredLore = new ArrayList<String>();
        for (String line : lore) {

            coloredLore.add(Orca.addColor(line));

        }

        m.setLore(coloredLore);
        item.setItemMeta(m);
        return this;

    }

    public ItemCreator setLore(List<String> lore) {

        ItemMeta m = item.getItemMeta();
        val coloredLore = new ArrayList<String>();
        for (String line : lore) {

            coloredLore.add(Orca.addColor(line));

        }

        m.setLore(coloredLore);
        item.setItemMeta(m);
        return this;

    }

    public ItemCreator setDurability(short durability) {

        item.setDurability(durability);
        return this;

    }

    public ItemCreator setType(Material type) {

        item.setType(type);
        return this;

    }

    public ItemCreator addEnchant(Enchantment enchantment, int level) {

        item.addUnsafeEnchantment(enchantment, level);
        return this;

    }

    public ItemCreator addSafeEnchantment(Enchantment enchantment, int level) {

        item.addEnchantment(enchantment, level);
        return this;

    }

    public ItemCreator addNBT(String key, String value) {

        NBTItem nbt = new NBTItem(getItem());
        nbt.setString(key, value);
        return this;

    }

    public String nbtString(String key) {

        return nbt.getString(key);

    }

    public NBTCompound nbtCompound() {

        return nbt;

    }
    /**
     * Create an ItemCreator from the data in the ConfigurationSection.
     * @param section ConfigurationSection which contains the data for the ItemStack.
     * @return ItemCreator containing the configured ItemStack, null if invalid format.
     */
    public static @Nullable ItemCreator fromConfiguration(ConfigurationSection section) {

        Material material = Material.matchMaterial(section.getString("material"));
        int amount = section.getInt("amount");

        ItemCreator creator;

        if (material != null) {
            creator = new ItemCreator(material);
        } else return null;
        if (amount != 0) {
            creator.setAmount(amount);
        } else return null;

        // Name
        String name = section.getString("name");
        if (name != null) creator.setName(name);

        // Lore
        List<String> lore = section.getStringList("lore");
        if (lore != null && !lore.isEmpty()) creator.setLore(lore);

        // Enchantment
        List<String> enchantments = section.getStringList("enchantments");
        if (enchantments != null && !enchantments.isEmpty()) {

            for (String string : enchantments) {

                String[] values = string.split(", ");

                try {
                    Enchantment e = Enchantment.getByName(values[0]);
                    int i = Integer.parseInt(values[1]);
                    creator.addEnchant(e, i);
                } catch (Exception ignored) {}

            }

        }

        // NBT
        List<String> nbtValues = section.getStringList("nbt");
        if (nbtValues != null && !nbtValues.isEmpty()) {

            for (String string : nbtValues) {

                String[] values = string.split(", ");
                creator.nbt.setString(values[0], values[1]);

            }

        }

        return creator;

    }

    /**
     * Add the ItemStack data to the ConfigurationSection.
     * @param section ConfigurationSection to which the ItemStack should be saved.
     */
    // TODO NBT SUPPORT
    public void toSection(ConfigurationSection section) {

        ItemMeta m = item.getItemMeta();
        section.set("material", item.getType().name());
        section.set("amount", item.getAmount());
        section.set("name", m.getDisplayName());

        section.set("lore", m.getLore());

        List<String> enchantments = new ArrayList<>();
        for (Map.Entry<Enchantment, Integer> entry : m.getEnchants().entrySet()) {

            enchantments.add(entry.getKey().getName() + ", " + entry.getValue());

        }
        section.set("enchantments", enchantments);

        // ADD NBT SUPPORT

    }

}
