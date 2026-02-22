package git.moiCR.hcf.teams.commands.args.user;

import git.moiCR.hcf.api.events.TeamPlayerCreateEvent;
import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.lib.command.Argument;
import git.moiCR.hcf.lib.command.CommandManager;
import git.moiCR.hcf.teams.type.player.TeamPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamCreateArg extends Argument {

    public TeamCreateArg(CommandManager manager) {
        super(manager, List.of("create"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        var player = (Player) sender;
        var currentTeam = getManager().getInstance().getTeamManager().getTeamByPlayer(player);

        if (currentTeam != null){
            player.sendMessage(LangHandler.INSTANCE.getMessage(player, Lang.ALREADY_IN_TEAM));
            return;
        }

        if (args.length == 0){
            return;
        }

        String name = args[0];

        if (name.length() <= 2){
            player.sendMessage(LangHandler.INSTANCE.getMessage(player, Lang.LENGTH_ERROR)
                    .replace("%value%", LangHandler.INSTANCE.getMessage(player, Lang.NAME))
                    .replace("%minLength%", String.valueOf(2)));
            return;
        }

        var teamPlayer = new TeamPlayer(name, player);
        var event = new TeamPlayerCreateEvent(player, teamPlayer);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()){
            return;
        }

        getManager().getInstance().getTeamManager().getTeams().add(teamPlayer);
        Bukkit.broadcastMessage(LangHandler.INSTANCE.getMessage(player, Lang.TEAM_CREATED)
                .replace("%team%", name)
                .replace("%player%", player.getName())
        );
    }

    @Override
    public List<String> getUsage(CommandSender sender) {
        return List.of("&cUsage: /t create <name>");
    }
}
