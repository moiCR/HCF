package git.moiCR.hcf.teams.commands.args.user;

import git.moiCR.hcf.lib.command.Argument;
import git.moiCR.hcf.lib.command.CommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamWhoArg extends Argument {

    public TeamWhoArg(CommandManager manager) {
        super(manager, List.of("who", "i", "info", "show"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        var player = (Player) sender;
        if (args.length == 0){
            var teamPlayer = getManager().getInstance().getTeamManager().getTeamByPlayer(player);
            if (teamPlayer == null){
                return;
            }
            teamPlayer.sendInfo(player);
            return;
        }

        String teamName = args[0];
        var team = getManager().getInstance().getTeamManager().getPlayerTeam(teamName);
        if (team == null){
            return;
        }

        team.sendInfo(player);
    }

    @Override
    public List<String> getUsage(CommandSender sender) {
        return List.of();
    }
}
