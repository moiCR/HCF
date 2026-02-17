package git.moiCR.hcf.teams.menu.edit;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.lib.menu.Menu;
import git.moiCR.hcf.lib.menu.button.Button;
import git.moiCR.hcf.lib.menu.decoration.Decoration;
import git.moiCR.hcf.lib.menu.misc.BackButton;
import git.moiCR.hcf.lib.prompt.type.PromptString;
import git.moiCR.hcf.teams.Team;
import git.moiCR.hcf.teams.menu.TeamEditorMenu;
import git.moiCR.hcf.teams.menu.edit.extra.TeamManageClaims;
import git.moiCR.hcf.teams.menu.edit.extra.TeamSelectColorMenu;
import git.moiCR.hcf.utils.ColorType;
import git.moiCR.hcf.utils.ItemMaker;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TeamEditMenu extends Menu {

    private final Team team;

    public TeamEditMenu(Main instance, Player player, Team team) {
        super(instance, player, false);
        setDecoration(Decoration.FILL);
        setSoundOnClick(true);
        this.team = team;
    }

    @Override
    public String getTitle() {
        return LangHandler.INSTANCE.getMessage(getPlayer(), Lang.EDITING).replace("%team%", team.getName());
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public Map<Integer, Button> getButtons() {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(0, new IDButton());

        buttons.put(2, new NameButton());
        buttons.put(3, new DisplayNameButton());
        buttons.put(4, new ColorButton());
        buttons.put(5, new ClaimsButton());


        buttons.put(8, new BackButton(getPlayer(), this, new TeamEditorMenu(getInstance(), getPlayer())));
        return buttons;
    }


    private class IDButton implements Button {

        @Override
        public ItemStack getIcon() {
            return ItemMaker.of(Material.COMMAND)
                    .setDisplayName(ChatColor.YELLOW + team.getId().toString())
                    .setLore()
                    .addAllFlags()
                    .build();
        }

        @Override
        public boolean isRemovable() {
            return false;
        }

        @Override
        public void onClick(InventoryClickEvent event) {

        }
    }

    private class NameButton implements Button {

        @Override
        public ItemStack getIcon() {
            return ItemMaker.of(Material.SIGN)
                    .setDisplayName(LangHandler.INSTANCE.getMessage(getPlayer(), Lang.CLICK_TO_CHANGE)
                            .replace("%value%", LangHandler.INSTANCE.getMessage(getPlayer(), Lang.NAME)))

                    .setLore(LangHandler.INSTANCE.getMessageList(getPlayer(), Lang.CURRENT_VALUE)
                            .stream().map(s -> s.replace("%value%", team.getName())).toList())

                    .addAllFlags()
                    .build();
        }

        @Override
        public boolean isRemovable() {
            return false;
        }

        @Override
        public void onClick(InventoryClickEvent event) {
            var namePrompt = new PromptString(getInstance(), getPlayer());

            namePrompt.setPromptMessage(LangHandler.INSTANCE.getMessage(getPlayer(), Lang.ENTER_NEW_VALUE)
                    .replace("%value%", "name"));
            namePrompt.start();

            namePrompt.getFuture().thenAccept(name -> {
                team.setName(name);
                redirect(new TeamEditMenu(getInstance(), getPlayer(), team));
            }).exceptionally(ex -> {
                redirect(new TeamEditMenu(getInstance(), getPlayer(), team));
                return null;
            });

        }
    }

    private class DisplayNameButton implements Button {

        @Override
        public ItemStack getIcon() {
            return ItemMaker.of(Material.NAME_TAG)
                    .setDisplayName(LangHandler.INSTANCE.getMessage(getPlayer(), Lang.CLICK_TO_CHANGE)
                            .replace("%value%", "displayName"))

                    .setLore(LangHandler.INSTANCE.getMessageList(getPlayer(), Lang.CURRENT_VALUE)
                            .stream().map(s -> s.replace("%value%", team.getDisplayName())).toList())

                    .addAllFlags()
                    .build();
        }

        @Override
        public boolean isRemovable() {
            return false;
        }

        @Override
        public void onClick(InventoryClickEvent event) {
            var namePrompt = new PromptString(getInstance(), getPlayer());

            namePrompt.setPromptMessage(LangHandler.INSTANCE.getMessage(getPlayer(), Lang.ENTER_NEW_VALUE).
                    replace("%value%", LangHandler.INSTANCE.getMessage(getPlayer(), Lang.DISPLAY_NAME)));
            namePrompt.start();

            namePrompt.getFuture().thenAccept(displayName -> {
                team.setDisplayName(displayName);
                redirect(new TeamEditMenu(getInstance(), getPlayer(), team));
            }).exceptionally(ex -> {
                redirect(new TeamEditMenu(getInstance(), getPlayer(), team));
                return null;
            });
        }
    }

    private class ColorButton implements Button {

        @Override
        public ItemStack getIcon() {
            return ItemMaker.of(Material.WOOL)
                    .setData(ColorType.getByChatColor(team.getColor()).getWoolData())
                    .setDisplayName(LangHandler.INSTANCE.getMessage(getPlayer(), Lang.CLICK_TO_CHANGE)
                            .replace("%value%", "color"))

                    .setLore(LangHandler.INSTANCE.getMessageList(getPlayer(), Lang.CURRENT_VALUE)
                            .stream().map(s -> s.replace("%value%", team.getColor().name())).toList())

                    .addAllFlags()
                    .build();
        }

        @Override
        public boolean isRemovable() {
            return false;
        }

        @Override
        public void onClick(InventoryClickEvent event) {
            getPlayer().closeInventory();
            var menu = new TeamSelectColorMenu(getInstance(), getPlayer());
            redirect(menu);

            menu.getFuture().thenAccept(color -> {
               team.setColor(color.getChatColor());
               redirect(new TeamEditMenu(getInstance(), getPlayer(), team));
            }).exceptionally(ex -> {
                getPlayer().closeInventory();
                getPlayer().sendMessage(LangHandler.INSTANCE.getMessage(getPlayer(), Lang.OPERATION_CANCELLED));
                redirect(new TeamEditMenu(getInstance(), getPlayer(), team));
                return null;
            });
        }
    }

    private class ClaimsButton implements Button {

        @Override
        public ItemStack getIcon() {
            return ItemMaker.of(Material.COMPASS)
                    .setDisplayName(LangHandler.INSTANCE.getMessage(getPlayer(), Lang.CLICK_TO_MANAGE)
                            .replace("%value%", LangHandler.INSTANCE.getMessage(getPlayer(), Lang.CLAIM)))

                    .setLore(LangHandler.INSTANCE.getMessageList(getPlayer(), Lang.CURRENT_VALUE)
                            .stream().map(s -> s.replace("%value%", String.valueOf(team.getClaims().size()))).toList())

                    .addAllFlags()
                    .build();
        }

        @Override
        public boolean isRemovable() {
            return false;
        }

        @Override
        public void onClick(InventoryClickEvent event) {
            redirect(new TeamManageClaims(getInstance(), getPlayer(), team));
        }
    }
}
