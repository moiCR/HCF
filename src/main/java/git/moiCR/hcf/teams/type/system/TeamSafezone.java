package git.moiCR.hcf.teams.type.system;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.teams.Team;
import org.bson.Document;
import org.bukkit.ChatColor;

public class TeamSafezone extends Team {

    public TeamSafezone(String name) {
        super(name, name, ChatColor.GREEN);
    }

    public TeamSafezone(Main instance, Document document) {
        super(instance, document);
    }
}
