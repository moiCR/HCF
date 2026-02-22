package git.moiCR.hcf.teams.type.system;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.teams.Team;
import org.bson.Document;
import org.bukkit.ChatColor;

public class TeamEvent extends Team {

    public TeamEvent(String name) {
        super(name, name + " Event", ChatColor.LIGHT_PURPLE);
    }

    public TeamEvent(Main instance, Document document) {
        super(instance, document);
    }
}
