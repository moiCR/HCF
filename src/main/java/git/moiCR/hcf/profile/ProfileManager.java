package git.moiCR.hcf.profile;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lib.Handler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class ProfileManager extends Handler {

    private final Map<UUID, HCFProfile> profiles;

    public ProfileManager(Main instance) {
        super(instance);
        this.profiles = new HashMap<>();
    }

    @Override
    public void load() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            var profile = new HCFProfile(player);
            addProfile(profile);
        });
    }

    public HCFProfile findProfile(Player player){
        return profiles.get(player.getUniqueId());
    }

    public HCFProfile findProfile(UUID id){
        return profiles.get(id);
    }

    public HCFProfile findByName(String name){
        return  profiles.values().stream().filter(profile -> profile.getName().equals(name)).findFirst().orElse(null);
    }
    public void addProfile(HCFProfile profile){
        profiles.put(profile.getId(), profile);
    }

}
