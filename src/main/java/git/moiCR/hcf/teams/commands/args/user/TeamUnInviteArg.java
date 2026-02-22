package git.moiCR.hcf.teams.commands.args.user;

import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.lib.command.Argument;
import git.moiCR.hcf.lib.command.CommandManager;
import git.moiCR.hcf.profile.Profile;
import git.moiCR.hcf.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamUnInviteArg extends Argument {

    public TeamUnInviteArg(CommandManager manager) {
        super(manager, List.of("uninvite"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        var player = (Player) sender;
        var team = getManager().getInstance().getTeamManager().getTeamByPlayer(player);

        if (team == null){
            player.sendMessage(LangHandler.INSTANCE.getMessage(player, Lang.NOT_IN_TEAM));
            return;
        }

        if (args.length == 0){
            getUsage(sender).forEach(line -> player.sendMessage(CC.t(line)));
            return;
        }

        String name = args[0];
        Profile profile = getManager().getInstance().getProfileManager().findProfile(Bukkit.getPlayer(name));

        if (profile == null){
            return;
        }

        var invite = profile.getInvite(team);

        if (invite == null){

            return;
        }

        profile.getInvites().remove(invite);
        player.sendMessage(CC.t("&aThe invitation has been removed."));
    }

    @Override
    public List<String> getUsage(CommandSender sender) {
        return List.of(LangHandler.INSTANCE.getMessage((Player) sender, Lang.USAGE)
                .replace("%usage%", "/t uninvite <invited-player>")
        );
    }
}
