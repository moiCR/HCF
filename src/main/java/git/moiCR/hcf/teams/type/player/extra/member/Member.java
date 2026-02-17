package git.moiCR.hcf.teams.type.player.extra.member;

import git.moiCR.hcf.teams.type.player.extra.chat.ChatChannel;
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
    private ChatChannel chatChannel;

    public Member(UUID id, Role role) {
        this.id = id;
        this.role = role;
        this.chatChannel = ChatChannel.GLOBAL;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(id);
    }
    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(id);
    }

}
