package dev.wither.orca.bukkit.events;

import dev.wither.orca.bukkit.OrcaListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Listener for Bukkit events that are then converted into Orca events.
 */
@OrcaListener
public class OrcaBukkitListener implements Listener {

    public static boolean enabled = true;

    /**
     * When a players moves upwards, the PlayerJumpEvent is called.
     * No support for swimming upstream or forced pushing has been added.
     * @param event event for a player movement.
     */
    @EventHandler
    public void onMoveEvent(PlayerMoveEvent event) {

        if (event.getTo().getY() > event.getFrom().getY()) {

            PlayerJumpEvent jumpEvent = new PlayerJumpEvent(event.getPlayer(), event.getTo(), event.getFrom());
            jumpEvent.call();

            if (jumpEvent.isCancelled()) {

                event.getPlayer().teleport(event.getFrom());
                event.setCancelled(true);

            }

        }

    }

}
