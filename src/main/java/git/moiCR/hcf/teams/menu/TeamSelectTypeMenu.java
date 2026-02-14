package git.moiCR.hcf.teams.menu;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lib.menu.Menu;
import git.moiCR.hcf.lib.menu.button.Button;
import git.moiCR.hcf.teams.type.TeamTypeEnum;
import git.moiCR.hcf.utils.ItemMaker;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;

public class TeamSelectTypeMenu extends Menu {

    @Getter
    private final CompletableFuture<TeamTypeEnum> future;

    public TeamSelectTypeMenu(Main instance, Player player) {
        super(instance, player, false);
        this.future = new CompletableFuture<>();
        setSoundOnClick(true);
    }

    @Override
    public String getTitle() {
        return "Select Team Type";
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();

        for (TeamTypeEnum type : TeamTypeEnum.values()) {
            buttons.put(buttons.size(), new Button() {

                @Override
                public ItemStack getIcon() {
                    return ItemMaker.of(Material.PAPER)
                            .setDisplayName("&a" + type.name())
                            .addAllFlags()
                            .build();
                }

                @Override
                public boolean isRemovable() {
                    return false;
                }

                @Override
                public void onClick(InventoryClickEvent event) {
                    future.complete(type);
                    getPlayer().closeInventory();
                }
            });
        }
        return buttons;
    }

    @Override
    public void onClose() {
        if(future.isDone()){
            return;
        }

        future.completeExceptionally(new CancellationException("The process has been cancelled."));
    }
}
