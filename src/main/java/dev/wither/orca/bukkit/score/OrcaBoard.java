package dev.wither.orca.bukkit.score;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;


// TODO
public class OrcaBoard {

    @Getter private final Player player;

    @Getter private final Scoreboard board;
    @Getter private final Objective objective;

    @Getter @Setter private OrcaContents content;

    public OrcaBoard(Player player) {

        this.player = player;
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = board.getObjective(DisplaySlot.SIDEBAR);

    }

    public void update() {

        int i = 0;
        for (String string : content.getContent()) {

            objective.getScore(string).setScore(i);
            i++;

        }

    }

}
