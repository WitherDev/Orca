package dev.wither.orca.bukkit.revamps;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

public class OrcaSound {

    @JsonProperty("location")
    @Getter @Setter private OrcaLocation location;

    @JsonProperty("sound")
    @Getter @Setter private Sound sound;

    @JsonProperty("volume")
    @Getter @Setter private float volume;

    @JsonProperty("pitch")
    @Getter @Setter private float pitch;

    public OrcaSound setup(@NotNull Location location, @NotNull Sound sound) {

        this.location = new OrcaLocation().fromBukkit(location);
        this.sound = sound;
        volume = 3;
        pitch = 3;

        return this;

    }

    public OrcaSound setup(@NotNull Location location, @NotNull Sound sound, float volume) {

        this.location = new OrcaLocation().fromBukkit(location);
        this.sound = sound;
        this.volume = volume;
        pitch = 3;

        return this;

    }

    public void play() {

        location.getBukkitWorld().playSound(location.toBukkit(), sound, volume, pitch);

    }

}
