package dev.wither.orca.cmd.trial;

import dev.wither.orca.cmd.OrcaCommand;
import dev.wither.orca.cmd.data.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "trial", ignore = true)
public class TrialCommand extends OrcaCommand {

    @Override
    public void onCommand(CommandSender sender, String alias, String[] args) {

        respond(sender, "&7Please use &c/trial feed &7to feed yourself.");

    }

    @Command(name = "feed", perm = "trial.feed", console = false)
    public void feedSender(CommandSender sender) {

        Player player = (Player) sender;
        player.setFoodLevel(20);

    }

    @Command(name = "show me blocks")
    public void showMeBlocks(CommandSender sender) {



    }

    @Command(name = "yeet <player>")
    public void yeet() {



    }

}
