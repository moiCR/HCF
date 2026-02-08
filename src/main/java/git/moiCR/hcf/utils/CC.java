package git.moiCR.hcf.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class CC {

    public String t(String text) {
        return ChatColor.translateAlternateColorCodes('&',
                Optional.ofNullable(text).orElse("NULL"));
    }


    public List<String> t(String... texts){
        return t(Arrays.asList(texts));
    }

    public List<String> t(List<String> texts){
        return texts.stream().map(CC::t).toList();
    }
}
