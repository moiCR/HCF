package git.moiCR.hcf.teams.claim.listener;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.api.PlayerChangeClaimEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

public class ClaimListener implements Listener {

    private final Main plugin;

    public ClaimListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        var from = event.getFrom();
        var to = event.getTo();
        var player = event.getPlayer();

        if (from.getBlockX() == to.getBlockX()
                && from.getBlockY() == to.getBlockY()
                && from.getBlockZ() == to.getBlockZ()) {
            return;
        }

        var teamFrom = plugin.getClaimManager().getTeamAt(from);
        var teamTo = plugin.getClaimManager().getTeamAt(to);

        if (Objects.equals(teamFrom, teamTo)){
            return;
        }



        var newEvent = new PlayerChangeClaimEvent(event.getPlayer(), teamFrom, teamTo);
        plugin.getServer().getPluginManager().callEvent(newEvent);
        if (!newEvent.isCancelled()){
            return;
        }

        event.setTo(from);
    }
}
