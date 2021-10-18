package dev.wither.orca.cmd.var.integrated;

import com.avaje.ebean.validation.NotNull;
import dev.wither.orca.cmd.var.CommandVariable;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * OnlinePlayerCommandVariable is integrated into the CommandCenter.
 */
public class PlayerCommandVariable extends CommandVariable {

    /**
     * @param sender the CommandSender requesting tab completion.
     * @return every online player visible to the sender.
     */
    @Override
    public @NotNull List<String> getCompletion(CommandSender sender) {

        List<String> list = new ArrayList<>();

        if (sender instanceof Player) {

            Player pSender = (Player) sender;
            for (Player player : Bukkit.getOnlinePlayers()) {

                if (!pSender.canSee(player)) continue;
                list.add(player.getName());

            }

        } else {

            for (Player player : Bukkit.getOnlinePlayers()) {

                list.add(player.getName());

            }

        }

        return list;

    }

}
