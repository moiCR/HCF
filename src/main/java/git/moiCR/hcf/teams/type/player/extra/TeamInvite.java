package git.moiCR.hcf.teams.type.player.extra;

import git.moiCR.hcf.teams.type.player.extra.member.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class TeamInvite {

    private UUID id;
    private UUID teamId;
    private UUID invitedPlayerId;

    public TeamInvite(UUID teamId, UUID invitedPlayerId) {
        this.teamId = teamId;
        this.invitedPlayerId = invitedPlayerId;
    }


}
