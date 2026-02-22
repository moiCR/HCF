package git.moiCR.hcf.location;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lib.Manager;
import git.moiCR.hcf.utils.SerializeUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter
public class LocationManager extends Manager {

    private Location overworldSpawn;
    private Location netherSpawn;
    private Location endSpawn;
    private Location endExit;


    public LocationManager(Main instance) {
        super(instance);
    }

    @Override
    public void load() {
        var config = getInstance().getConfig();

        if (config.contains("locations.overworld-spawn")) {
            this.overworldSpawn = SerializeUtil.deserializeLocation(config.getString("locations.overworld-spawn"));
        }

        if (config.contains("locations.nether-spawn")) {
            this.netherSpawn = SerializeUtil.deserializeLocation(config.getString("locations.nether-spawn"));
        }

        if (config.contains("locations.end-spawn")) {
            this.endSpawn = SerializeUtil.deserializeLocation(config.getString("locations.end-spawn"));
        }

        if (config.contains("locations.end-exit")) {
            this.endExit = SerializeUtil.deserializeLocation(config.getString("locations.end-exit"));
        }

    }

    @Override
    public void unload() {
        saveLocation(overworldSpawn, LocationEnum.OVERWORLD_SPAWN);
        saveLocation(netherSpawn, LocationEnum.NETHER_SPAWN);
        saveLocation(endSpawn, LocationEnum.END_SPAWN);
        saveLocation(endExit, LocationEnum.END_EXIT);
    }

    public void saveLocation(Location location, LocationEnum type) {
        var config = getInstance().getConfig();

        if (location == null) {
            return;
        }

        String path = switch (type) {
            case OVERWORLD_SPAWN -> "locations.overworld-spawn";
            case NETHER_SPAWN -> "locations.nether-spawn";
            case END_SPAWN -> "locations.end-spawn";
            case END_EXIT -> "locations.end-exit";
        };

        config.set(path, SerializeUtil.serializeLocation(location));
        updateInternalVariable(location, type);

        config.save();
    }

    private void updateInternalVariable(Location location, LocationEnum type) {
        switch (type) {
            case OVERWORLD_SPAWN -> this.overworldSpawn = location;
            case NETHER_SPAWN -> this.netherSpawn = location;
            case END_SPAWN -> this.endSpawn = location;
            case END_EXIT -> this.endExit = location;
        }
    }
}
