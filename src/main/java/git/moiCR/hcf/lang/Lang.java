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
    ),

    INVALID_ARGS(
            "&cInvalid arguments.",
            "&cArgumentos invalidos."
    ),

    ERROR_OCCURRED(
            "&cAn error occurred while executing this command.",
            "&cOcurrio un error al ejecutar este comando."
    ),

    USAGE(
            "&cUsage: %usage%",
            "&cUso: %usage%"
    ),

    OPERATION_CANCELLED(
            "&cOperation cancelled.",
            "&cOperacion cancelada."
    ),

    TEAM_ALREADY_EXISTS(
            "&cA team with that name already exists.",
            "&cUn equipo con ese nombre ya existe."
    ),

    TEAM_SUCCESSFULLY_CREATED(
            "&aTeam &e%team% &asuccessfully created.",
            "&aEquipo &e%team% &acreado exitosamente."
    ),

    PROCESS_CANCELLED(
            "&cProcess cancelled.",
            "&cProceso cancelado."
    ),

    PROCESS_ERROR(
            "&cAn error occurred while processing your request.",
            "&cOcurrio un error al procesar tu solicitud."
    ),

    PROMPT_EXPIRED(
            "&cYour prompt has expired.",
            "&cTu prompt ha expirado."
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
