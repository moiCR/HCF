package git.moiCR.hcf.teams.type.system;

import git.moiCR.hcf.teams.Team;
import org.bukkit.ChatColor;

public class TeamEvent extends Team {

    public TeamEvent(String name, String displayName) {
        super(name, displayName, ChatColor.LIGHT_PURPLE);
    }
}
