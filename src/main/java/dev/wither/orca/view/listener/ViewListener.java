package dev.wither.orca.view.listener;

import dev.wither.orca.bukkit.OrcaListener;
import dev.wither.orca.view.OrcaView;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created automatically. Don't bother.
 */
@OrcaListener
public class ViewListener implements Listener {

    /**
     * Statically interact with the ViewListener, if needed.
     */
    @Getter private static ViewListener listener;

    /**
     * Player UUID is used in preference to a Player for an expectedly better hashing function.
     */
    @Getter private final HashMap<UUID, OrcaView> cache;

    public ViewListener() {

        listener = this;
        cache = new HashMap<>();

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        OrcaView view = cache.get(player.getUniqueId());

        if (view != null && event.getClickedInventory().equals(view.getView().getInventory())) {

            view.onClick(event);

        }

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {

        Player player = (Player) event.getPlayer();
        OrcaView view = cache.get(player.getUniqueId());

        if (view != null) {

            view.onClose(event);

        }

    }

    public void addCache(Player player, OrcaView view) {

        cache.put(player.getUniqueId(), view);

    }

    public void removeCache(Player player) {

        cache.remove(player.getUniqueId());

    }

}
