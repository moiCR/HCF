package git.moiCR.hcf.lib.menu.misc;

import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.lib.menu.button.Button;
import git.moiCR.hcf.lib.menu.paginated.MenuPaginated;
import git.moiCR.hcf.utils.ItemMaker;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public record NextButton(MenuPaginated menu, Player player) implements Button {

    @Override
    public ItemStack getIcon() {
        return ItemMaker.of(Material.SKULL_ITEM)
                .setData(3)
                .setDisplayName(LangHandler.INSTANCE.getMessage(player, Lang.NEXT_PAGE))
                .setHeadTextureFromURL("http://textures.minecraft.net/texture/7c69d41076a8dea4f06d3f1a9ac47cc996988b74a0913ab2ac1a74caf7081918")
                .addAllFlags()
                .build();
    }

    @Override
    public boolean isRemovable() {
        return false;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        menu.next();
    }
}
