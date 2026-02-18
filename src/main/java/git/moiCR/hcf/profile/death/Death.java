package git.moiCR.hcf.profile.death;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;



@Data
@AllArgsConstructor
public class Death {

    private Location location;
    private ItemStack[] contents;
    private ItemStack[] armorContents;

}
