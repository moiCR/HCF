package git.moiCR.hcf.teams.menu;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lib.menu.Menu;
import git.moiCR.hcf.lib.menu.button.Button;
import git.moiCR.hcf.teams.Team;
import org.bukkit.entity.Player;

import java.util.Map;

public class TeamSystemEditorMenu extends Menu {

    private final Team team;

    public TeamSystemEditorMenu(Main instance, Player player, Team team) {
        super(instance, player, false);
        this.team = team;
    }

    @Override
    public String getTitle() {
        return "Editing " + team.getName();
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Map<Integer, Button> getButtons() {
        return Map.of();
    }
}
