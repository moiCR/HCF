package git.moiCR.hcf.teams;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.api.PlayerChangeClaimEvent;
import git.moiCR.hcf.utils.Handler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

public class TeamHandler extends Handler {

    public TeamHandler(Main instance) {
        super(instance);
    }

    @Override
    public Listener getEvents() {
        return new Listener() {
            @EventHandler
            public void onMove(PlayerMoveEvent event){
                var from = event.getFrom();
                var to = event.getTo();

                if (from.getBlockX() == to.getBlockX()
                        && from.getBlockY() == to.getBlockY()
                        && from.getBlockZ() == to.getBlockZ()) {
                    return;
                }

                var teamFrom = getInstance().getClaimManager().getTeamAt(from);
                var teamTo = getInstance().getClaimManager().getTeamAt(to);

                if (Objects.equals(teamFrom, teamTo)){
                    return;
                }

                var newEvent = new PlayerChangeClaimEvent(event.getPlayer(), teamFrom, teamTo);
                getInstance().getServer().getPluginManager().callEvent(newEvent);
                if (!newEvent.isCancelled()){
                    return;
                }

                event.setTo(from);
            }
        };
    }
}
