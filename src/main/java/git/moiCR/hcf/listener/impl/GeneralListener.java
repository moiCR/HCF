package git.moiCR.hcf.listener.impl;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.api.events.PlayerChangeClaimEvent;
import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.teams.type.system.TeamSafezone;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

@AllArgsConstructor
public class GeneralListener implements Listener {

    private final Main plugin;

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChangeClaim(PlayerChangeClaimEvent event){
        var toTeam = event.getTo();
        var fromTeam = event.getFrom();
        var player = event.getPlayer();

        player.sendMessage(LangHandler.INSTANCE.getMessage(event.getPlayer(), Lang.FROM_CLAIM)
                .replace("%team%", fromTeam.getColor() + fromTeam.getName())
                .replace("%deathban%", fromTeam instanceof TeamSafezone ?
                        LangHandler.INSTANCE.getMessage(player, Lang.NON_DEATH_BAN) :
                        LangHandler.INSTANCE.getMessage(player, Lang.DEATH_BAN)));

        player.sendMessage(LangHandler.INSTANCE.getMessage(event.getPlayer(), Lang.TO_CLAIM)
                .replace("%team%", toTeam.getColor() + toTeam.getName())
                .replace("%deathban%", toTeam instanceof TeamSafezone ?
                        LangHandler.INSTANCE.getMessage(player, Lang.NON_DEATH_BAN) :
                        LangHandler.INSTANCE.getMessage(player, Lang.DEATH_BAN)));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCreatureSpawn(CreatureSpawnEvent event){
        if (event.getEntity() instanceof Player){
            return;
        }

        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER){
            return;
        }

        event.setCancelled(true);
    }

}
