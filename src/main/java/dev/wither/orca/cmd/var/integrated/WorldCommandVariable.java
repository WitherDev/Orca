package dev.wither.orca.cmd.var.integrated;

import com.avaje.ebean.validation.NotNull;
import dev.wither.orca.cmd.var.CommandVariable;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * WorldCommandVariable is integrated into the CommandCenter.
 */
public class WorldCommandVariable extends CommandVariable {

    /**
     * @param sender the CommandSender requesting tab completion.
     * @return every world that Bukkit can see.
     */
    @Override
    public @NotNull List<String> getCompletion(CommandSender sender) {
        List<String> list = new ArrayList<>();

        for (World world : Bukkit.getWorlds()) {

            list.add(world.getName());

        }

        return list;

    }

}
