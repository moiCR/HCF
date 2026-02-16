package git.moiCR.hcf.lib.command;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.teams.commands.TeamCommand;
import git.moiCR.hcf.lib.Manager;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CommandManager extends Manager {

    private final CommandMap commandMap;
    private final Set<Command> commands;

    public CommandManager(Main instance) {
        super(instance);
        this.commandMap = getInstance().getServer().getCommandMap();
        this.commands = new HashSet<>();
    }

    @Override
    public void load() {
        this.commands.addAll(List.of(
                new TeamCommand(this)
        ));
        this.commands.forEach(this::registerCommand);
    }

    public void registerCommand(Command command) {
        if (commandMap == null) {
            return;
        }

        PluginCommand pluginCommand = newCommand(command.getName());
        if (pluginCommand == null) {
            return;
        }

        pluginCommand.setAliases(command.getAliases());
        pluginCommand.setTabCompleter((sender, cmd, label, args) -> command.onTabComplete(sender, args));
        pluginCommand.setExecutor((sender, cmd, label, args) -> executeCommand(sender, command, args));

        commandMap.register(getInstance().getName().toLowerCase(), pluginCommand);
    }

    public boolean executeCommand(CommandSender sender, Command command, String[] args){
        if (command == null){
            sender.sendMessage(LangHandler.INSTANCE.getMessage((sender instanceof Player player) ? player : null , Lang.UNKNOWN_COMMAND));
            return false;
        }

        if (!command.getPermission().isEmpty() && !sender.hasPermission(command.getPermission())){
            sender.sendMessage(LangHandler.INSTANCE.getMessage((sender instanceof Player player) ? player : null , Lang.NO_PERMS));
            return false;
        }

        command.onExecute(sender, args);
        return true;
    }

    private PluginCommand newCommand(String name) {
        try {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, org.bukkit.plugin.Plugin.class);
            constructor.setAccessible(true);
            return constructor.newInstance(name, getInstance());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void unload() {

    }
}
