package git.moiCR.hcf;

import git.moiCR.hcf.teams.claim.ClaimManager;
import git.moiCR.hcf.lib.menu.MenuHandler;
import git.moiCR.hcf.teams.TeamManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    private TeamManager teamManager;
    private ClaimManager claimManager;

    private MenuHandler menuHandler;

    @Override
    public void onEnable() {
        this.teamManager = new TeamManager(this);
        this.claimManager = new ClaimManager(this);

        this.menuHandler = new MenuHandler(this);
    }

    @Override
    public void onDisable() {

    }
}
