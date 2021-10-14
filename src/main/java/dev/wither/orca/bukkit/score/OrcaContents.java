package dev.wither.orca.bukkit.score;

import dev.wither.orca.Orca;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//TODO

/**
 * OrcaContents is simply an ArrayList of Strings that can be easily modified for lore, sidebar, or more.
 */
public class OrcaContents {

    @Getter @Setter private List<String> content;

    public OrcaContents() {

        content = new ArrayList<>();

    }

    public OrcaContents(List<String> content) {

        this.content = content;

    }

    public OrcaContents format() {

        for (int i = 0; i != content.size(); i++) {

            content.set(i, Orca.addColor(content.get(i)));

        }

        return this;

    }

}
