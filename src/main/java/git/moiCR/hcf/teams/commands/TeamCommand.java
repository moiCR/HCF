package git.moiCR.hcf.teams.commands;

import git.moiCR.hcf.lib.command.Command;
import git.moiCR.hcf.lib.command.CommandManager;
import git.moiCR.hcf.teams.commands.args.staff.TeamSystemArg;

import java.util.List;

public class TeamCommand extends Command {

    public TeamCommand(CommandManager manager) {
        super("team");
        setAliases(List.of("t", "faction", "f", "fac", "factions", "teams", "clan", "clans"));
        setArguments(List.of(
                new TeamSystemArg(manager)
        ));
    }

    @Override
    public List<String> getUsage() {
        return List.of(
                "&7&m------------------------------------------------",
                "&6&lFaction Commands",
                "",
                "&7<> - Required",
                "&7[] - Optional",
                "",
                "&e* &6/f create <name> &7- Create a faction.",
                "&e* &6/f join <name> &7- Join a faction.",
                "&e* &6/f leave &7- Leave your current faction.",
                "&e* &6/f who [name] &7- View information about a faction.",
                "&e* &6/f deposit <amount>/all &7- Deposit money to the faction.",
                "",
                "&6&lCaptain Commands",
                "",
                "&e* &6/f claim &7- Start claim process",
                "&e* &6/f invite <player> &7- Invite a player to your faction.",
                "",
                "&e&lLeader Commands",
                "&e* &6/f disband &7- Disband your faction.",
                "&7&m------------------------------------------------"
        );
    }
}
