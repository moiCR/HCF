package git.moiCR.hcf.lang;

import git.moiCR.hcf.utils.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum Lang {

    NO_PERMS(
            "&cNo perms.",
            "&c&lNo tienes permisos."
    ),

    ONLY_PLAYER(
            "&cThis command is only executable in game.",
            "&cEste commando solo puede ejecutarse dentro del juego."
    ),

    UNKNOWN_COMMAND(
            "&cUnknown command.",
            "&cComando desconocido."
    );

    private final String english;
    private final String spanish;


    Lang(String english, String spanish) {
        this.english = english;
        this.spanish = spanish;
    }

    public String get(Player player){
        if (player == null) {return  CC.t(english);}
        var local = player.spigot().getLocale();
        return local.startsWith("es") ? CC.t(spanish) : CC.t(english);
    }

    public String get(CommandSender sender){
        if (sender == null) {return  CC.t(english);}
        if (sender instanceof Player player){return get(player);}
        return CC.t(english);
    }

}
