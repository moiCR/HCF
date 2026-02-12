package git.moiCR.hcf.lib.menu.misc;

import git.moiCR.hcf.lib.menu.button.Button;
import git.moiCR.hcf.lib.menu.paginated.MenuPaginated;
import git.moiCR.hcf.utils.ItemMaker;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public record PrevButton(MenuPaginated menu, Player player) implements Button {


    @Override
    public ItemStack getIcon() {
        return ItemMaker.of(Material.SKULL_ITEM)
                .setData(3)
                .setDisplayName(player.spigot().getLocale().startsWith("es") ? "&aPagina Anterior" : "&aPrevious Page")
                .setHeadTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGUxZGZjMTFhODM3MTExZDIyYjAwMWExNDQ2MWY5YTdmYzA5MzUyMmY4OGM1OGZhZWZkNmFkZWZmY2Q0ZTlhYiJ9fX0=")
                .addAllFlags()
                .build();
    }

    @Override
    public boolean isRemovable() {
        return false;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        menu.prev();
    }
}
