package git.moiCR.hcf.profile;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.profile.death.DeathRecord;
import git.moiCR.hcf.teams.Team;
import git.moiCR.hcf.teams.type.player.TeamPlayer;
import git.moiCR.hcf.teams.type.player.extra.TeamInvite;
import git.moiCR.hcf.utils.CC;
import git.moiCR.hcf.utils.SerializeUtil;
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
public class Profile {

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
    private List<DeathRecord> deathRecords;

    public Profile(Player player) {
        this.id = player.getUniqueId();
        this.name = player.getName();
        this.balance = 2500;
        this.kills = 0;
        this.deaths = 0;
        this.killStreak = 0;
        this.kdr = 0.0;
        this.language = player.spigot().getLocale();
        this.invites = new ArrayList<>();
        this.deathRecords = new ArrayList<>();
    }


    public Profile(Main instance, Document document) {
        this.id = UUID.fromString(document.getString("_id"));
        this.name = document.getString("name");
        this.balance = document.getInteger("balance");
        this.kills = document.getInteger("kills");
        this.deaths = document.getInteger("deaths");
        this.killStreak = document.getInteger("killStreak");
        this.kdr = document.getDouble("kdr");
        this.language = document.getString("language");

        this.invites = new ArrayList<>();
        List<Document> savedInvites = document.getList("invites", Document.class);
        if (savedInvites != null) {
            savedInvites.forEach(doc -> this.invites.add(
                    new TeamInvite(
                            UUID.fromString(doc.getString("id")),
                            UUID.fromString(doc.getString("team_id"))
                    )));
        }

        this.deathRecords = new ArrayList<>();
        List<Document> savedDeaths = document.getList("deaths_records", Document.class);
        if (savedDeaths != null) {
            savedDeaths.forEach(doc -> {
                try {
                    this.deathRecords.add(new DeathRecord(
                            UUID.fromString(doc.getString("id")),
                            SerializeUtil.deserializeLocation(doc.getString("location")),
                            SerializeUtil.itemsFromBase64(doc.getString("inv_contents")),
                            SerializeUtil.itemsFromBase64(doc.getString("armor_contents"))
                    ));
                } catch (Exception e) {
                    Bukkit.getLogger().severe("The process of loading death records has failed for: " + name);
                }
            });
        }
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


    public void invite(TeamPlayer team){
        this.invites.add(new TeamInvite(team));
        var player = getPlayer();

        if (player == null || !player.isOnline()){
            return;
        }

        new FancyMessage(CC.t("&a" + team.getName() + "has invited you. "))
                .then(CC.t("(Click here to accept)"))
                .command("/t join " + team.getName()).send(player);

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
                .append("language", language)
                .append("deaths_records", deathRecords.stream().map(record ->
                        new Document()
                                .append("id", record.getId().toString())
                                .append("location", SerializeUtil.serializeLocation(record.getLocation()))
                                .append("inv_contents", SerializeUtil.itemsToBase64(record.getContents()))
                                .append("armor_contents", SerializeUtil.itemsToBase64(record.getArmorContents()))
                ))
                .append("invites", invites.stream().map(invite ->
                        new Document()
                                .append("id", invite.getId().toString())
                                .append("team_id", invite.getTeamId().toString())
                ));
    }
}
