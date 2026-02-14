package git.moiCR.hcf.teams.type.player.extra;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

@Setter @Getter
public class Member {

    private final UUID id;
    private Role role;

    public Member(UUID id, Role role) {
        this.id = id;
        this.role = role;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(id);
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(id);
    }

}
