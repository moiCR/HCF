package git.moiCR.hcf.teams;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.teams.claim.Claim;
import git.moiCR.hcf.teams.type.TeamWarzone;
import git.moiCR.hcf.teams.type.TeamWilderness;
import git.moiCR.hcf.teams.type.player.TeamPlayer;
import git.moiCR.hcf.utils.Manager;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class TeamManager extends Manager {

    private final Set<Team> teams;
    private final TeamWilderness wildernessTeam;
    private final TeamWarzone warzoneTeam;

    public TeamManager(Main instance) {
        super(instance);
        this.teams = new HashSet<>();
        this.wildernessTeam = new TeamWilderness();
        this.warzoneTeam = new TeamWarzone();
    }


    public Team getFaction(UUID id){
        for (Team team : teams) {
            if(team.getId().equals(id)) return team;
        }
        return null;
    }

    public Team getFaction(String name){
        for (Team faction : teams) {
            if (faction instanceof TeamPlayer) continue;
            if(faction.getName().equalsIgnoreCase(name)) return faction;
        }
        return null;
    }

    public Team getPlayerTeam(String name){
        for (Team faction : teams) {
            if(faction instanceof TeamPlayer && faction.getName().equalsIgnoreCase(name)) return faction;
        }
        return null;
    }

    public Team getFactionByClaim(Claim claim){
        for (Team team : teams) {
            if(team.getClaims().contains(claim)) return team;
        }
        return null;
    }

    public Team getFactionByPlayer(Player player){
        for (TeamPlayer team : getPlayerTeams()) {
            if (team.isMember(player)) return team;
        }
        return null;
    }

    public Set<TeamPlayer> getPlayerTeams(){
        Set<TeamPlayer> playerFactions = new HashSet<>();
        for (Team faction : teams) {
            if(faction instanceof TeamPlayer) playerFactions.add((TeamPlayer) faction);
        }
        return playerFactions;
    }

}
