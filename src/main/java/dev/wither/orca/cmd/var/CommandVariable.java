package dev.wither.orca.cmd.var;

import com.avaje.ebean.validation.NotNull;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class CommandVariable {

    /**
     * @param sender the CommandSender requesting tab completion.
     * @return completion that should be shown to the user.
     */
    public abstract @NotNull List<String> getCompletion(CommandSender sender);

    /**
     * @param sender the CommandSender requesting tab completion.
     * @param current the String that the sender has already typed.
     * @return completion shown to the user, filtered based on what has been already typed.
     */
    public @NotNull List<String> smartCompletion(CommandSender sender, String current) {

        List<String> completion = getCompletion(sender);
        completion.removeIf(s -> !s.startsWith(current));

        return completion;

    }

}
