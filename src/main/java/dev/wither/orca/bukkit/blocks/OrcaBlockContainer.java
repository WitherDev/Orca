package dev.wither.orca.bukkit.blocks;

import com.avaje.ebean.validation.NotNull;
import org.bukkit.block.Block;

import java.util.Iterator;

/**
 * OrcaBlockContainer is a simple interface for classes that represent an array of in-game Blocks.
 */
public interface OrcaBlockContainer {

    /**
     * @return Iterator containing every block.
     */
    @NotNull Iterator<Block> getBlocks();

}
