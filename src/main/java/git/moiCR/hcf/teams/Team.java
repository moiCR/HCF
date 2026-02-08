package git.moiCR.hcf.teams;

import git.moiCR.hcf.claim.Claim;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public abstract class Team {

    private UUID id;
    private String name, displayName;
    private ChatColor color;
    private Set<Claim> claims;

    public Team(String name, String displayName, ChatColor color) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.displayName = displayName;
        this.color = color;
    }

}
