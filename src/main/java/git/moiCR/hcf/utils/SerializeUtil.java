package git.moiCR.hcf.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@UtilityClass
public class SerializeUtil {

    public String itemsToBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(items.length);

            for (ItemStack item : items) {
                dataOutput.writeObject(item);
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public ItemStack[] itemsFromBase64(String data) throws Exception {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new Exception("Unable to decode class type.", e);
        }
    }


    public String serializeLocation(Location location){
        return location.getWorld().getName() + ":"
                + location.getX() + ":"
                + location.getY() + ":"
                + location.getZ();
    }

    public Location deserializeLocation(String serializedLocation){
        var parts = serializedLocation.split(":");
        var world = Bukkit.getWorld(parts[0]);
        var x = Double.parseDouble(parts[1]);
        var y = Double.parseDouble(parts[2]);
        var z = Double.parseDouble(parts[3]);
        return new Location(world, x, y, z);
    }
}
