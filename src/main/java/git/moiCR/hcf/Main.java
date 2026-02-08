package git.moiCR.hcf;

import git.moiCR.hcf.claim.ClaimManager;
import git.moiCR.hcf.teams.TeamManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    private TeamManager teamManager;
    private ClaimManager claimManager;

    @Override
    public void onEnable() {
        this.teamManager = new TeamManager(this);
        this.claimManager = new ClaimManager(this);
    }

    @Override
    public void onDisable() {

    }
}
