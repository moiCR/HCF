package git.moiCR.hcf;

import git.moiCR.hcf.api.API;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.lib.Manager;
import git.moiCR.hcf.lib.command.CommandManager;
import git.moiCR.hcf.lib.menu.MenuHandler;
import git.moiCR.hcf.lib.prompt.PromptHandler;
import git.moiCR.hcf.listener.ListenerManager;
import git.moiCR.hcf.profile.ProfileManager;
import git.moiCR.hcf.storage.StorageManager;
import git.moiCR.hcf.teams.TeamManager;
import git.moiCR.hcf.teams.claim.ClaimManager;
import git.moiCR.hcf.utils.YmlFile;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class Main extends JavaPlugin {

    private List<Manager> managers;

    private TeamManager teamManager;
    private ClaimManager claimManager;
    private CommandManager commandManager;
    private ProfileManager profileManager;
    private ListenerManager listenerManager;
    private StorageManager storageManager;


    private MenuHandler menuHandler;
    private PromptHandler promptHandler;
    private LangHandler langHandler;

    private YmlFile config;

    @Override
    public void onEnable() {
        try{
            this.managers = new ArrayList<>();
            this.teamManager = new TeamManager(this);
            this.claimManager = new ClaimManager(this);
            this.commandManager = new CommandManager(this);
            this.profileManager = new ProfileManager(this);
            this.listenerManager = new ListenerManager(this);

            this.menuHandler = new MenuHandler(this);
            this.promptHandler = new PromptHandler(this);
            this.langHandler = new LangHandler(this);

            this.config = new YmlFile(this, "config");

            this.storageManager = new StorageManager(this);
            this.managers.forEach(Manager::load);

            new API(this);
        }catch (Exception e){
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        this.managers.forEach(Manager::unload);
    }
}
