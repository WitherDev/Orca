package dev.wither.orca.cmd;

import dev.wither.orca.Orca;
import dev.wither.orca.cmd.data.Command;
import dev.wither.orca.cmd.var.CommandVariable;
import dev.wither.orca.cmd.var.integrated.PlayerCommandVariable;
import dev.wither.orca.cmd.var.integrated.WorldCommandVariable;
import dev.wither.orca.misc.annotions.NotNull;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandCenter {

    @Getter private static CommandCenter center;

    @Getter private final HashMap<String, CommandVariable> variables;
    @Getter private final HashMap<String, OrcaCommand> commands;

    @Getter private final JavaPlugin plugin;

    @Getter @Setter private String noPermissionResponse;
    @Getter @Setter private String consoleBlockedResponse;
    @Getter @Setter private String playerBlockedResponse;
    @Getter @Setter private String disabledResponse;
    @Getter @Setter private String errorThrownResponse;

    @Getter @Setter private String defaultDescription;

    /**
     * Each plugin using Orca should create one, and only one, Command Center that can then be accessed statically with this class.
     * Extending CommandCenter and overriding methods to adjust to a custom environment is a valid way to use this API.
     */
    public CommandCenter(JavaPlugin plugin) {

        center = this;
        this.plugin = plugin;

        variables = new HashMap<>(8);
        commands = new HashMap<>(8);

        registerIntegrated();

        setDefaultResponses();
        validateResponses();

    }

    /**
     * When a plugin is ready to register each command, this method should be run once, and only once.
     */
    public void register() {

        ClassGraph graph = new ClassGraph();

        String[] group = plugin.getClass().getPackage().getName().split("\\.");
        graph.acceptPackages(group[0] + "." + group[1]).enableClassInfo().enableAnnotationInfo();

        ScanResult result = graph.scan();

        List<Class<?>> commandClasses = result.getClassesWithAnnotation(Command.class.getName()).loadClasses();

        try {

            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            field.setAccessible(true);
            CommandMap map = (CommandMap) field.get(Bukkit.getServer());
            field.setAccessible(false);
            if (map == null) {
                Orca.consoleAnnounce("&c&lOrca Command API &8» &7CommandMap is null. Version error?");
                return;
            }

            int total = 0;

            for (Class<?> clazz : commandClasses) {

                if (clazz.getDeclaredAnnotation(Command.class).ignore()) continue;

                try {

                    if (OrcaCommand.class.isAssignableFrom(clazz)) {

                        Object instance = clazz.newInstance();
                        OrcaCommand command = (OrcaCommand) instance;

                        if (command.isHidden()) continue;

                        map.register(plugin.getName(), command.getBukkitCommand());
                        commands.put(command.getData().getName(), command);

                        total++;

                    } else {

                        Orca.orcaConsole("&cClass " + clazz.getName() + " was incorrectly annotated with @Command.");

                    }

                } catch (InstantiationException | IllegalAccessException e) {

                    Orca.orcaConsole("&cFailed to load OrcaCommand! &8(" + clazz.getName()+ ")");
                    e.printStackTrace();

                }

            }

            Orca.consoleAnnounce("&b&lOrca &8» &3CommandCenter was loaded for plugin " + plugin.getName() + ".", "&8Loaded " + total + " OrcaCommands!" );

        } catch (NoSuchFieldException | IllegalAccessException e) {

            Orca.orcaConsole("&cError was thrown while attempting to register the CommandCenter!");
            e.printStackTrace();

        }

    }

    /**
     * Can be overridden to change the default response values.
     * When a response is not set by this method, a second method runs to set it to Orca's default response.
     */
    public void setDefaultResponses() {

        noPermissionResponse = "&cYou do not have the permission to use that.";
        errorThrownResponse = "&cError was thrown in the background. Please let an admin know!";
        consoleBlockedResponse = "&cThe command is blocked for console.";
        playerBlockedResponse = "&cThe command is blocked for players.";
        disabledResponse = "&cThe command is disabled.";

    }
    /**
     * Ensure that each response was given a default value, otherwise set it to the Orca default response.
     */
    private void validateResponses() {

        if (noPermissionResponse == null) noPermissionResponse = "&cYou do not have the permission to use that.";
        if (errorThrownResponse == null) errorThrownResponse = "&cError was thrown in the background. Please let an admin know!";
        if (consoleBlockedResponse == null) consoleBlockedResponse = "&cThe command is blocked for console.";
        if (playerBlockedResponse == null) playerBlockedResponse = "&cThe command is blocked for players.";
        if (disabledResponse == null) disabledResponse = "&cThe command is disabled.";

    }

    /**
     * Register every CommandVariable that is built into the Command API.
     */
    public void registerIntegrated() {

        registerVariable("<player>", new PlayerCommandVariable());
        registerVariable("<world>", new WorldCommandVariable());

    }

    /**
     * Verify that the variable can be added, then register it to the CommandCenter.
     * @param var the String equivalent of the variable.
     * @param completer the implementation of CommandVariable that provides the completion.
     */
    public void registerVariable(String var, CommandVariable completer) {

        if (!isVariableSyntax(var)) throw new IllegalArgumentException("Please ensure you add variables formatted as <variable>. " + var + " is illegal.");
        if (!isAvailable(var)) throw new IllegalArgumentException("Please ensure you add variables that are unique. " + var + " is taken.");

        variables.put(var, completer);

    }

    /**
     * Verify whether a sender has a specific permission or not.
     * @param sender the sender of a command.
     * @param value the permission value that should be met.
     * @param ignoreOperator whether or not the algorithm should consider the operator status of the sender.
     * @return true if the player meets the permission value, false otherwise.
     */
    public boolean hasPermission(CommandSender sender, String value, boolean ignoreOperator) {

       // Set<PermissionAttachmentInfo> perms = sender.getEffectivePermissions();

        return sender.hasPermission(value);

    }

    /**
     * @param sender the sender of a command.
     * @param variable the variable indicated in the name.
     * @return the potential Strings that match that variable.
     */
    public @NotNull List<String> getCompletion(@NotNull CommandSender sender, @NotNull String variable) {

        List<String> completed = new ArrayList<>();

        CommandVariable var = variables.get(variable);
        if (var != null) completed = var.getCompletion(sender);

        return completed;

    }

    /**
     * @param sender the sender of a command.
     * @param variable the variable indicated in the name.
     * @param current what has been typed so far.
     * @return the Strings that should match the variable.
     */
    public @NotNull List<String> getSmartCompletion(@NotNull CommandSender sender, @NotNull String variable, @NotNull String current) {

        List<String> completed = new ArrayList<>();

        CommandVariable var = variables.get(variable);
        if (var != null) completed = var.smartCompletion(sender, current);

        return completed;

    }

    /**
     * Whether or not a String matches the <variable> syntax.
     * @param variable the String being checked.
     * @return true if it matches, false otherwise.
     */
    public boolean isVariableSyntax(String variable) {

        return variable.startsWith("<") && variable.endsWith(">");

    }

    /**
     * Whether a variable is not found in the current dataset or not.
     * @param variable the String being checked.
     * @return true if the variable is not found in the dataset, false otherwise.
     */
    public boolean isAvailable(String variable) {

        return !variables.containsKey(variable);

    }

    /**
     * Orca will create a custom /(command) help message for your command.
     * The message is dynamically changed based on whether a user can/can not use the command.
     * @param command the command for which the message should be generated.
     * @param sender the user who will see the message.
     * @param theme the primary color theme for the message.
     * @return the message that can be shown to the player.
     */
    public List<String> createHelpMessage(OrcaCommand command, CommandSender sender, ChatColor theme) {

        return new ArrayList<>();

    }

    public String createUsageMessage(String name, Command command) {

        return "&cPlease run&8: &7" + name + " " + command.name();

    }

}
