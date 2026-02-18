package git.moiCR.hcf.teams.commands.args.user;

import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.lib.command.Argument;
import git.moiCR.hcf.lib.command.CommandManager;
import git.moiCR.hcf.teams.type.player.extra.chat.ChatChannel;
import git.moiCR.hcf.utils.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamChatArg extends Argument {

    public TeamChatArg(CommandManager manager) {
        super(manager, List.of("chat", "c"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        var player = (Player) sender;
        var playerTeam = getManager().getInstance().getTeamManager().getTeamByPlayer(player);

        if (playerTeam == null){
            player.sendMessage(LangHandler.INSTANCE.getMessage(player, Lang.NOT_IN_TEAM));
            return;
        }

        var member = playerTeam.getMember(player.getUniqueId());

        if (args.length == 0){
            member.setChatChannel(ChatChannel.GLOBAL);
            player.sendMessage(LangHandler.INSTANCE.getMessage(player, Lang.CHAT_CHANGED).replace("%chat%", ChatChannel.GLOBAL.name().toLowerCase()));
            return;
        }

        if (args[0].equalsIgnoreCase("faction")
                || args[0].equalsIgnoreCase("f")
                || args[0].equalsIgnoreCase("team")
                || args[0].equalsIgnoreCase("t")){

            member.setChatChannel(ChatChannel.TEAM);
            player.sendMessage(LangHandler.INSTANCE.getMessage(player, Lang.CHAT_CHANGED).replace("%chat%", ChatChannel.TEAM.name().toLowerCase()));
            return;
        }

        if (args[0].equalsIgnoreCase("ally")
                || args[0].equalsIgnoreCase("a")
                || args[0].equalsIgnoreCase("allie")
                || args[0].equalsIgnoreCase("allies")){

            member.setChatChannel(ChatChannel.ALLY);
            player.sendMessage(LangHandler.INSTANCE.getMessage(player, Lang.CHAT_CHANGED).replace("%chat%", ChatChannel.ALLY.name().toLowerCase()));
            return;
        }
    }

    @Override
    public List<String> getUsage(CommandSender sender) {
        return List.of();
    }
}
