package git.moiCR.hcf.teams.claim;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

@Getter
@Setter
public class Claim {

    private final UUID id;
    private String worldName;
    private int x1, y1, z1;
    private int x2, y2, z2;

    private int minX, maxX, minZ, maxZ;

    public Claim(Location corner1, Location corner2) {
        this.id = UUID.randomUUID();
        this.worldName = corner1.getWorld().getName();
        this.x1 = corner1.getBlockX();
        this.y1 = corner1.getBlockY();
        this.z1 = corner1.getBlockZ();

        this.x2 = corner2.getBlockX();
        this.y2 = corner2.getBlockY();
        this.z2 = corner2.getBlockZ();
        calculateBounds();
    }

    private void calculateBounds() {
        this.minX = Math.min(x1, x2);
        this.maxX = Math.max(x1, x2);
        this.minZ = Math.min(z1, z2);
        this.maxZ = Math.max(z1, z2);
    }

    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }

    public Location getCorner1() {
        return new Location(getWorld(), x1, y1, z1);
    }

    public Location getCorner2() {
        return new Location(getWorld(), x2, y2, z2);
    }

    public boolean contains(Location loc) {
        if (loc == null) return false;
        if (!loc.getWorld().getName().equals(worldName)) return false;

        int x = loc.getBlockX();
        int z = loc.getBlockZ();

        return x >= minX && x <= maxX && z >= minZ && z <= maxZ;
    }

    public boolean overlaps(Claim other) {
        if (other == null) return false;
        if (!this.worldName.equals(other.worldName)) return false;

        return this.minX <= other.maxX && this.maxX >= other.minX &&
                this.minZ <= other.maxZ && this.maxZ >= other.minZ;
    }

    public Location getCenter() {
        double centerX = (minX + maxX) / 2.0;
        double centerZ = (minZ + maxZ) / 2.0;
        double centerY = Math.max(y1, y2) + 1;
        return new Location(getWorld(), centerX, centerY, centerZ);
    }

    public void iterateChunks(ChunkConsumer consumer) {
        int startX = minX >> 4;
        int endX = maxX >> 4;
        int startZ = minZ >> 4;
        int endZ = maxZ >> 4;

        for (int x = startX; x <= endX; x++) {
            for (int z = startZ; z <= endZ; z++) {
                consumer.accept(x, z);
            }
        }
    }

    @FunctionalInterface
    public interface ChunkConsumer {
        void accept(int chunkX, int chunkZ);
    }

    public int getArea() {
        return (getWidth()) * (getLength());
    }

    public int getWidth() {
        return (maxX - minX) + 1;
    }

    public int getLength() {
        return (maxZ - minZ) + 1;
    }
}