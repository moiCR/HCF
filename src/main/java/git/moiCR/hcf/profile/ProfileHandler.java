package git.moiCR.hcf.profile;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lib.Handler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ProfileHandler extends Handler {

    public ProfileHandler(Main instance) {
        super(instance);
    }

    @Override
    public Listener getEvents() {
        return new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent event){
                var player = event.getPlayer();
                var profile = getInstance().getProfileManager().findProfile(player);
                if (profile == null){
                    profile = new HCFProfile(player);
                    getInstance().getProfileManager().addProfile(profile);
                }
            }
        };
    }
}
