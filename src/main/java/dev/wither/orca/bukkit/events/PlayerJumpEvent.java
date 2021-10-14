package dev.wither.orca.bukkit.events;

import dev.wither.orca.bukkit.OrcaCancellableEvent;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerJumpEvent extends OrcaCancellableEvent {

    @Getter private final Player player;
    @Getter private final Location to;
    @Getter private final Location from;

    public PlayerJumpEvent(Player player, Location to, Location from) {

        this.player = player;
        this.to = to;
        this.from = from;

    }

}
