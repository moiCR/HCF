package git.moiCR.hcf.lib.menu.button;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface Button {

    ItemStack getIcon();
    boolean isRemovable();
    void onClick(InventoryClickEvent event);

}
