package dev.wither.orca.view.touch;

import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class SmartTouch extends Touch {

    @Override
    public void onClick(InventoryClickEvent event) {

        switch (event.getClick()) {

            case LEFT: onLeftClick(event); break;
            case RIGHT: onRightClick(event); break;
            case SHIFT_RIGHT: onShiftRightClick(event); break;
            case SHIFT_LEFT: onShiftLeftClick(event); break;

        }

    }

    public abstract void onLeftClick(InventoryClickEvent event);
    public abstract void onRightClick(InventoryClickEvent event);
    public abstract void onShiftLeftClick(InventoryClickEvent event);
    public abstract void onShiftRightClick(InventoryClickEvent event);

}
