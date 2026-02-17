package git.moiCR.hcf.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class Constants {

    public ItemStack SYSTEM_WAND = ItemMaker.of(Material.DIAMOND_AXE)
            .setDisplayName("&6System Wand")
            .setLore(
                    "&7&m---------------------------------",
                    "&eLeft-Click Air and Shift &fto confirm process.",
                    "&eLeft-Click Block &fto select first corner.",
                    "&eRight-Click Block &fto select second corner.",
                    "&6Right-Click Air &fto cancel process.",
                    "&7&m---------------------------------"
            )
            .addAllFlags()
            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
            .setUnbreakable(true)
            .build();
}
