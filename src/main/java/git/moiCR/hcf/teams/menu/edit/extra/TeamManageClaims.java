package git.moiCR.hcf.teams.menu.edit.extra;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.lib.menu.button.Button;
import git.moiCR.hcf.lib.menu.misc.BackButton;
import git.moiCR.hcf.lib.menu.paginated.MenuPaginated;
import git.moiCR.hcf.teams.Team;
import git.moiCR.hcf.claim.Claim;
import git.moiCR.hcf.teams.menu.edit.TeamEditMenu;
import git.moiCR.hcf.utils.Constants;
import git.moiCR.hcf.utils.ItemMaker;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TeamManageClaims extends MenuPaginated {

    private final Team team;

    public TeamManageClaims(Main instance, Player player, Team team) {
        super(instance, player, false);
        this.team = team;
        setSoundOnClick(true);
        getNavigateBar().put(4, new NewClaimButton());
        getNavigateBar().put(7, new BackButton(getPlayer(), this, new TeamEditMenu(instance, player, team)));
    }


    @Override
    public Map<Integer, Button> getPaginatedButtons() {
        Map<Integer, Button> buttons = new HashMap<>();
        if (team.getClaims().isEmpty()){
            return buttons;
        }

        for (Claim claim : team.getClaims()) {
            buttons.put(buttons.size(), new Button() {
                @Override
                public ItemStack getIcon() {
                    return ItemMaker.of(Material.BEACON)
                            .setDisplayName(ChatColor.BLUE + (claim.getCenter().getX() + ", " + claim.getCenter().getY() + ", " + claim.getCenter().getZ()))
                            .setLore(LangHandler.INSTANCE.getMessageList(getPlayer(), Lang.TEAM_CLAIM_LORE))
                            .build();
                }

                @Override
                public boolean isRemovable() {
                    return false;
                }

                @Override
                public void onClick(InventoryClickEvent event) {
                    switch (event.getClick()) {
                        case LEFT -> getPlayer().teleport(claim.getCenter());
                        case RIGHT -> {
                            team.removeClaim(claim);
                            update();
                        }
                    }

                }
            });
        }

        return buttons;
    }

    @Override
    public String getTitle() {
        return LangHandler.INSTANCE.getMessage(getPlayer(), Lang.EDITING).replace("%name%", team.getName());
    }

    @Override
    public int getSize() {
        return 4*9;
    }


    private class NewClaimButton implements Button {

        @Override
        public ItemStack getIcon() {
            return ItemMaker.of(Material.DIAMOND_AXE)
                    .setDisplayName(LangHandler.INSTANCE.getMessage(getPlayer(), Lang.TEAM_NEW_CLAIM))
                    .setLore(LangHandler.INSTANCE.getMessageList(getPlayer(), Lang.TEAM_NEW_CLAIM_LORE))
                    .addAllFlags()
                    .build();
        }

        @Override
        public boolean isRemovable() {
            return false;
        }

        @Override
        public void onClick(InventoryClickEvent event) {
            getPlayer().closeInventory();
            if (getInstance().getClaimManager().getHandler().isClaimSelection(getPlayer())) {
                if (!getPlayer().getInventory().contains(Constants.SYSTEM_WAND))
                    getPlayer().getInventory().addItem(Constants.SYSTEM_WAND);
                return;
            }

            if (getPlayer().getInventory().firstEmpty() == -1) return;

            if (getPlayer().getInventory().contains(Constants.SYSTEM_WAND)) {
                getPlayer().getInventory().remove(Constants.SYSTEM_WAND);
            }

            getPlayer().getInventory().addItem(Constants.SYSTEM_WAND);
            var claimSelection = getInstance().getClaimManager().getHandler().startClaimSelection(getPlayer());
            claimSelection.getFuture().thenRun(() -> {
                getPlayer().getInventory().remove(Constants.SYSTEM_WAND);
                getInstance().getClaimManager().createClaim(team, claimSelection.getCorner1(), claimSelection.getCorner2());
                redirect(new TeamManageClaims(getInstance(), getPlayer(), team));
            }).exceptionally(throwable -> {
                getPlayer().getInventory().remove(Constants.SYSTEM_WAND);
                return null;
            });
        }
    }

}
