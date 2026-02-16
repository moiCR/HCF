package git.moiCR.hcf.teams.claim;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.teams.Team;
import git.moiCR.hcf.lib.Manager;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ClaimManager extends Manager {

    private final Map<Long, Claim> chunkMap;
    private final int WARZONE_RADIUS = 1000;
    private final int NETHER_WARZONE_RADIUS = 500;
    private final ClaimHandler handler;

    public ClaimManager(Main instance) {
        super(instance);
        this.chunkMap = new HashMap<>();
        this.handler = new ClaimHandler(instance);
    }

    public boolean createClaim(Team team, Location corner1, Location corner2) {
        if (!corner1.getWorld().equals(corner2.getWorld())) {
            return false;
        }

        Claim newClaim = new Claim(corner1, corner2);
        int x1 = newClaim.getMinX();
        int x2 = newClaim.getMaxX();
        int z1 = newClaim.getMinZ();
        int z2 = newClaim.getMaxZ();


        int minChunkX = x1 >> 4;
        int maxChunkX = x2 >> 4;
        int minChunkZ = z1 >> 4;
        int maxChunkZ = z2 >> 4;

        for (int x = minChunkX; x <= maxChunkX; x++) {
            for (int z = minChunkZ; z <= maxChunkZ; z++) {
                long key = getChunkKey(x, z);

                if (chunkMap.containsKey(key)) {
                    return  false;
                }

                chunkMap.put(key, newClaim);
            }
        }

        team.getClaims().add(newClaim);
        return true;
    }

    public Claim getClaimAt(Location location) {
        if (location == null) return null;

        long key = getChunkKey(location);

        Claim claim = chunkMap.get(key);

        if (claim != null && claim.contains(location)) {
            return claim;
        }

        return null;
    }

    public Team getTeamAt(Location location) {
        var environment = location.getWorld().getEnvironment();

        var claim = getClaimAt(location);
        if (claim != null) {
            return getInstance().getTeamManager().getTeamByClaim(claim);
        }

        if (environment == World.Environment.THE_END) {
            return getInstance().getTeamManager().getWarzoneTeam();
        }

        if (isWarzone(location)) {
            return getInstance().getTeamManager().getWarzoneTeam();
        }

        return getInstance().getTeamManager().getWildernessTeam();
    }

    public boolean isWarzone(Location location) {
        if (location == null) return false;

        World.Environment env = location.getWorld().getEnvironment();
        double x = Math.abs(location.getX());
        double z = Math.abs(location.getZ());

        if (env == World.Environment.NETHER) {
            return x <= NETHER_WARZONE_RADIUS && z <= NETHER_WARZONE_RADIUS;
        } else if (env == World.Environment.NORMAL) {
            return x <= WARZONE_RADIUS && z <= WARZONE_RADIUS;
        }

        return false;
    }

    private long getChunkKey(int x, int z) {
        return (long) x & 0xFFFFFFFFL | ((long) z & 0xFFFFFFFFL) << 32;
    }

    private long getChunkKey(Location location) {
        return getChunkKey(location.getBlockX() >> 4, location.getBlockZ() >> 4);
    }

}
