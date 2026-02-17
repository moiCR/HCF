package git.moiCR.hcf.teams.menu;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.lib.menu.button.Button;
import git.moiCR.hcf.lib.menu.paginated.MenuPaginated;
import git.moiCR.hcf.lib.prompt.type.PromptString;
import git.moiCR.hcf.teams.menu.edit.TeamEditMenu;
import git.moiCR.hcf.teams.type.player.TeamPlayer;
import git.moiCR.hcf.utils.ColorType;
import git.moiCR.hcf.utils.ItemMaker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TeamEditorMenu extends MenuPaginated {

    public TeamEditorMenu(Main instance, Player player) {
        super(instance, player, false);
        getNavigateBar().put(4, new CreateTeamButton());
        setSoundOnClick(true);
    }

    @Override
    public String getTitle() {
        return LangHandler.INSTANCE.getMessage(getPlayer(), Lang.SELECT_TEAM_TO_EDIT);
    }

    @Override
    public int getSize() {
        return 6*9;
    }


    @Override
    public Map<Integer, Button> getPaginatedButtons() {
        Map<Integer, Button> buttons = new HashMap<>();
        getInstance().getTeamManager().getTeams().stream().filter(team -> !(team instanceof TeamPlayer)).forEach(
                team ->
                        buttons.put(buttons.size(), new Button() {
                            @Override
                            public ItemStack getIcon() {
                                return ItemMaker.of(Material.WOOL)
                                        .setDisplayName(team.getColor() + team.getDisplayName())
                                        .setData(ColorType.getByChatColor(team.getColor()).getWoolData())
                                        .setLore(
                                                " ",
                                                LangHandler.INSTANCE.getMessage(getPlayer(), Lang.CLICK_TO_EDIT),
                                                " "
                                        )
                                        .build();
                            }

                            @Override
                            public boolean isRemovable() {
                                return false;
                            }

                            @Override
                            public void onClick(InventoryClickEvent event) {
                                getPlayer().closeInventory();
                                new TeamEditMenu(getInstance(), getPlayer(), team).open();
                            }
                        }));

        return buttons;
    }


    private class CreateTeamButton implements Button {
        @Override
        public ItemStack getIcon() {
            return ItemMaker.of(Material.NETHER_STAR)
                    .setDisplayName(LangHandler.INSTANCE.getMessage(getPlayer(), Lang.NEW_TEAM_CREATE))
                    .setLore(LangHandler.INSTANCE.getMessageList(getPlayer(), Lang.NEW_TEAM_CREATE_LORE))
                    .addAllFlags()
                    .build();
        }

        @Override
        public boolean isRemovable() {
            return false;
        }

        @Override
        public void onClick(InventoryClickEvent event) {
            var namePrompt = new PromptString(getInstance(), getPlayer());
            namePrompt.setPromptMessage(LangHandler.INSTANCE.getMessage(getPlayer(), Lang.ENTER_TEAM_NAME));
            namePrompt.start();
            namePrompt.getFuture().thenAccept(name -> {
                var teamType = new TeamSelectTypeMenu(getInstance(), getPlayer());
                teamType.open();
                teamType.getFuture().thenAccept(selectedType -> {
                    if (!getInstance().getTeamManager().createTeam(getPlayer(), name, selectedType)){
                        return;
                    }

                    getPlayer().sendMessage(LangHandler.INSTANCE.getMessage(getPlayer(), Lang.REDIRECTING));
                    Bukkit.getScheduler().runTaskLaterAsynchronously(getInstance(), () -> new TeamEditorMenu(getInstance(), getPlayer()).open(), 40L);
                }).exceptionally(ex -> {
                    getPlayer().sendMessage(LangHandler.INSTANCE.getMessage(getPlayer(), Lang.OPERATION_CANCELLED));
                    return null;
                });
            }).exceptionally(ex -> {
                getPlayer().sendMessage(LangHandler.INSTANCE.getMessage(getPlayer(), Lang.OPERATION_CANCELLED));
                return null;
            });
        }
    }
}
