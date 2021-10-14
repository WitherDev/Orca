package dev.wither.orca.bukkit.blocks;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;

import java.util.Iterator;
import java.util.Set;

/**
 * OrcaBlockContainer implementation that holds an array of Blocks without any definite shape and can be mutated at any time.
 * Minimal functionality is provided by this class.
 */
public class OrcaBlockHolder implements OrcaBlockContainer {

    @Getter @Setter private Set<Block> blockSet;

    public OrcaBlockHolder(Set<Block> blocks) {

        this.blockSet = blocks;

    }

    @Override
    public Iterator<Block> getBlocks() {
        return blockSet.iterator();
    }

}
