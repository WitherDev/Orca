package dev.wither.orca.bukkit.revamps;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

/**
 * Smart version of the Bukkit Location with implemented functionality.
 *
 * Multiple storage mechanisms are added for ease-of-use.
 */
public class OrcaLocation {

    @JsonProperty("world")
    @Getter @Setter private String world;

    @JsonProperty("x")
    @Getter @Setter private double x;

    @JsonProperty("y")
    @Getter @Setter private double y;

    @JsonProperty("z")
    @Getter @Setter private double z;

    @JsonProperty("yaw")
    @Getter @Setter private float yaw;

    @JsonProperty("pitch")
    @Getter @Setter private float pitch;

    public OrcaLocation fromBukkit(@NotNull Location location) {

        world = location.getWorld().getName();
        x = location.getX();
        y = location.getY();
        z = location.getZ();
        yaw = location.getYaw();
        pitch = location.getPitch();

        return this;

    }

    public OrcaLocation fromString(String string) {

        String[] data = string.split("!");

        world = data[0];
        x = Double.parseDouble(data[1]);
        y = Double.parseDouble(data[2]);
        z = Double.parseDouble(data[3]);

        if (data.length == 4) return this;

        yaw = Float.parseFloat(data[4]);
        pitch = Float.parseFloat(data[5]);

        return this;

    }

    public Location toBukkit() {

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);

    }

    @Override
    public String toString() {

        return world +"!" + x + "!" + y + "!" + z + "!" + yaw + "!" + pitch + "!";

    }

    public World getBukkitWorld() {

        return Bukkit.getWorld(world);

    }

    /**
     * Creates a nicely-formatted String that only provides the world name and x/y/z integer values.
     * @return the formatted String.
     */
    public String formatBlockLocation() {

        return world + ", " + (int)x + ", " + (int)y + ", " + (int)z;

    }

}
