package git.moiCR.hcf.lib.command;

import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.utils.CC;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter @Setter
public abstract class Command {

    private String name;
    private String permission;
    private List<String> aliases;
    private List<Argument> arguments;
    private boolean onlyPlayer;

    public Command(String name) {
        this.name = name;
        this.permission = "";
        this.aliases = new ArrayList<>();
        this.arguments = new ArrayList<>();
        this.onlyPlayer = true;
    }

    /**
     * Executes the command logic when no specific parameter matches the input.
     *
     * @param sender The sender executing the command.
     * @param args   The arguments provided with the command.
     */
    protected void execute(CommandSender sender, String[] args){}
    public abstract List<String> getUsage();

    public void onExecute(CommandSender sender, String[] args){
        if (isOnlyPlayer() && !(sender instanceof Player)){
            sender.sendMessage(CC.t(LangHandler.INSTANCE.getMessage(null, Lang.ONLY_PLAYER)));
            return;
        }

        if (arguments.isEmpty()){
            execute(sender, args);
            return;
        }

        if (args.length == 0){
            sendUsage(sender);
            return;
        }

        Optional<Argument> optParameter = getParameter(args[0]);
        optParameter.ifPresent(parameter -> parameter.execute(sender, Arrays.copyOfRange(args, 1, args.length)));

    }

    public Player getPlayer(CommandSender sender) {
        return sender instanceof Player ? (Player) sender : null;
    }

    /**
     * Sends the usage instructions for the command to the sender.
     *
     * @param sender The sender requesting the usage information.
     */
    public void sendUsage(CommandSender sender){
        if (getUsage() == null || getUsage().isEmpty()){
            return;
        }

        getUsage().forEach(line -> sender.sendMessage(CC.t(line)));
    }

    public Optional<Argument> getParameter(String name) {
        return arguments.stream()
                .filter(param -> param.getNames().stream()
                        .anyMatch(paramName -> paramName.equalsIgnoreCase(name)))
                .findFirst();
    }

    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }



}
