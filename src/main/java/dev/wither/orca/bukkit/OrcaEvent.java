package dev.wither.orca.bukkit;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * OrcaEvent is an abstract implementation of the Bukkit Event,
 */
public abstract class OrcaEvent extends Event {

    /**
     * Easier method for calling the Event to the Bukkit PluginManager.
     */
    public void call() {

        Bukkit.getPluginManager().callEvent(this);

    }

    @Getter private static final HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
