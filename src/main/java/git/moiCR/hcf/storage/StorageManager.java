package git.moiCR.hcf.storage;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lib.Manager;
import git.moiCR.hcf.storage.impl.MongoStorage;
import lombok.Getter;

@Getter
public class StorageManager extends Manager {

    private final IStorage storage;

    public StorageManager(Main instance) {
        super(instance);
        this.storage = new MongoStorage(instance, instance.getConfig().getString("mongo_uri"));
    }


    @Override
    public void load() {
        storage.load();
    }

    @Override
    public void unload() {
        storage.unload();
    }
}
