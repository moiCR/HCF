package git.moiCR.hcf;

import git.moiCR.hcf.lib.command.CommandManager;
import git.moiCR.hcf.lib.prompt.PromptHandler;
import git.moiCR.hcf.teams.TeamHandler;
import git.moiCR.hcf.teams.claim.ClaimManager;
import git.moiCR.hcf.lib.menu.MenuHandler;
import git.moiCR.hcf.teams.TeamManager;
import git.moiCR.hcf.utils.Manager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class Main extends JavaPlugin {

    private List<Manager> managers;

    private TeamManager teamManager;
    private ClaimManager claimManager;
    private CommandManager commandManager;

    private MenuHandler menuHandler;
    private TeamHandler teamHandler;
    private PromptHandler promptHandler;

    @Override
    public void onEnable() {
        this.managers = new ArrayList<>();
        this.teamManager = new TeamManager(this);
        this.claimManager = new ClaimManager(this);
        this.commandManager = new CommandManager(this);

        this.menuHandler = new MenuHandler(this);
        this.teamHandler = new TeamHandler(this);
        this.promptHandler = new PromptHandler(this);

        this.managers.forEach(Manager::load);
    }

    @Override
    public void onDisable() {
        this.managers.forEach(Manager::unload);
    }
}
