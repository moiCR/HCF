package git.moiCR.hcf.teams.menu.edit;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lib.menu.Menu;
import git.moiCR.hcf.lib.menu.button.Button;
import git.moiCR.hcf.teams.type.system.TeamRoad;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TeamRoadEditMenu extends Menu {

    private final TeamRoad road;

    public TeamRoadEditMenu(Main instance, Player player, TeamRoad road) {
        super(instance, player, false);
        this.road = road;
    }

    @Override
    public String getTitle() {
        return getPlayer().spigot().getLocale().startsWith("es")
                ? "Editando: " + road.getDisplayName()
                : "Editing: " + road.getDisplayName();
    }

    @Override
    public int getSize() {
        return 4*9;
    }

    @Override
    public Map<Integer, Button> getButtons() {
        return new HashMap<>();
    }
}
