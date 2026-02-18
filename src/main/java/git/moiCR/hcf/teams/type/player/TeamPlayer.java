package git.moiCR.hcf.teams.type.player;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.teams.Team;
import git.moiCR.hcf.teams.type.player.extra.dtr.DTRStatus;
import git.moiCR.hcf.teams.type.player.extra.member.Member;
import git.moiCR.hcf.teams.type.player.extra.member.Role;
import git.moiCR.hcf.utils.CC;
import lombok.Getter;
import lombok.Setter;
import mkremins.fanciful.FancyMessage;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

@Getter @Setter
public class TeamPlayer extends Team {

    private Location homeLocation;
    private int balance;
    private double dtr;
    private DTRStatus dtrStatus;
    private long frozenUntil;
    private boolean open;
    private int points;
    private Set<Member> members;
    private boolean regenNotified = true;

    public TeamPlayer(String name, Player leader) {
        super(name, name, ChatColor.WHITE);
        this.homeLocation = null;
        this.balance = 0;
        this.dtr = 1.1;
        this.dtrStatus = DTRStatus.NORMAL;
        this.frozenUntil = 0;
        this.points = 0;

        this.open = false;

        this.members = new HashSet<>();
        this.members.add(new Member(leader.getUniqueId(), Role.LEADER));
    }

