package git.moiCR.hcf.teams.claim;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.lib.Handler;
import git.moiCR.hcf.utils.Constants;
import git.moiCR.hcf.utils.ParticleUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ClaimHandler extends Handler {

    private final Map<UUID, ClaimSelection> claimSelections;

    public ClaimHandler(Main instance) {
        super(instance);
        this.claimSelections = new HashMap<>();
    }

    public ClaimSelection startClaimSelection(Player player){
        var selection = new ClaimSelection();
        claimSelections.put(player.getUniqueId(), selection);
        return selection;
    }

    public boolean isClaimSelection(Player player){
        return claimSelections.containsKey(player.getUniqueId());
    }


    @Override
    public Listener getEvents() {

        return new Listener() {
            @EventHandler
            public void onSystemWand(PlayerInteractEvent event){
                var player = event.getPlayer();
                var item = event.getItem();
                var action = event.getAction();
                var claimSelection = claimSelections.get(player.getUniqueId());

                if (claimSelection == null || item == null || !item.isSimilar(Constants.SYSTEM_WAND)){
                    return;
                }

                event.setCancelled(true);

                if (action == Action.LEFT_CLICK_AIR && player.isSneaking()){
                    if (claimSelection.getCorner1() == null || claimSelection.getCorner2() == null){
                        player.sendMessage(LangHandler.INSTANCE.getMessage(player, Lang.BOTH_CORNERS));
                        return;
                    }

                    claimSelection.getFuture().complete(null);
                    claimSelections.remove(player.getUniqueId());
                    ParticleUtil.stop(player.getUniqueId());
                    return;
                }

                if (action == Action.RIGHT_CLICK_AIR){
                    claimSelection.getFuture().completeExceptionally(new RuntimeException("cancelled"));
                    claimSelections.remove(player.getUniqueId());
                    ParticleUtil.stop(player.getUniqueId());
                    return;
                }

                if (event.getClickedBlock() == null){
                    return;
                }

                var clickedBlock = event.getClickedBlock();
                var location = clickedBlock.getLocation();

                if (action == Action.LEFT_CLICK_BLOCK){
                    claimSelection.setCorner1(location);
                    playSound(player, Sound.ITEM_PICKUP);
                    player.sendMessage(LangHandler.INSTANCE.getMessage(player, Lang.FIRST_CORNER_SELECTED));
                    if (claimSelection.getCorner2() != null){
                        ParticleUtil.start(getInstance(), player.getUniqueId(), claimSelection);
                    }
                    return;
                }

                if (action == Action.RIGHT_CLICK_BLOCK){
                    claimSelection.setCorner2(location);
                    playSound(player, Sound.ITEM_PICKUP);
                    player.sendMessage(LangHandler.INSTANCE.getMessage(player, Lang.SECOND_CORNER_SELECTED));
                    if (claimSelection.getCorner1() != null){
                        ParticleUtil.start(getInstance(), player.getUniqueId(), claimSelection);
                    }
                    return;
                }
            }
        };
    }

    @Getter
    @Setter
    public static class ClaimSelection{
        private CompletableFuture<Void> future;
        private Location corner1, corner2;

        public ClaimSelection(){
            this.future = new CompletableFuture<>();
            this.corner1 = null;
            this.corner2 = null;
        }
    }
}
