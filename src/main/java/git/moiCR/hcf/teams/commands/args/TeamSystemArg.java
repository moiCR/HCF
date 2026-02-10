package git.moiCR.hcf.teams.commands.args;

import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lib.command.Argument;
import git.moiCR.hcf.lib.command.CommandManager;
import git.moiCR.hcf.teams.Team;
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

            selectTypeMenu.getFuture().thenAccept(teamType -> {
                if (teamType == null){
                    sender.sendMessage(Lang.OPERATION_CANCELLED.get(sender));
                    return;
                }

                Team team = null;
                switch (teamType) {
                    case ROAD -> team = new TeamRoad(name);
                    case SAFE_ZONE -> team = new TeamSafezone(name);
                }

                if (team == null){
                    sender.sendMessage(Lang.ERROR_OCCURRED.get(sender));
                    return;
                }

                getManager().getInstance().getTeamManager().getTeams().add(team);
                sender.sendMessage(Lang.TEAM_SUCCESSFULLY_CREATED.get(sender).replace("%team%", team.getName()));
            });
            return;
        }
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
                "&e* &6/f system create <name> <type> &7- Create a faction.",
                "&e* &6/f system claim <name> &7- Claim land for a faction.",
                "&e* &6/f system edit <name> &7- Open the faction editor menu.",
                "&e* &6/f system editor &7- Open the faction editor menu.",
                "&e* &6/f system delete <name> &7- Delete a faction.",
                "&7&m------------------------------------------------"
        );
    }
}
