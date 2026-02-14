package git.moiCR.hcf.lib.menu.misc;

import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.lib.menu.Menu;
import git.moiCR.hcf.lib.menu.button.Button;
import git.moiCR.hcf.utils.ItemMaker;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.awt.*;

public record BackButton(Player player, Menu current, Menu previous) implements Button {

    @Override
    public ItemStack getIcon() {
        return ItemMaker.of(Material.REDSTONE)
                .setDisplayName(LangHandler.INSTANCE.getMessage(player, Lang.BACK_BUTTON))
                .addAllFlags().build();
    }

    @Override
    public boolean isRemovable() {
        return false;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        current.redirect(previous);
    }
}
