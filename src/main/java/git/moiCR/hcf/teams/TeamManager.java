package git.moiCR.hcf.teams;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.teams.claim.Claim;
import git.moiCR.hcf.teams.type.TeamTypeEnum;
import git.moiCR.hcf.teams.type.system.TeamRoad;
import git.moiCR.hcf.teams.type.system.TeamSafezone;
import git.moiCR.hcf.teams.type.system.TeamWarzone;
import git.moiCR.hcf.teams.type.system.TeamWilderness;
import git.moiCR.hcf.teams.type.player.TeamPlayer;
import git.moiCR.hcf.utils.Manager;
import lombok.Getter;
import org.bukkit.entity.Player;

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


    public Team getTeam(UUID id){
        for (Team team : teams) {
            if(team.getId().equals(id)) return team;
        }
        return null;
    }

    public Team getTeam(String name){
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

    public Team getTeamByClaim(Claim claim){
        for (Team team : teams) {
            if(team.getClaims().contains(claim)) return team;
        }
        return null;
    }

    public Team getTeamByPlayer(Player player){
        for (TeamPlayer team : getPlayerTeams()) {
            if (team.isMember(player)) return team;
        }
        return null;
    }

    public boolean createTeam(Player player, String name, TeamTypeEnum teamType){
        if (teamType == null){
            player.sendMessage(Lang.OPERATION_CANCELLED.get(player));
            return false;
        }

        if (getTeam(name) != null){
            player.sendMessage(Lang.TEAM_ALREADY_EXISTS.get(player));
            return false;
        }

        Team team = null;
        switch (teamType) {
            case ROAD -> team = new TeamRoad(name);
            case SAFE_ZONE -> team = new TeamSafezone(name);
        }

        if (team == null){
            player.sendMessage(Lang.ERROR_OCCURRED.get(player));
            return false;
        }

        getTeams().add(team);
        player.sendMessage(Lang.TEAM_SUCCESSFULLY_CREATED.get(player).replace("%team%", team.getName()));
        return true;
    }

    public Set<TeamPlayer> getPlayerTeams(){
        Set<TeamPlayer> playerFactions = new HashSet<>();
        for (Team faction : teams) {
            if(faction instanceof TeamPlayer) playerFactions.add((TeamPlayer) faction);
        }
        return playerFactions;
    }

}
