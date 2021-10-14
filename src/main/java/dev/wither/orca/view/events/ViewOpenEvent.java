package dev.wither.orca.view.events;

import dev.wither.orca.bukkit.OrcaCancellableEvent;
import dev.wither.orca.view.OrcaView;
import lombok.Getter;
import org.bukkit.entity.Player;

public class ViewOpenEvent extends OrcaCancellableEvent {

    @Getter private final OrcaView view;
    @Getter private final Player player;

    public ViewOpenEvent(OrcaView view, Player player) {

        this.view = view;
        this.player = player;

    }

}
