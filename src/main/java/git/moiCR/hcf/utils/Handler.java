package git.moiCR.hcf.utils;

import git.moiCR.hcf.Main;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

@Getter
public abstract class Handler extends Manager {

    public Handler(Main instance) {
        super(instance);
        if (getEvents() != null) Bukkit.getPluginManager().registerEvents(getEvents(), instance);
    }

    public Listener getEvents() {
        return null;
    }

    public abstract void load();
    public abstract void unload();

}