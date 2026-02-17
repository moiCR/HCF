package git.moiCR.hcf.listener.impl;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.api.PlayerChangeClaimEvent;
import git.moiCR.hcf.teams.type.system.TeamSafezone;
import git.moiCR.hcf.utils.CC;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

@AllArgsConstructor
public class GeneralListener implements Listener {

    private final Main plugin;

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChangeClaim(PlayerChangeClaimEvent event){
        var toTeam = event.getTo();
        var fromTeam = event.getFrom();
        var player = event.getPlayer();

        player.sendMessage(CC.t("&7From: " + fromTeam.getColor() + fromTeam.getName() +
                "&7(" + (fromTeam instanceof TeamSafezone ? "&aNon-Deathban" : "&cDeathban") + "&7)"));

        player.sendMessage(CC.t("&7To: " + toTeam.getColor() + toTeam.getName() +
                "&7(" + (toTeam instanceof TeamSafezone ? "&aNon-Deathban" : "&cDeathban") + "&7)"));
    }


}
