package dev.wither.orca;

import dev.wither.orca.cmd.CommandCenter;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Orca extends JavaPlugin {

    @Getter private static Orca plugin;
    @Getter private static CommandCenter center;

    @Override
    public void onEnable() {

        orcaConsole("&3Your server is using the Orca API! Please let Wither#1337 (on Discord) know about any bugs.");

    }

    // ─────── ─────── ─────── ───────

    public static String addColor(String string) {

        return ChatColor.translateAlternateColorCodes('&', string);

    }
    public static String[] addColor(String[] strings) {

        List<String> colorized = new ArrayList<>();

        for (String string : strings) {

            colorized.add(addColor(string));

        }

        return colorized.toArray(strings);

    }
    public static List<String> addColor(List<String> strings) {

        List<String> newStrings = new ArrayList<>();

        for (String string : strings) {

            newStrings.add(Orca.addColor(string));

        }

        return newStrings;

    }

    public static void console(String string) {

        Bukkit.getConsoleSender().sendMessage(addColor(string));

    }
    public static void consoleAnnounce(String... announcement) {

        console("&8───────────────");
        for (String msg : announcement) {

            console(msg);

        }
        console("&8───────────────");

    }

    public static void orcaConsole(String string) {

        console("&b&lOrca &8» &7" + string);

    }
    public static void orcaAnnounce(String... announcement) {

        console("&8───────────────");
        for (String msg : announcement) {

            orcaConsole(msg);

        }
        console("&8───────────────");

    }

    public static OfflinePlayer fromUUID(String uuid) {

        return Bukkit.getOfflinePlayer(UUID.fromString(uuid));

    }

}
