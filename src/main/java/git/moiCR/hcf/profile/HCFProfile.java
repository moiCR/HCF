package git.moiCR.hcf.profile;

import git.moiCR.hcf.teams.type.player.extra.TeamInvite;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class HCFProfile {

    @Setter(AccessLevel.NONE)
    private UUID id;

    private String name;

    private int balance;
    private int kills;
    private int deaths;
    private int killStreak;
    private double kdr;
    private String language;
    private Set<TeamInvite> invites;

    public HCFProfile(Player player) {
        this.id = player.getUniqueId();
        this.name = player.getName();
        this.balance = 0;
        this.kills = 0;
        this.deaths = 0;
        this.killStreak = 0;
        this.kdr = 0.0;
        this.language = player.spigot().getLocale();
        this.invites = new HashSet<>();
    }

    public void updateName() {
        var player = Bukkit.getPlayer(getId());
        if (player == null) {
            return;
        }

        if (Objects.equals(player.getName(), name)) {
            return;
        }
        setName(player.getName());
    }
}
