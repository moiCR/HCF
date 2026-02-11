package git.moiCR.hcf.teams.menu;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lib.menu.Menu;
import git.moiCR.hcf.lib.menu.button.Button;
import org.bukkit.entity.Player;

import java.util.Map;

public class TeamSystemEditorMenu extends Menu {

    public TeamSystemEditorMenu(Main instance, Player player) {
        super(instance, player, false);
    }

    @Override
    public String getTitle() {
        return "";
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
