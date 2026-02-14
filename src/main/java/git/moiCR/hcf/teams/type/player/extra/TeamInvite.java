package git.moiCR.hcf.teams.type.player.extra;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class TeamInvite {

    private UUID teamId;
    private UUID invitedPlayerId;
    private Role role;

    public TeamInvite(UUID teamId, UUID invitedPlayerId, Role role) {
        this.teamId = teamId;
        this.invitedPlayerId = invitedPlayerId;
        this.role = role;
    }

    public TeamInvite(UUID teamId, UUID invitedPlayerId) {
        this.teamId = teamId;
        this.invitedPlayerId = invitedPlayerId;
        this.role = Role.MEMBER;
    }


}
