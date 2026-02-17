package git.moiCR.hcf.listener;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lib.Manager;
import git.moiCR.hcf.listener.impl.GeneralListener;
import git.moiCR.hcf.profile.listener.ProfileListener;
import git.moiCR.hcf.teams.claim.listener.ClaimListener;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class ListenerManager extends Manager {

    private final List<Listener> listeners;

    public ListenerManager(Main instance) {
        super(instance);
        this.listeners = new ArrayList<>();
    }

    @Override
    public void load() {
        listeners.addAll(List.of(
                new GeneralListener(getInstance()),
                new ProfileListener(getInstance()),
                new ClaimListener(getInstance())
        ));


        listeners.forEach(listener -> getInstance().getServer().getPluginManager().registerEvents(listener, getInstance()));
    }

    @Override
    public void unload() {
        listeners.forEach(HandlerList::unregisterAll);

    }


}
