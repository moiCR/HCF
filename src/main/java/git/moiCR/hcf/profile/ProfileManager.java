package git.moiCR.hcf.profile;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.utils.Handler;
import org.bukkit.entity.Player;

import java.util.*;

public class ProfileManager extends Handler {

    private final Map<UUID, HCFProfile> profiles;

    public ProfileManager(Main instance) {
        super(instance);
        this.profiles = new HashMap<>();
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
