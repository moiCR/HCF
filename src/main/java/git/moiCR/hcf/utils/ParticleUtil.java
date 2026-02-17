package git.moiCR.hcf.utils;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.teams.claim.ClaimHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParticleUtil {

    private static final Map<UUID, BukkitTask> activeTasks = new HashMap<>();

    public static boolean isRunning(UUID playerId) {
        return activeTasks.containsKey(playerId);
    }

    public static void start(Main instance, UUID playerId, ClaimHandler.ClaimSelection data) {
        stop(playerId);

        BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(
                instance,
                () -> {
                    Player player = Bukkit.getPlayer(playerId);
                    if (player == null || data.getCorner1() == null || data.getCorner2() == null) {
                        stop(playerId);
                        return;
                    }

                    drawCuboid(player, data.getCorner1(), data.getCorner2());

                }, 0L, 5L);

        activeTasks.put(playerId, task);
    }

    public static void stop(UUID playerId) {
        if (isRunning(playerId)) {
            activeTasks.remove(playerId).cancel();
        }
    }

    private static void drawCuboid(Player player, Location corner1, Location corner2) {
        World world = corner1.getWorld();

        double minX = Math.min(corner1.getX(), corner2.getX());
        double maxX = Math.max(corner1.getX(), corner2.getX());
        double minZ = Math.min(corner1.getZ(), corner2.getZ());
        double maxZ = Math.max(corner1.getZ(), corner2.getZ());
        double minY = 0;
        double maxY = world.getMaxHeight();

        drawLine(player, new Location(world, minX, minY, minZ), new Location(world, maxX, minY, minZ));
        drawLine(player, new Location(world, maxX, minY, minZ), new Location(world, maxX, minY, maxZ));
        drawLine(player, new Location(world, maxX, minY, maxZ), new Location(world, minX, minY, maxZ));
        drawLine(player, new Location(world, minX, minY, maxZ), new Location(world, minX, minY, minZ));

        drawLine(player, new Location(world, minX, maxY, minZ), new Location(world, maxX, maxY, minZ));
        drawLine(player, new Location(world, maxX, maxY, minZ), new Location(world, maxX, maxY, maxZ));
        drawLine(player, new Location(world, maxX, maxY, maxZ), new Location(world, minX, maxY, maxZ));
        drawLine(player, new Location(world, minX, maxY, maxZ), new Location(world, minX, maxY, minZ));

        drawLine(player, new Location(world, minX, minY, minZ), new Location(world, minX, maxY, minZ));
        drawLine(player, new Location(world, maxX, minY, minZ), new Location(world, maxX, maxY, minZ));
        drawLine(player, new Location(world, maxX, minY, maxZ), new Location(world, maxX, maxY, maxZ));
        drawLine(player, new Location(world, minX, minY, maxZ), new Location(world, minX, maxY, maxZ));
    }

    private static void drawLine(Player player, Location p1, Location p2) {
        if (!p1.getWorld().equals(p2.getWorld())) return;

        double distance = p1.distance(p2);
        int particles = (int) (distance / 0.25);

        double deltaX = p2.getX() - p1.getX();
        double deltaY = p2.getY() - p1.getY();
        double deltaZ = p2.getZ() - p1.getZ();

        for (int i = 0; i <= particles; i++) {
            double progress = (double) i / particles;

            double x = p1.getX() + deltaX * progress;
            double y = p1.getY() + deltaY * progress;
            double z = p1.getZ() + deltaZ * progress;

            Location particleLoc = new Location(p1.getWorld(), x, y, z);

            ParticleEffect.VILLAGER_HAPPY.display(particleLoc, player);
        }
    }
}
