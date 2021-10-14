package dev.wither.orca.bukkit.revamps;

import lombok.Getter;
import org.bukkit.entity.Player;

public class OrcaPlayer {

    @Getter private final Player player;

    public OrcaPlayer(Player player) {

        this.player = player;

    }

}
