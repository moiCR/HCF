package git.moiCR.hcf.utils;


import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class ItemMaker {

    private final ItemStack item;
    private final ItemMeta meta;

    private ItemMaker(ItemStack item) {
        this.item = item.clone();
        this.meta = item.getItemMeta();
    }

    private ItemMaker(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    private ItemMaker(String material) {
        Material mat = Material.getMaterial(material);
        this.item = new ItemStack(Objects.requireNonNullElse(mat, Material.STONE));
        this.meta = item.getItemMeta();
    }

    public static ItemMaker of(ItemStack item) {
        return new ItemMaker(item);
    }

    public static ItemMaker of(Material material) {
        return new ItemMaker(material);
    }

    public static ItemMaker of(String material) {
        return new ItemMaker(material);
    }

    public ItemMaker setDisplayName(String displayName) {
        meta.setDisplayName(CC.t(displayName));
        return this;
    }


    public ItemMaker setLore(List<String> lore) {
        meta.setLore(CC.t(lore));
        return this;
    }

    public ItemMaker setLore(String... lore) {
        meta.setLore(CC.t(lore));
        return this;
    }

    public ItemMaker setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemMaker addAllFlags() {
        meta.addItemFlags(ItemFlag.values());
        return this;
    }

    public ItemMaker addFlag(ItemFlag flag) {
        meta.addItemFlags(flag);
        return this;
    }

    public ItemMaker removeFlag(ItemFlag flag) {
        meta.removeItemFlags(flag);
        return this;
    }

    public ItemMaker removeAllFlags() {
        meta.removeItemFlags(ItemFlag.values());
        return this;
    }

    public ItemMaker setArmorColor(Color color) {
        if (meta instanceof LeatherArmorMeta leatherMeta) {
            leatherMeta.setColor(color);
        }
        return this;
    }

    public ItemMaker setOwner(String name) {
        if (item.getType() == Material.SKULL_ITEM && item.getDurability() == 3
                && meta instanceof SkullMeta skullMeta) {
            skullMeta.setOwner(name);
        }
        return this;
    }

    public ItemMaker setData(int data) {
        item.setDurability((short) data);
        return this;
    }

    public ItemMaker setUnbreakable(boolean unbreakable) {
        meta.spigot().setUnbreakable(unbreakable);
        return this;
    }

    public ItemMaker addEnchant(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}