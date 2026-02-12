package git.moiCR.hcf.teams.menu;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lib.menu.Menu;
import git.moiCR.hcf.lib.menu.button.Button;
import git.moiCR.hcf.lib.menu.paginated.MenuPaginated;
import git.moiCR.hcf.lib.prompt.type.PromptString;
import git.moiCR.hcf.teams.menu.edit.TeamRoadEditMenu;
import git.moiCR.hcf.teams.type.player.TeamPlayer;
import git.moiCR.hcf.teams.type.system.TeamRoad;
import git.moiCR.hcf.teams.type.system.TeamWilderness;
import git.moiCR.hcf.utils.CC;
import git.moiCR.hcf.utils.ItemMaker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TeamSelectToEditMenu extends MenuPaginated {

    public TeamSelectToEditMenu(Main instance, Player player) {
        super(instance, player, false);
        getNavigateBar().put(4, new CreateTeamButton());
    }

    @Override
    public String getTitle() {
        return getPlayer().spigot().getLocale().startsWith("es")
                ? "Selecciona un equipo"
                : "Select a team";
    }

    @Override
    public int getSize() {
        return 4*9;
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
                                        .setDisplayName(team.getDisplayName())
                                        .setLore(
                                                " ",
                                                Lang.CLICK_TO_EDIT.get(getPlayer()),
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
                                if (team instanceof TeamRoad road) new TeamRoadEditMenu(getInstance(), getPlayer(), road).open();
                            }
                        }));

        return buttons;
    }


    private class CreateTeamButton implements Button {

        @Override
        public ItemStack getIcon() {
            return ItemMaker.of(Material.EMERALD)
                    .setDisplayName(getPlayer().spigot().getLocale().startsWith("es")
                            ? "&aCrear un nuevo equipo"
                            : "&aCreate a new team")
                    .setLore(
                            " ",
                            getPlayer().spigot().getLocale().startsWith("es")
                                    ? "&eHaz clic para crear un nuevo equipo."
                                    : "&eClick to create a new team.",
                            " "
                    )
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
            namePrompt.setPromptMessage(getPlayer().spigot().getLocale().startsWith("es")
                    ? "&aPor favor, ingresa el nombre del equipo:"
                    : "&aPlease enter the team name:");
            namePrompt.start();
            namePrompt.getFuture().thenAccept(name -> {
                var teamType = new TeamSelectTypeMenu(getInstance(), getPlayer());
                teamType.open();
                teamType.getFuture().thenAccept(selectedType -> {
                    if (!getInstance().getTeamManager().createTeam(getPlayer(), name, selectedType)) return;

                    getPlayer().sendMessage(Lang.REDIRECTING.get(getPlayer()));
                    Bukkit.getScheduler().runTaskLaterAsynchronously(getInstance(), () -> new TeamSelectToEditMenu(getInstance(), getPlayer()).open(), 40L);
                });
            });
        }
    }
}
