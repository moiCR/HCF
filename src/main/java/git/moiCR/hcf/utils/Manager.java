package git.moiCR.hcf.utils;

import git.moiCR.hcf.Main;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@Getter
public abstract class Manager {

    private final Main instance;

    public Manager(Main instance) {
        this.instance = instance;
    }

    public void sendMessage(Player player, String message) {
        player.sendMessage(CC.t(message));
    }

    public void sendMessage(Player player, String... messages) {
        CC.t(messages).forEach(s -> sendMessage(player, s));
    }

    public void playSound(Player player, Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
    }

    public void playSound(Location location, Sound sound, float volume, float pitch) {
        location.getWorld().playSound(location, sound, volume, pitch);
    }

    public void playSound(Location location, Sound sound) {
        location.getWorld().playSound(location, sound, 1.0f, 1.0f);
    }


    public void load(){}
    public void unload(){}
}