    public TeamPlayer(Main instance, Document document) {
        super(instance, document);
        if (document.containsKey("hq")) {
            Document hqDoc = (Document) document.get("hq");
            this.homeLocation = new Location(
                    Bukkit.getWorld(hqDoc.getString("world")),
                    hqDoc.getDouble("x"),
                    hqDoc.getDouble("y"),
                    hqDoc.getDouble("z")
            );
        }


        this.dtr = document.getDouble("dtr");
        this.frozenUntil = document.getLong("frozen_until");
        this.balance = document.getInteger("balance");
        this.points = document.getInteger("points");
        this.open = document.getBoolean("open");
        this.members = new HashSet<>();

        var savedMembers = document.getList("members", Document.class);

        savedMembers.forEach(member -> members.add(
                new Member(
                        UUID.fromString(member.getString("id")),
                        Role.valueOf(member.getString("role"))
                )));

        this.dtrStatus = DTRStatus.valueOf(document.getString("dtr_status"));

        instance.getTeamManager().getTeams().add(this);
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

    public double getMaxDtr(){
        return Math.min(5.5, members.size() * 1.1);
    }

    public boolean isRaidable(){
        return dtr <= 0;
    }

    public boolean isFrozen(){
        return System.currentTimeMillis() < frozenUntil;
    }

    public void freeze(long millis) {
        this.dtrStatus = DTRStatus.FROZEN;
        this.frozenUntil = System.currentTimeMillis() + millis;
    }

    public String getFreezeTimeFormatted() {
        long millis = Math.max(0, frozenUntil - System.currentTimeMillis());
        if (millis <= 0) return "0s";

        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        if (hours > 0) return String.format("%dh %dm", hours, minutes % 60);
        if (minutes > 0) return String.format("%dm %ds", minutes, seconds % 60);
        return seconds + "s";
    }

    public void broadcast(String... messages){
        var onlineMembers = getOnlineMembers().stream().map(Member::getPlayer).toList();

        for (Player player : onlineMembers) {
            for (String message : messages) {
                player.sendMessage(CC.t(message));
            }
        }
    }


    private List<UUID> getCoLeaders(){
        List<UUID> coLeaders = new ArrayList<>();
        for (Member member : members) {
            if (member.getRole().equals(Role.CO_LEADER)) coLeaders.add(member.getId());
        }
        return coLeaders;
    }

    private List<UUID> getCaptains(){
        List<UUID> captains = new ArrayList<>();
        for (Member member : members) {
            if (member.getRole().equals(Role.CAPTAIN)) captains.add(member.getId());
        }
        return captains;
    }

    private List<UUID> getNormalMembers(){
        List<UUID> normalMembers = new ArrayList<>();
        for (Member member : members) {
            if (member.getRole().equals(Role.MEMBER)) normalMembers.add(member.getId());
        }
        return normalMembers;
    }


    @Override
    public void sendInfo(Player player) {
        List<String> lines = LangHandler.INSTANCE.getMessageList(player, Lang.TEAM_WHO_INFO);

        String hqCoords = (homeLocation == null) ? "None" : homeLocation.getBlockX() + ", " + homeLocation.getBlockY() + ", " + homeLocation.getBlockZ();
        String dtrText = dtrStatus.getColor() + String.format("%.2f", dtr) + dtrStatus.getIcon();
        String statusText = open ? "&a(Open)" : "&c(Closed)";
        String frozenLine = (isFrozen())
                ? "&eFrozen Time&7: &9" + getFreezeTimeFormatted() : "";

        for (String line : lines) {
            if (line.contains("%frozen_time%") && frozenLine.isEmpty()) continue;

            FancyMessage fm = new FancyMessage("");

            String baseLine = line
                    .replace("%name%", getName())
                    .replace("%online%", String.valueOf(getOnlineMembers().size()))
                    .replace("%max%", String.valueOf(members.size()))
                    .replace("%leader%", formatPlayerName(getLeader().getId()))
                    .replace("%co-leaders%", formatPlayerList(getCoLeaders()))
                    .replace("%captains%", formatPlayerList(getCaptains()))
                    .replace("%members%", formatPlayerList(getNormalMembers()))
                    .replace("%balance%", String.valueOf(balance))
                    .replace("%dtr%", dtrText)
                    .replace("%frozen_time%", frozenLine)
                    .replace("%points%", String.valueOf(points));

            if (baseLine.contains("%hq%")) {
                String[] parts = baseLine.split("%hq%");
                fm.text(CC.t(parts[0]))
                        .then(hqCoords)
                        .tooltip(CC.t("&eClick to teleport to HQ"))
                        .command("/tphq " + getName())
                        .then(parts.length > 1 ? CC.t(parts[1]) : "");
            }
            else if (baseLine.contains("%status%")) {
                String[] parts = baseLine.split("%status%");
                fm.text(CC.t(parts[0]))
                        .then(CC.t(statusText))
                        .tooltip("&eClick to " + (open ? "close" : "open") + " faction")
                        .command("/f open " + getName())
                        .then(parts.length > 1 ? CC.t(parts[1]) : "");
            }
            else {

                fm.text(CC.t(baseLine));
            }

            fm.send(player);
        }
    }

    public List<Member> getOnlineMembers(){
        var toReturn = new ArrayList<Member>();
        for (Member member : members) {
            var player = Bukkit.getPlayer(member.getId());
            if (player != null) toReturn.add(member);
        }
        return toReturn;
    }

    private String formatPlayerName(UUID uuid) {
        if (uuid == null) return "None";
        org.bukkit.OfflinePlayer offlinePlayer = org.bukkit.Bukkit.getOfflinePlayer(uuid);
        String name = offlinePlayer.getName();
        return (offlinePlayer.isOnline() ? "&a" : "&7") + name;
    }

    private String formatPlayerList(java.util.List<java.util.UUID> uuids) {
        if (uuids == null || uuids.isEmpty()) return "";
        return uuids.stream()
                .map(this::formatPlayerName)
                .collect(java.util.stream.Collectors.joining("&7, "));
    }


    @Override
    public Document toDocument() {
        Document document = super.toDocument();

        if (homeLocation != null){
            document.append("hq", new Document()
                    .append("world", homeLocation.getWorld().getName())
                    .append("x", homeLocation.getX())
                    .append("y", homeLocation.getY())
                    .append("z", homeLocation.getZ()));
        }

        document.append("balance", balance);
        document.append("dtr", dtr);
        document.append("dtr_status", dtrStatus.name());
        document.append("frozen_until", frozenUntil);
        document.append("open", open);
        document.append("points", points);

        document.append("members", members.stream()
                .map(member -> new Document()
                        .append("id", member.getId().toString())
                        .append("role", member.getRole().name()))
                .collect(java.util.stream.Collectors.toList()));


        return document;
    }
}
