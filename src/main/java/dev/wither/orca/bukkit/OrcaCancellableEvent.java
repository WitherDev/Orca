package dev.wither.orca.bukkit;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;

public abstract class OrcaCancellableEvent extends OrcaEvent implements Cancellable {

    @Getter @Setter private boolean cancelled;

}
