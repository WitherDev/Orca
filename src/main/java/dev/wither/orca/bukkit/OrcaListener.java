package dev.wither.orca.bukkit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class marked with OrcaListener is going to be automatically instantiated and registered as a listener.
 * Please note that interference issues may occur
 */
@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.TYPE)
public @interface OrcaListener {


}
