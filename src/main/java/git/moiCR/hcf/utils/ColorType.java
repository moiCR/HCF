package git.moiCR.hcf.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ColorType {

    BLACK(ChatColor.BLACK, DyeColor.BLACK),
    DARK_BLUE(ChatColor.DARK_BLUE, DyeColor.BLUE),
    DARK_GREEN(ChatColor.DARK_GREEN, DyeColor.GREEN),
    DARK_AQUA(ChatColor.DARK_AQUA, DyeColor.CYAN),
    DARK_RED(ChatColor.DARK_RED, DyeColor.RED),
    DARK_PURPLE(ChatColor.DARK_PURPLE, DyeColor.PURPLE),
    GOLD(ChatColor.GOLD, DyeColor.ORANGE),
    GRAY(ChatColor.GRAY, DyeColor.SILVER),
    DARK_GRAY(ChatColor.DARK_GRAY, DyeColor.GRAY),
    BLUE(ChatColor.BLUE, DyeColor.LIGHT_BLUE),
    GREEN(ChatColor.GREEN, DyeColor.LIME),
    AQUA(ChatColor.AQUA, DyeColor.LIGHT_BLUE),
    RED(ChatColor.RED, DyeColor.RED),
    LIGHT_PURPLE(ChatColor.LIGHT_PURPLE, DyeColor.MAGENTA),
    YELLOW(ChatColor.YELLOW, DyeColor.YELLOW),
    WHITE(ChatColor.WHITE, DyeColor.WHITE);

    private final ChatColor chatColor;
    private final DyeColor dyeColor;

    public static ColorType getByChatColor(ChatColor chatColor) {
        return Arrays.stream(values())
                .filter(type -> type.chatColor == chatColor)
                .findFirst()
                .orElse(WHITE);
    }

    public static DyeColor getDyeFromChat(ChatColor chatColor) {
        return getByChatColor(chatColor).getDyeColor();
    }
    public byte getWoolData() {
        return dyeColor.getWoolData();
    }
}