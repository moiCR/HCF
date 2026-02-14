package git.moiCR.hcf.teams.menu.edit.extra;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.lib.menu.button.Button;
import git.moiCR.hcf.lib.menu.paginated.MenuPaginated;
import git.moiCR.hcf.utils.ColorType;
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

public class TeamSelectColorMenu extends MenuPaginated {

    @Getter
    private final CompletableFuture<ColorType> future;

    public TeamSelectColorMenu(Main instance, Player player) {
        super(instance, player, false);
        this.future = new CompletableFuture<>();
    }

    @Override
    public Map<Integer, Button> getPaginatedButtons() {
        Map<Integer, Button> buttons = new HashMap<>();

        for (ColorType colorType : ColorType.values()) {
            buttons.put(buttons.size(), new Button() {
                @Override
                public ItemStack getIcon() {
                    return ItemMaker.of(Material.WOOL)
                            .setData(colorType.getWoolData())
                            .setDisplayName(colorType.getChatColor() + colorType.name())
                            .addAllFlags()
                            .build();
                }

                @Override
                public boolean isRemovable() {
                    return false;
                }

                @Override
                public void onClick(InventoryClickEvent event) {
                    future.complete(colorType);
                }
            });
        }

        return buttons;
    }

    @Override
    public String getTitle() {
        return LangHandler.INSTANCE.getMessage(getPlayer(), Lang.SELECT_COLOR);
    }

    @Override
    public int getSize() {
        return 3*9;
    }

    @Override
    public void onClose() {
        if(future.isDone()){
            return;
        }

        future.completeExceptionally(new CancellationException("The process has been cancelled."));    }
}
