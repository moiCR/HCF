package git.moiCR.hcf.teams.type.system;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.teams.Team;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.ChatColor;

@Getter
@Setter
public class TeamRoad extends Team {

    public TeamRoad(String name) {
        super(name, name + " Road", ChatColor.GOLD);
    }

    public TeamRoad(Main instance, Document document) {
        super(instance, document);
    }
}
