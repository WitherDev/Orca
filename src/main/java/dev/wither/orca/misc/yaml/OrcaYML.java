package dev.wither.orca.misc.yaml;

import dev.wither.orca.Orca;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * OrcaYML allows you to create a simple manager for a single .yml file.
 */
public abstract class OrcaYML {

    /**
     * The actual Java File object that should be minimally interacted with.
     */
    @Getter
    private final File file;

    /**
     * The FileConfiguration that can be accessed to interact with the YML inside.
     */
    @Getter private FileConfiguration yaml;

    /**
     * @param plugin The plugin creating the .yaml. Used to access the data folder.
     * @param name name of the yml file.
     */
    public OrcaYML(JavaPlugin plugin, String name) {

        if (!name.contains(".yml")) name = name + ".yml";

        if (!plugin.getDataFolder().exists()) {

            boolean success = plugin.getDataFolder().mkdirs();

        }

        file = new File(plugin.getDataFolder(), name);

        if (file.exists()) {

            try {

                boolean success = file.createNewFile();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        reload();

    }

    /**
     * @param folder The folder where the yaml should be created.
     * @param name name of the yml file.
     */
    public OrcaYML(File folder, String name) {

        if (!name.contains(".yml")) name = name + ".yml";

        if (!folder.exists()) {

            boolean success = folder.mkdirs();

        }
        if (!folder.isDirectory()) throw new IllegalArgumentException("File given for OrcaYAML must be a folder.");

        file = new File(folder, name);

        if (file.exists()) {

            try {

                boolean success = file.createNewFile();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        reload();

    }

    /**
     * Save the FileConfiguration to the File object, effectively saving the data inside.
     * Can be run after each modification and built into other methods to ensure each change is logged.
     */
    public void save() {

        try {

            yaml.save(file);

        } catch (IOException e) {

            Orca.console("&cError caught while saving OrcaYaml: " + getClass().getName());
            e.printStackTrace();

        }

    }

    /**
     * Overrides the existing FileConfiguration object with a new one that contains the data of the File.
     * Should be used when the actual .yml is intentionally changed.
     */
    public void reload() {

        yaml = YamlConfiguration.loadConfiguration(file);

    }

}
