package git.moiCR.hcf.lib.menu.misc;


import git.moiCR.hcf.lib.menu.button.Button;
import git.moiCR.hcf.utils.ItemMaker;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public record DecorationButton() implements Button {
    @Override
    public ItemStack getIcon() {
        return ItemMaker.of(Material.STAINED_GLASS_PANE)
                .setData(10)
                .setDisplayName(" ")
                .addAllFlags()
                .build();
    }

    @Override
    public boolean isRemovable() {
        return false;
    }

    @Override
    public void onClick(InventoryClickEvent event) {

    }
}
