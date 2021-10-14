package dev.wither.orca.view;

import dev.wither.orca.view.listener.ViewListener;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public abstract class OrcaView {

    @Getter private final Player player;
    @Getter @Setter private OrcaPage view;

    /**
     * Toggled to true whenever a player's menu is being manually closed only to be reopened.
     */
    @Getter @Setter private boolean switching;

    public OrcaView(Player player) {

        this.player = player;

    }

    public void open() {

        view.open(player);
        ViewListener.getListener().addCache(player, this);

    }

    public void onClose(InventoryCloseEvent event) {

        if (switching) return;
        ViewListener.getListener().removeCache(player);

    }

    public void onClick(InventoryClickEvent event) {

        view.click(event);

    }

    public void switchPage(OrcaPage page) {

        setView(page);
        setSwitching(true);
        view.open(player);
        setSwitching(false);

    }

}
