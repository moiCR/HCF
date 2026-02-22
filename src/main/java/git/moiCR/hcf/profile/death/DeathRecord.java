package git.moiCR.hcf.profile.death;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;


@Data
public class DeathRecord {

    private UUID id;
    private Location location;
    private ItemStack[] contents;
    private ItemStack[] armorContents;

    public DeathRecord(Location location, ItemStack[] contents, ItemStack[] armorContents){
        this.id = UUID.randomUUID();
        this.location = location;
        this.contents = contents;
        this.armorContents = armorContents;
    }

    public DeathRecord(UUID id, Location location, ItemStack[] contents, ItemStack[] armorContents) {
        this.id = id;
        this.location = location;
        this.contents = contents;
        this.armorContents = armorContents;
    }

}
