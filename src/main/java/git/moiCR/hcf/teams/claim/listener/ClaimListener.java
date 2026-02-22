package git.moiCR.hcf.teams.claim.listener;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.api.events.PlayerChangeClaimEvent;
import git.moiCR.hcf.teams.type.player.TeamPlayer;
import git.moiCR.hcf.teams.type.system.TeamRoad;
import git.moiCR.hcf.teams.type.system.TeamSafezone;
import git.moiCR.hcf.teams.type.system.TeamWilderness;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Objects;

public class ClaimListener implements Listener {

    private final Main plugin;

    public ClaimListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        var from = event.getFrom();
        var to = event.getTo();
        var player = event.getPlayer();

        if (from.getBlockX() == to.getBlockX()
                && from.getBlockY() == to.getBlockY()
                && from.getBlockZ() == to.getBlockZ()) {
            return;
        }

        var teamFrom = plugin.getClaimManager().getTeamAt(from);
        var teamTo = plugin.getClaimManager().getTeamAt(to);

        if (Objects.equals(teamFrom, teamTo)) {
            return;
        }

        var newEvent = new PlayerChangeClaimEvent(player, teamFrom, teamTo);
        plugin.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            event.setTo(from);
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        var from = event.getFrom();
        var to = event.getTo();
        var player = event.getPlayer();

        var teamFrom = plugin.getClaimManager().getTeamAt(from);
        var teamTo = plugin.getClaimManager().getTeamAt(to);

        if (Objects.equals(teamFrom, teamTo)) {
            return;
        }

        var newEvent = new PlayerChangeClaimEvent(player, teamFrom, teamTo);
        plugin.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            event.setTo(from);
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        var player = event.getPlayer();
        var block = event.getBlock();
        var teamAt = plugin.getClaimManager().getTeamAt(block.getLocation());
        boolean bypass = this.haveBypass(player);

        if (teamAt instanceof TeamWilderness || bypass) return;

        if (teamAt instanceof TeamSafezone || teamAt instanceof TeamRoad) {
            event.setCancelled(true);
            return;
        }

        if (teamAt instanceof TeamPlayer pt) {
            if (!pt.isMember(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        var player = event.getPlayer();
        var block = event.getBlock();
        var teamAt = plugin.getClaimManager().getTeamAt(block.getLocation());
        boolean bypass = this.haveBypass(player);

        if (teamAt instanceof TeamWilderness || bypass) return;

        if (teamAt instanceof TeamSafezone || teamAt instanceof TeamRoad) {
            event.setCancelled(true);
            return;
        }

        if (teamAt instanceof TeamPlayer pt) {
            if (!pt.isMember(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        var player = event.getPlayer();
        var block = event.getClickedBlock();
        if (block == null) return;

        boolean bypass = this.haveBypass(player);
        var teamAt = plugin.getClaimManager().getTeamAt(block.getLocation());

        if (teamAt instanceof TeamWilderness || bypass) return;
        if (!isInteractable(block.getType())) return;

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) return;

        if (teamAt instanceof TeamSafezone || teamAt instanceof TeamRoad) {
            event.setCancelled(true);
            return;
        }

        if (teamAt instanceof TeamPlayer pt) {
            if (!pt.isMember(player)) {
                event.setCancelled(true);
            }
        }
    }

    private boolean isInteractable(Material type) {
        return switch (type) {
            case CHEST, TRAPPED_CHEST, ENDER_CHEST, FURNACE, DISPENSER, DROPPER, HOPPER, ANVIL, BREWING_STAND,
                 ENCHANTMENT_TABLE, NOTE_BLOCK, LEVER, BEACON, JUKEBOX -> true;
            default -> type.name().contains("BUTTON") || type.name().contains("DOOR") || type.name().contains("GATE");
        };
    }

    public boolean haveBypass(Player player) {
        return player.hasPermission("hcf.bypass") && player.getGameMode() == GameMode.CREATIVE;
    }
}