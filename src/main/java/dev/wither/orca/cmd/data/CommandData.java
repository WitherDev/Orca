package dev.wither.orca.cmd.data;

import dev.wither.orca.cmd.CommandCenter;
import dev.wither.orca.cmd.OrcaCommand;
import dev.wither.orca.misc.OrcaCaller;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public class CommandData {

    @Getter private static final CommandCenter center = CommandCenter.getCenter();

    @Getter private final String name;
    @Getter private String perm;

    @Getter private final Method method;

    @Getter @Setter private String noPermissionResponse;
    @Getter @Setter private String consoleBlockedResponse;
    @Getter @Setter private String playerBlockedResponse;
    @Getter @Setter private String disabledResponse;

    @Getter private final String description;

    @Getter @Setter private boolean enabled;
    @Getter private final boolean player;
    @Getter private final boolean console;
    /**
     * When true, the method for the command will be executed asynchronously using BukkitScheduler.
     * Otherwise, it will be executed like normal code.
     */
    @Getter private final boolean async;

    public CommandData(Method method, Command command) {

        name = command.name();
        if (!command.perm().equalsIgnoreCase("none")) perm = command.perm();
        enabled = command.enabled();

        description = command.description();
        player = command.player();
        console = command.console();
        async = command.async();
        this.method = method;

        fromCenter();

    }

    private void fromCenter() {

        setNoPermissionResponse(center.getNoPermissionResponse());
        setConsoleBlockedResponse(center.getConsoleBlockedResponse());
        setPlayerBlockedResponse(center.getPlayerBlockedResponse());
        setDisabledResponse(center.getDisabledResponse());

    }

    public String onCommand(CommandSender sender) {

        if (!enabled) return disabledResponse;
        if (perm != null) if (!center.hasPermission(sender, perm, false)) return noPermissionResponse;
        if (sender instanceof Player && !player) return playerBlockedResponse;
        if (sender instanceof ConsoleCommandSender && !console) return playerBlockedResponse;
        return null;

    }

    public void invoke(OrcaCommand target, CommandSender sender, String alias, String[] args) {

        if (async) {

            OrcaCaller.callAsync(CommandCenter.getCenter().getPlugin(), method, target, target, sender, alias, args, this);

        } else {

            OrcaCaller.call(method, target, target, sender, alias, args, this);

        }

    }

}
