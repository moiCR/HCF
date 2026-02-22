package git.moiCR.hcf.profile.listener;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.profile.Profile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
            profile.updateName();
            return;
        }

        var newProfile = new Profile(player);
        plugin.getProfileManager().addProfile(newProfile);
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        var player = event.getPlayer();
        var profile = plugin.getProfileManager().findProfile(player);

        if (profile == null) return;

        plugin.getStorageManager().getStorage().saveProfile(profile, true);
    }

}
