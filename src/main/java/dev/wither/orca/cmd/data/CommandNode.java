package dev.wither.orca.cmd.data;

import dev.wither.orca.cmd.CommandCenter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Every CommandNode is created with a HashMap (initial capacity of 2) for branching nodes,
 */
public class CommandNode {

    @Getter private final CommandNode parent;
    @Getter private final HashMap<String, CommandNode> nodes;

    @Getter @Setter private CommandData data;

    public CommandNode(CommandNode parent) {

        this.parent = parent;
        nodes = new HashMap<>(3);

    }

    public CommandNode append(String key) {

        CommandNode node = nodes.get(key);
        if (node == null) {

            node = new CommandNode(this);
            nodes.put(key, node);

        }

        return node;

    }

    /**
     * Find a matching CommandNode based on the key, or a command variable node if available.
     * @param key the key for the node.
     */
    public CommandNode getNode(String key) {

        CommandNode var = null;

        for (Map.Entry<String, CommandNode> entry : nodes.entrySet()) {

            if (entry.getKey().equalsIgnoreCase(key)) return entry.getValue();
            if (isVariableSyntax(entry.getKey())) var = entry.getValue();

        }

        return var;

    }

    /**
     * Whether or not a String matches the <variable> syntax.
     * @param variable the String being checked.
     * @return true if it matches, false otherwise.
     */
    public boolean isVariableSyntax(String variable) {

        return variable.startsWith("<") && variable.endsWith(">");

    }

    public List<String> getCompletion(CommandSender sender, String current) {

        List<String> completed = new ArrayList<>();

        for (Map.Entry<String, CommandNode> entry : nodes.entrySet()) {

            if (entry.getValue().getData() != null) {

                String response = entry.getValue().getData().onCommand(sender);
                if (response != null) continue;

            }

            if (isVariableSyntax(entry.getKey())) {

                completed.addAll(CommandCenter.getCenter().getSmartCompletion(sender, entry.getKey(), current));
                continue;

            }

            completed.add(entry.getKey());

        }

        return completed;

    }

}
