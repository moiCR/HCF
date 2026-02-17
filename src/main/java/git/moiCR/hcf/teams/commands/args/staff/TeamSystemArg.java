package git.moiCR.hcf.teams.commands.args.staff;

import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.lib.command.Argument;
import git.moiCR.hcf.lib.command.CommandManager;
import git.moiCR.hcf.teams.menu.TeamEditorMenu;
import git.moiCR.hcf.teams.menu.TeamSelectTypeMenu;
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
            sender.sendMessage(LangHandler.INSTANCE.getMessage((Player) sender, Lang.NO_PERMS));
            return;
        }

        if (args.length == 0){
            getUsage().forEach(s -> sender.sendMessage(CC.t(s)));
            return;
        }

        if (args[0].equalsIgnoreCase("create")){
            if (args.length < 2){
                sender.sendMessage(LangHandler.INSTANCE.getMessage((Player) sender, Lang.INVALID_ARGS));
                return;
            }

            String name = args[1];
            if (getManager().getInstance().getTeamManager().getTeam(name) != null){
                sender.sendMessage(LangHandler.INSTANCE.getMessage((Player) sender, Lang.TEAM_ALREADY_EXISTS));
                return;
            }

            var selectTypeMenu = new TeamSelectTypeMenu(getManager().getInstance(), (Player) sender);
            selectTypeMenu.open();

            selectTypeMenu.getFuture().thenAccept(teamType ->
                    getManager().getInstance().getTeamManager().createTeam((Player) sender, name, teamType)
            ).exceptionally(ex -> {
                sender.sendMessage(LangHandler.INSTANCE.getMessage((Player) sender, Lang.OPERATION_CANCELLED));
                return null;
            });
            return;
        }

        if (args[0].equalsIgnoreCase("editor")){
            new TeamEditorMenu(getManager().getInstance(), (Player) sender).open();
            return;
        }

        getUsage().forEach(s -> sender.sendMessage(CC.t(s)));
    }

    @Override
    public List<String> getUsage() {
        return List.of(
                "&7&m------------------------------------------------",
                "&6&lTeam System Commands",
                "",
                "&7<> - Required",
                "&7[] - Optional",
                "",
                "&e* &6/t system create <name> &7- Create a team.",
                "&e* &6/t system editor &7- Open the team editor menu.",
                "&7&m------------------------------------------------"
        );
    }
}
