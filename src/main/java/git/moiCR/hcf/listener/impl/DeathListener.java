package git.moiCR.hcf.listener.impl;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.profile.death.Death;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

@AllArgsConstructor
public class DeathListener implements Listener {

    private final Main plugin;

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        event.setDeathMessage("");
        var player = event.getEntity();
        var profile = plugin.getProfileManager().findProfile(player);

        if (profile != null){
            profile.setDeaths(profile.getDeaths() + 1);
            profile.setKillStreak(0);
            profile.getDeathsList().add(
                    new Death(
                            player.getLocation(),
                            player.getInventory().getContents(),
                            player.getInventory().getArmorContents()
                    ));
        }

        var deathTeam = plugin.getTeamManager().getTeamByPlayer(player);
        if (deathTeam != null){
            deathTeam.setDtr(deathTeam.getDtr() - 1);
            deathTeam.freeze(60 * 60 * 1000L);
            deathTeam.setPoints(Math.max(0, deathTeam.getPoints() - 5));

            deathTeam.broadcast(LangHandler.INSTANCE.getMessage(null, Lang.MEMBER_DEATH)
                    .replace("%player%", player.getName()));

            deathTeam.broadcast(LangHandler.INSTANCE.getMessage(null, Lang.TEAM_FROZEN)
                    .replace("%dtr%", String.format("%.2f", deathTeam.getDtr()))
                    .replace("%time%", "1h"));

            if (deathTeam.isRaidable()){
                deathTeam.broadcast(LangHandler.INSTANCE.getMessage(null, Lang.TEAM_RAIDABLE));
            }

        }

        var killer = player.getKiller();

        if (killer == null){
            return;
        }

        var killerProfile = plugin.getProfileManager().findProfile(killer);

        if (killerProfile == null){
            return;
        }

        killerProfile.setKills(killerProfile.getKills() + 1);
        killerProfile.setKillStreak(killerProfile.getKillStreak() + 1);

        var killerTeam = plugin.getTeamManager().getTeamByPlayer(killer);

        if (killerTeam == null){
            return;
        }

        killerTeam.setPoints(killerTeam.getPoints() + 10);
    }

}
