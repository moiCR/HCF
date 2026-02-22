package git.moiCR.hcf.teams.commands;

import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.lib.command.Command;
import git.moiCR.hcf.lib.command.CommandManager;
import git.moiCR.hcf.teams.commands.args.staff.TeamSystemArg;
import git.moiCR.hcf.teams.commands.args.user.*;
import org.bukkit.command.CommandSender;

import java.util.List;

public class TeamCommand extends Command {

    public TeamCommand(CommandManager manager) {
        super("team");
        setAliases(List.of("t", "faction", "f", "fac", "factions", "teams", "clan", "clans"));
        setArguments(List.of(
                new TeamSystemArg(manager),
                new TeamCreateArg(manager),
                new TeamWhoArg(manager),
                new TeamChatArg(manager),
                new TeamInviteArg(manager),
                new TeamUnInviteArg(manager)
        ));
    }

    @Override
    public List<String> getUsage(CommandSender sender) {
        return LangHandler.INSTANCE.getMessageList(getPlayer(sender), Lang.TEAM_COMMAND_USAGE);
    }
}
