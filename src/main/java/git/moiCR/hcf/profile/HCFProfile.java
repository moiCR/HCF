package git.moiCR.hcf.profile;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.profile.death.Death;
import git.moiCR.hcf.teams.type.player.extra.TeamInvite;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

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
    private List<Death> deathsList;

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
        this.deathsList = new ArrayList<>();
    }


    public HCFProfile(Main instance, Document document) {
        this.id = UUID.fromString(document.getString("_id"));
        this.name = document.getString("name");
        this.balance = document.getInteger("balance");
        this.kills = document.getInteger("kills");
        this.deaths = document.getInteger("deaths");
        this.killStreak = document.getInteger("killStreak");
        this.kdr = document.getDouble("kdr");
        this.language = document.getString("language");

        this.invites = new HashSet<>();
        this.deathsList = new ArrayList<>();

        instance.getProfileManager().addProfile(this);
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


    public Document toDocument(){
        return new Document("_id", id.toString())
                .append("name", name)
                .append("balance", balance)
                .append("kills", kills)
                .append("deaths", deaths)
                .append("killStreak", killStreak)
                .append("kdr", kdr)
                .append("language", language);
    }
}
