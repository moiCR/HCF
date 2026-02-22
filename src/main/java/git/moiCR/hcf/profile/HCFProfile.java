package git.moiCR.hcf.profile;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.profile.death.Death;
import git.moiCR.hcf.teams.Team;
import git.moiCR.hcf.teams.type.player.extra.TeamInvite;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import mkremins.fanciful.FancyMessage;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
    private List<TeamInvite> invites;
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
        this.invites = new ArrayList<>();
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

        this.invites = new ArrayList<>();
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

    public TeamInvite getInvite(Team team){
        return this.invites
                .stream()
                .filter(invite -> invite.getTeamId().equals(team.getId()))
                .findFirst()
                .orElse(null);
    }


    public void invite(Team team){
        this.invites.add(new TeamInvite(team.getId(), getId()));
        var player = getPlayer();

        if (player == null || !player.isOnline()){
            return;
        }

        new FancyMessage("&a" + team.getName() + "has invited you.")
                .then("(Click here to accept)")
                .command("t join " + team.getName()).send(player);

    }

    public Player getPlayer(){
        return Bukkit.getPlayer(getId());
    }

    public OfflinePlayer getOfflinePlayer(){
        return Bukkit.getOfflinePlayer(getId());
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
