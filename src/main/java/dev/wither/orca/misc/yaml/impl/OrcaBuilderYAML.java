package dev.wither.orca.misc.yaml.impl;

import dev.wither.orca.Orca;
import dev.wither.orca.misc.yaml.OrcaYML;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.TreeMap;

/**
 * OrcaBuilderYAML is an abstract implementation of OrcaYAML that allows a user to automatically configure the .yml based on a set of values.
 * Especially useful when the
 */
public abstract class OrcaBuilderYAML extends OrcaYML {

    public OrcaBuilderYAML(JavaPlugin plugin, String name, TreeMap<String, Object> values) {

        super(plugin, name);
        build(values);

    }

    private void build(TreeMap<String, Object> values) {

        for (Map.Entry<String, Object> entry : values.entrySet()) {

            try {

                if (!getYaml().contains(entry.getKey())) getYaml().set(entry.getKey(), entry.getValue());

            } catch (Exception e) {

                Orca.console("Value " + entry.getKey() + " threw an exception while being built.");

            }

        }

        save();

    }

}
