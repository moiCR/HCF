package git.moiCR.hcf.teams;

import git.moiCR.hcf.teams.claim.Claim;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.HashSet;
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
        this.claims = new HashSet<>();
    }

    public void addClaim(Claim claim){
        claims.add(claim);
    }

    public void removeClaim(Claim claim){
        claims.removeIf(claims::contains);
    }

}
