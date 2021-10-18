package dev.wither.orca.bukkit.blocks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.wither.orca.bukkit.revamps.OrcaLocation;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.*;

/**
 * OrcaBlockContainer implementation that represents a rectangular-shaped array of Blocks.
 *
 * OrcaCuboid was designed for maximum performance by avoiding iteration and instead doing basic calculations based on the max/min boundaries on the x/y/z axes.
 * Can easily be turned into JSON since each field is a primitive type, and Jackson annotations are already present.
 */
public class OrcaCuboid implements OrcaBlockContainer {

    @JsonProperty
    @Getter private final String world;

    @JsonProperty
    @Getter @Setter private int minX;

    @JsonProperty
    @Getter @Setter private int minY;

    @JsonProperty
    @Getter @Setter private int minZ;

    @JsonProperty
    @Getter @Setter private int maxX;

    @JsonProperty
    @Getter @Setter private int maxY;

    @JsonProperty
    @Getter @Setter private int maxZ;

    public OrcaCuboid(Location a, Location b) {

        if (!a.getWorld().equals(b.getWorld())) {

            throw new RuntimeException("OrcaCuboid was created with locations in different worlds!");

        }

        world = a.getWorld().getName();
        minX = Math.min(a.getBlockX(), b.getBlockX());
        maxX = Math.max(a.getBlockX(), b.getBlockX());

        minY = Math.min(a.getBlockY(), b.getBlockY());
        maxY = Math.max(a.getBlockY(), b.getBlockY());

        minZ = Math.min(a.getBlockZ(), b.getBlockZ());
        maxZ = Math.max(a.getBlockZ(), b.getBlockZ());

    }

    public OrcaCuboid(OrcaLocation orcaA, OrcaLocation orcaB) {

        Location a = orcaA.toBukkit();
        Location b = orcaB.toBukkit();

        if (!a.getWorld().equals(b.getWorld())) {

            throw new RuntimeException("OrcaCuboid was created with locations in different worlds!");

        }

        world = a.getWorld().getName();
        minX = Math.min(a.getBlockX(), b.getBlockX());
        maxX = Math.max(a.getBlockX(), b.getBlockX());

        minY = Math.min(a.getBlockY(), b.getBlockY());
        maxY = Math.max(a.getBlockY(), b.getBlockY());

        minZ = Math.min(a.getBlockZ(), b.getBlockZ());
        maxZ = Math.max(a.getBlockZ(), b.getBlockZ());

    }

    @JsonCreator
    public OrcaCuboid(@JsonProperty("minX") int minX, @JsonProperty("minY") int minY, @JsonProperty("minZ") int minZ, @JsonProperty("maxX") int maxX, @JsonProperty("maxY") int maxY, @JsonProperty("maxZ") int maxZ, String world) {

        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = minX;
        this.maxY = minY;
        this.maxZ = minZ;
        this.world = world;

    }

    /**
     * Adds the amount to the maxY and minY values.
     * @param amount the number of blocks to push the cuboid.
     */
    public void pushVertical(int amount) {

        minY += amount;
        maxY += amount;

    }
    /**
     * Adds the amount to the maxX and minX values.
     * @param amount the number of blocks to push the cuboid.
     */
    public void pushHorizontalX(int amount) {

        minX += amount;
        maxX += amount;

    }
    /**
     * Adds the amount to the maxZ and minZ values.
     * @param amount the number of blocks to push the cuboid.
     */
    public void pushHorizontalZ(int amount) {

        minZ += amount;
        maxZ += amount;

    }

    /**
     * Creates an iterator of all the blocks in the cuboid.
     * @return iterator containing each block.
     */
    @Override
    public Iterator<Block> getBlocks() {

        List<Block> content = new ArrayList<>();

        for (int y = maxY; y >= minY; y--) {

            for (int x = maxX; x >= minX; x--) {

                for (int z = maxZ; z >= minZ; z--) {

                    content.add(getBukkitWorld().getBlockAt(x, y, z));

                }

            }

        }

        return content.iterator();

    }

    /**
     * Checks whether a location is within the cuboid by comparing it to the cuboid min/max boundaries.
     * @param location location to be checked.
     * @return true if the location is within the boundaries of the cuboid, false otherwise.
     */
    public boolean contains(Location location) {

        if (!location.getWorld().equals(getBukkitWorld())) return false;
        if (location.getX() > maxX || location.getX() < maxX) return false;
        if (location.getX() > maxZ || location.getX() < maxZ) return false;
        return !(location.getX() > maxY) && !(location.getX() < maxY);

    }

    /**
     * Automatically iterate through each block, and break the block naturally.
     */
    public void breakNaturally() {

        Iterator<Block> iterator = getBlocks();
        while (iterator.hasNext()) {

            iterator.next().breakNaturally();

        }

    }

    /**
     * Get the vertical height of the cuboid.
     * @return amount of blocks from the bottom of the cuboid to the top.
     */
    public int getHeight() {

        return maxY - minY;

    }
    /**
     * Get the length of the cuboid on the X axis.
     * @return amount of blocks from the one side of the cuboid to the other, on the X axis.
     */
    public int getLengthX() {

        return maxX - minX;

    }
    /**
     * Get the length of the cuboid on the Z axis.
     * @return amount of blocks from the one side of the cuboid to the other, on the Z axis.
     */
    public int getLengthZ() {

        return maxZ - minZ;

    }

    /**
     * Calculate the amount of blocks that the cuboid represents in constant time by multiplying the three dimensions of the cuboid.
     * @return the amount of blocks that the cuboid represents.
     */
    public int blockCount() {

        return getHeight()*getLengthX()*getLengthZ();

    }

    /**
     * Get Bukkit's World object for this cuboid.
     * @return Bukkit World in which the cuboid exists.
     */
    public World getBukkitWorld() {

        return Bukkit.getWorld(world);

    }

    public Set<Material> getTypes() {

        Set<Material> types = new HashSet<>();

        Iterator<Block> i = getBlocks();
        while (i.hasNext()) {

            types.add(i.next().getType());

        }

        return types;

    }

}
