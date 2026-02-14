package git.moiCR.hcf.lib.menu;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lib.menu.button.Button;
import git.moiCR.hcf.lib.menu.decoration.Decoration;
import git.moiCR.hcf.utils.CC;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;

@Getter
public abstract class Menu  {

    private final Player player;
    private final boolean autoUpdate;

    @Getter(AccessLevel.PRIVATE)
    private final BukkitTask task;

    @Setter
    private boolean soundOnClick;

    private Inventory inventory;

    @Setter
    private Decoration decoration;

    @Setter
    private boolean movePlayerInventory = false, moveMenu = false;

    private final Main instance;

    public Menu(Main instance, Player player, boolean autoUpdate){
        this.instance = instance;
        this.player = player;
        this.autoUpdate = autoUpdate;
        this.task = (autoUpdate ? Bukkit.getScheduler().runTaskTimerAsynchronously(instance, this::update, 0L, 100L) : null);
    }

    public void open(){
        var title = getTitle();
        var size = Math.min(getSize(), 54);

        if (title.length() > 32) title = title.substring(0, 32);
        if (size % 9 != 0) size += (9 - (size % 9));

        this.inventory = Bukkit.createInventory(null, size, CC.t(title));
        this.update();
        getInstance().getMenuHandler().registerMenu(player, this);
        player.openInventory(this.inventory);
    }

    public void update(){
        this.inventory.clear();
        this.setButtons();
    }

    public void clean(){
        getButtons().clear();
        this.inventory.clear();

        if (getTask() != null) task.cancel();

        getInstance().getMenuHandler().unregisterMenu(player);
    }

    protected void setButtons(){
        if (decoration != null) decoration.decorate(this);
        getButtons().forEach(((slot, button) -> this.inventory.setItem(slot, button.getIcon())));
    }

    public void redirect(Menu menu){
        if (getPlayer().getOpenInventory() != null){
            getPlayer().getOpenInventory().close();
        }

        menu.open();
    }

    public abstract String getTitle();
    public abstract int getSize();
    public abstract Map<Integer, Button> getButtons();
    public void onClose(){}
}
