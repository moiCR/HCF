package git.moiCR.hcf.teams.commands.args;

import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lib.command.Argument;
import git.moiCR.hcf.lib.command.CommandManager;
import git.moiCR.hcf.teams.Team;
import git.moiCR.hcf.teams.menu.TeamSelectToEditMenu;
import git.moiCR.hcf.teams.menu.TeamSelectTypeMenu;
import git.moiCR.hcf.teams.type.system.TeamRoad;
import git.moiCR.hcf.teams.type.system.TeamSafezone;
import git.moiCR.hcf.utils.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamSystemArg extends Argument {

    public TeamSystemArg(CommandManager manager) {
        super(manager, List.of("system", "sys"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("hcf.command.team.system")){
            sender.sendMessage(Lang.NO_PERMS.get(sender));
            return;
        }

        if (args.length == 0){
            getUsage().forEach(s -> sender.sendMessage(CC.t(s)));
            return;
        }

        if (args[0].equalsIgnoreCase("create")){
            if (args.length < 2){
                sender.sendMessage(Lang.INVALID_ARGS.get(sender));
                return;
            }

            String name = args[1];
            if (getManager().getInstance().getTeamManager().getTeam(name) != null){
                sender.sendMessage(Lang.TEAM_ALREADY_EXISTS.get(sender));
                return;
            }

            var selectTypeMenu = new TeamSelectTypeMenu(getManager().getInstance(), (Player) sender);
            selectTypeMenu.open();

            selectTypeMenu.getFuture().thenAccept(teamType -> getManager().getInstance().getTeamManager().createTeam((Player) sender, name, teamType));
            return;
        }

        if (args[0].equalsIgnoreCase("editor")){
            new TeamSelectToEditMenu(getManager().getInstance(), (Player) sender).open();
            return;
        }

        getUsage().forEach(s -> sender.sendMessage(CC.t(s)));
    }

    @Override
    public List<String> getUsage() {
        return List.of(
                "&7&m------------------------------------------------",
                "&6&lFaction System Commands",
                "",
                "&7<> - Required",
                "&7[] - Optional",
                "",
                "&e* &6/t system create <name> &7- Create a team.",
                "&e* &6/t system claim <name> &7- Claim land for a team.",
                "&e* &6/t system edit <name> &7- Open the team editor menu.",
                "&e* &6/t system editor &7- Open the team editor menu.",
                "&e* &6/t system delete <name> &7- Delete a team.",
                "&7&m------------------------------------------------"
        );
    }
}
