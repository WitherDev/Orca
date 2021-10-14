package dev.wither.orca.cmd;

import dev.wither.orca.Orca;
import dev.wither.orca.cmd.data.Command;
import dev.wither.orca.cmd.data.CommandData;
import dev.wither.orca.cmd.data.CommandNode;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class OrcaCommand {

    @Getter private org.bukkit.command.Command bukkitCommand;

    @Getter private CommandData data;
    @Getter private CommandNode node;

    @Getter private boolean hidden;

    public OrcaCommand() {

        Command command = getClass().getDeclaredAnnotation(Command.class);
        if (command == null) throw new RuntimeException("Command annotation is missing on an OrcaCommand! " + getClassName());

        if (command.ignore()) {

            hidden = true;
            return;

        }
        data = new CommandData(null, command);
        node = new CommandNode(null);

        bukkitCommand = new org.bukkit.command.Command(command.name()) {

            @Override
            public boolean execute(CommandSender sender, String alias, String[] args) {

                try {

                    onBukkit(sender, alias, args);

                } catch (Exception e) {

                    respond(sender, CommandCenter.getCenter().getErrorThrownResponse());
                    e.printStackTrace();

                }

                return true;

            }

            @Override
            public List<String> tabComplete(CommandSender sender, String label, String[] args) {

                try {

                    return onTab(sender, args);

                } catch (Exception e) {

                    respond(sender, CommandCenter.getCenter().getErrorThrownResponse());
                    e.printStackTrace();
                    return new ArrayList<>();

                }

            }

        };

        try {

            register();

        } catch (Exception e) {

            Orca.orcaConsole("&cError registering an OrcaCommand! " + getClassName());
            e.printStackTrace();
            return;

        }

        hidden = false;

    }

    public void register() {

        Method[] methods = getClass().getDeclaredMethods();

        for (Method method : methods) {

            Command cmd = method.getDeclaredAnnotation(Command.class);
            if (cmd == null) continue;

            CommandData data = new CommandData(method, cmd);

            createSub(cmd.name(), data);
            for (String string : cmd.aliases()) {

                createSub(string, data);

            }

        }

    }

    private void createSub(String value, CommandData data) {

        String[] keys = value.split(" ");

        CommandNode pointer = node;

        for (String key : keys) {

            pointer = pointer.append(key);

        }

        pointer.setData(data);

    }

    /**
     * method will be executed when no subcommand matches the content, including when content is empty.
     * @param sender sender of the command.
     * @param args the arguments that the player entered.
     * @param alias the exact String the player typed for the command name.
     */
    public abstract void onCommand(CommandSender sender, String alias, String[] args);

    /**
     * Adapter between Bukkit's Command and OrcaCommand.
     * @param sender sender sending the command.
     * @param alias exact String typed.
     * @param args Strings after the alias that were typed.
     */
    protected void onBukkit(CommandSender sender, String alias, String[] args) {

        String response = data.onCommand(sender);
        if (response != null) {
            respond(sender, response);
            return;
        }

        if (args.length == 0) {

            onCommand(sender, alias, args);
            return;

        }

        CommandNode next = node;
        CommandNode last = node;
        for (String arg : args) {

            next = next.getNode(arg);
            if (next == null) break;
            last = next;

        }

        if (last.equals(node)) {

            onCommand(sender, alias, args);
            return;

        }
        if (last.getData() == null) {

            while (last.getData() == null) {

                if (last.getParent() != null) last = last.getParent(); else {

                    onCommand(sender, alias, args);
                    return;

                }

            }

            if (last.getData() == null) {

                onCommand(sender, alias, args);
                return;

            }

        }

        // CommandNode last should not be the master node, and should have a CommandData, by now

        response = last.getData().onCommand(sender);
        if (response != null) {

            respond(sender, response);
            return;

        }
        last.getData().invoke(this, sender, alias, args);

    }

    /**
     * Adapter between Bukkit's TabCompleter and OrcaCommand.
     * @param sender sender sending the command.
     * @param args Strings after the alias that were typed.
     */
    protected List<String> onTab(CommandSender sender, String[] args) {

        if (args.length == 0) return node.getCompletion(sender, "");

        String current = args[args.length-1];

        CommandNode next = node;

        for (int i = 0; i != args.length-1; i++) {

            next = next.getNode(args[i]);
            if (next == null) return new ArrayList<>();

        }

        return next.getCompletion(sender, current);

    }

    /**
     * Sends the CommandSender a message with ChatColor replacement.
     * Simple utility method.
     * @param sender the sender of the command.
     * @param response response to be sent.
     */
    public void respond(CommandSender sender, String response) {

        sender.sendMessage(Orca.addColor(response));

    }
    public void respond(CommandSender sender, String[] response) {

        for (String string : response) {

            respond(sender, string);

        }

    }
    public void respond(CommandSender sender, List<String> response) {

        for (String string : response) {

            respond(sender, string);

        }

    }

    public boolean isOnlinePlayer(String name) {

        return Bukkit.getPlayer(name) != null;

    }
    public Player getOnlinePlayer(String name) {

        return Bukkit.getPlayer(name);

    }
    public boolean isPlayer(String name) {

        return Bukkit.getOfflinePlayer(name) != null;

    }
    public OfflinePlayer getPlayer(String name) {

        return Bukkit.getOfflinePlayer(name);

    }

    public boolean isNumber(String number) {

        try {

            double d = Double.parseDouble(number);
            return true;

        } catch (Exception e) {

            return false;

        }

    }
    public double getNumber(String number) {

        return Double.parseDouble(number);

    }
    public boolean isInteger(String integer) {

        try {

            int i = Integer.parseInt(integer);
            return true;

        } catch (Exception e) {

            return false;

        }

    }
    public int getInteger(String integer) {

        return Integer.parseInt(integer);

    }

    public boolean isWorld(String world) {

        return Bukkit.getWorld(world) != null;

    }
    public World getWorld(String world) {

        return Bukkit.getWorld(world);

    }

    /**
     * Build a single String from an initial point in an array.
     * @param input array to build from.
     * @param start index from which the building will begin.
     * @return single String built from the array.
     */
    public String buildFromArray(String[] input, int start) {

        StringBuilder builder = new StringBuilder();

        for (int i = start; i != input.length; i++) {

            builder.append(" ");
            builder.append(input[i]);

        }

        return builder.toString().trim();

    }

    /**
     * Add a description to the underlying Bukkit Command. Automatically adjusts color code.
     * @param description the description to be given.
     */
    public void setDescription(String description) {

        getBukkitCommand().setDescription(Orca.addColor(description));

    }

    private String getClassName() {

        return "&8(" + getClass().getSimpleName() + ")";

    }

}
