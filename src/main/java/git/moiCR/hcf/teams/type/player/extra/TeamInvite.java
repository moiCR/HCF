package git.moiCR.hcf.teams.type.player.extra;

import git.moiCR.hcf.teams.type.player.TeamPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class TeamInvite {

    private UUID id;
    private UUID teamId;

    public TeamInvite(TeamPlayer team) {
        this.id = UUID.randomUUID();
        this.teamId = team.getId();
    }

    public TeamInvite(UUID id, UUID teamId) {
        this.id = id;
        this.teamId = teamId;
    }
}
