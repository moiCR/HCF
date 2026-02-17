package git.moiCR.hcf.profile.listener;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.profile.HCFProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ProfileListener implements Listener {

    private final Main plugin;

    public ProfileListener(Main plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        var player = event.getPlayer();
        var profile = plugin.getProfileManager().findProfile(player);
        if (profile != null){
            return;
        }
        profile = new HCFProfile(player);
        plugin.getProfileManager().addProfile(profile);
    }

}
