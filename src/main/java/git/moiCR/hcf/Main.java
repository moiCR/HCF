package git.moiCR.hcf;

import git.moiCR.hcf.lib.command.CommandManager;
import git.moiCR.hcf.teams.TeamHandler;
import git.moiCR.hcf.teams.claim.ClaimManager;
import git.moiCR.hcf.lib.menu.MenuHandler;
import git.moiCR.hcf.teams.TeamManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    private TeamManager teamManager;
    private ClaimManager claimManager;
    private CommandManager commandManager;

    private MenuHandler menuHandler;
    private TeamHandler teamHandler;

    @Override
    public void onEnable() {
        this.teamManager = new TeamManager(this);
        this.claimManager = new ClaimManager(this);
        this.commandManager = new CommandManager(this);

        this.menuHandler = new MenuHandler(this);
        this.teamHandler = new TeamHandler(this);
    }

    @Override
    public void onDisable() {

    }
}
