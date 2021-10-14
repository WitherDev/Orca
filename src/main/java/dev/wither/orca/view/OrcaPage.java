package dev.wither.orca.view;

import dev.wither.orca.Orca;
import dev.wither.orca.bukkit.creation.ItemCreator;
import dev.wither.orca.view.touch.Touch;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * OrcaPage represents a single page/inventory inside of an OrcaView.
 */
public class OrcaPage {

    @Getter private static final Touch backgroundTouch = new Touch() {
        @Override
        public void onClick(InventoryClickEvent event) {
            event.setCancelled(true);
        }
    };

    @Getter private final Inventory inventory;
    @Getter private final HashMap<Integer, Touch> slots;

    @Setter private ItemStack background;

    @Getter @Setter private boolean collectible;

    public OrcaPage(String name, int rows) {

        inventory = Bukkit.createInventory(null, rows*9, Orca.addColor(name));
        slots = new HashMap<>();

        collectible = false;

        background = new ItemCreator(Material.STAINED_GLASS_PANE).setName(" ").setDurability((short) 7).getItem();

    }

    public OrcaPage(String name, int rows, boolean collectible) {

        inventory = Bukkit.createInventory(null, rows, Orca.addColor(name));
        slots = new HashMap<>();

        this.collectible = collectible;

        background = new ItemCreator(Material.STAINED_GLASS_PANE).setName("").setDurability((short) 7).getItem();

    }

    public void open(Player player) {

        player.openInventory(inventory);

    }

    public void click(InventoryClickEvent event) {

        int slot = event.getSlot();
        Touch touch = getSlots().get(slot);

        event.setCancelled(!collectible);

        if (touch != null) {

            touch.onClick(event);

        }

    }

    public void columnBackground(int column) {

        if (column > 9) return;

        int total = getInventory().getSize();

        for (int i = column-1; i < total; i += 9) {

            addBackground(i);

        }

    }
    public void rowBackground(int row) {

        if (row > getInventory().getSize()/9) return;

        int start = (row-1)*9;
        int end = start+9;
        for (int i = start; i < end; i++) {

            addBackground(i);

        }

    }
    public void borderBackground() {

        int rows = getInventory().getSize()/9;

        columnBackground(1);
        columnBackground(9);
        rowBackground(1);
        rowBackground(getInventory().getSize()/9);

    }
    public void addBackground(int slot) {

        if (slot > inventory.getSize()) return;

        slot(slot, backgroundTouch, background);

    }
    public void populateBackground() {

        while (nextEmpty() != -1) {

            addBackground(nextEmpty());

        }

    }

    /**
     * Attach a Touch and set the ItemStack at the slot with the given index.
     * @param slot index where this slot should be placed.
     * @param touch the Touch to be attached to the slot.
     * @param item the ItemStack to be placed in the inventory.
     */
    public void slot(int slot, Touch touch, ItemStack item) {

        inventory.setItem(slot, item);
        slots.put(slot, touch);

    }

    @Override @SuppressWarnings("all")
    public OrcaPage clone() {

        OrcaPage page = new OrcaPage(getInventory().getName(), getInventory().getSize(), collectible);

        page.setBackground(background);

        for (int i = 0; i != inventory.getSize(); i++) {

            page.slot(i, slots.get(i), inventory.getItem(i));

        }

        return page;

    }

    /**
     * Find the next slot that has no Touch attached to it.
     * @return the index of the next empty slot.
     */
    public int nextEmpty() {

        int i = 0;
        while (slots.get(i) != null) {

            i++;

        }

        if (i >= inventory.getSize()) return -1;

        return i;

    }

}
