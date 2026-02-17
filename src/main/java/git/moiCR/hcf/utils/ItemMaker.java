package git.moiCR.hcf.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
        this.item = new ItemStack(
            Objects.requireNonNullElse(mat, Material.STONE)
        );
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
        if (
            item.getType() == Material.SKULL_ITEM &&
            item.getDurability() == 3 &&
            meta instanceof SkullMeta skullMeta
        ) {
            skullMeta.setOwner(name);
        }
        return this;
    }

    public ItemMaker setHeadTexture(String textureValue) {
        if (textureValue == null || textureValue.isEmpty()) return this;

        if (
            item.getType() != Material.SKULL_ITEM ||
            !(meta instanceof SkullMeta skullMeta)
        ) return this;

        final UUID uuid = new UUID(
            textureValue.hashCode(),
            textureValue.hashCode()
        );
        final GameProfile profile = new GameProfile(uuid, null);
        PropertyMap map = profile.getProperties();

        map.put("texture", new Property("textures", textureValue));

        try {
            Field field = skullMeta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(skullMeta, profile);
        } catch (Exception e) {
            Bukkit.getLogger().warning(
                "Could not set head texture: " + e.getMessage()
            );
            return this;
        }

        return this;
    }

    public ItemMaker setHeadTextureFromURL(String urlString) {
        if (urlString == null || urlString.isEmpty()) return this;
        if (item.getType() != Material.SKULL_ITEM) return this;

        SkullMeta skullMeta = (SkullMeta) meta;
        String json =
            "{ \"textures\": { \"SKIN\": { \"url\": \"" +
            urlString +
            "\" } } }";

        String base64Encoded = Base64.getEncoder().encodeToString(
            json.getBytes()
        );

        UUID uuid = UUID.nameUUIDFromBytes(urlString.getBytes());
        GameProfile profile = new GameProfile(uuid, null);
        profile
            .getProperties()
            .put("textures", new Property("textures", base64Encoded));
        try {
            Field field = skullMeta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(skullMeta, profile);
        } catch (Exception e) {
            Bukkit.getLogger().warning(
                "Error setting head texture from URL: " + e.getMessage()
            );
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
