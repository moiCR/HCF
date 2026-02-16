package git.moiCR.hcf.lib.menu;
import git.moiCR.hcf.Main;
import git.moiCR.hcf.lib.Handler;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class MenuHandler extends Handler {

    private final Map<UUID, Menu> openedMenus;

    public MenuHandler(Main instance) {
        super(instance);
        this.openedMenus = new HashMap<>();
    }

    public Optional<Menu> getOpenedMenu(Player player){
        return Optional.ofNullable(openedMenus.get(player.getUniqueId()));
    }

    public void registerMenu(Player player, Menu menu){
        openedMenus.put(player.getUniqueId(), menu);
    }

    public void unregisterMenu(Player player){
        openedMenus.remove(player.getUniqueId());
    }


    @Override
    public Listener getEvents() {
        return new Listener() {

            @EventHandler
            public void onClick(InventoryClickEvent event){
                var player = (Player) event.getWhoClicked();
                var optMenu = getOpenedMenu(player);
                optMenu.ifPresent(menu -> {
                    var buttons = menu.getButtons();
                    event.setCancelled(!menu.isMoveMenu());

                    buttons.forEach((slot, button) ->{
                        if (slot == event.getSlot()){
                            button.onClick(event);
                            event.setCancelled(!button.isRemovable());

                            if (menu.isSoundOnClick()) playSound(player, Sound.CLICK);
                        }
                    });
                });
            }

            @EventHandler
            public void onClose(InventoryCloseEvent event){
                var player = (Player) event.getPlayer();
                var optMenu = getOpenedMenu(player);

                optMenu.ifPresent(menu -> {
                    menu.onClose();
                    menu.clean();
                });
            }

        };
    }
}
