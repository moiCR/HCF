package git.moiCR.hcf.teams;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.teams.claim.Claim;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

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

    public Team(Main instance, Document document){
        this.id = UUID.fromString(document.getString("id"));
        this.name = document.getString("name");
        this.displayName = document.getString("display_name");
        this.color = ChatColor.valueOf(document.getString("color"));

        this.claims = new HashSet<>();

        var claimsSaved = document.getList("claims", Document.class);

        claimsSaved.forEach(claim -> {
            int x1 = claim.getInteger("x1");
            int x2 = claim.getInteger("x2");

            int y1 = claim.getInteger("y1");
            int y2 = claim.getInteger("y2");

            int z1 = claim.getInteger("z1");
            int z2 = claim.getInteger("z2");
            var worldName = document.getString("world_name");
            var world = Bukkit.getWorld(worldName);

            if (world != null) {
                Location corner1 = new Location(world, x1, y1, z1);
                Location corner2 = new Location(world, x2, y2, z2);
                instance.getClaimManager().createClaim(this, corner1, corner2);
            } else {
                System.err.println("The process has failed. World '" + worldName + "' not found for claim.");
            }
        });
    }

    public void addClaim(Claim claim){
        claims.add(claim);
    }

    public void removeClaim(Claim claim){
        claims.removeIf(claims::contains);
    }

    public void sendInfo(Player player){

    }

    public Document toDocument(){
        Document document = new Document();

        document.append("id", id.toString());
        document.append("name", name);
        document.append("display_name", displayName);
        document.append("color", color.name());

        document.append("claims", claims.stream()
                .map(Claim::toDocument)
                .collect(java.util.stream.Collectors.toList()));

        return document;
    }

}
