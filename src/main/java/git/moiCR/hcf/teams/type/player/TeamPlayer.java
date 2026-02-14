package git.moiCR.hcf.teams.type.player;

import git.moiCR.hcf.teams.Team;
import git.moiCR.hcf.teams.type.player.extra.Member;
import git.moiCR.hcf.teams.type.player.extra.Role;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
public class TeamPlayer extends Team {

    private Location homeLocation;
    private int balance;
    private double dtr;
    private boolean open;

    private Set<Member> members;

    public TeamPlayer(String name, Player leader) {
        super(name, name, ChatColor.WHITE);
        this.homeLocation = null;
        this.balance = 0;
        this.dtr = 1.1;
        this.open = false;

        this.members = new HashSet<>();
        this.members.add(new Member(leader.getUniqueId(), Role.LEADER));

    }

    public Member getMember(UUID uuid) {
        return members.stream()
                .filter(m -> m.getId().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    public Member getLeader() {
        return this.members.stream().filter(member -> member.getRole() == Role.LEADER).findFirst().orElse(null);
    }

    public boolean isMember(Player player){
        return isMember(player.getUniqueId());
    }

    public boolean isMember(UUID id){
        return getMember(id) != null;
    }

    public void removeMember(UUID uuid) {
        members.removeIf(m -> !m.getRole().equals(Role.LEADER) && m.getId().equals(uuid));
    }

}
