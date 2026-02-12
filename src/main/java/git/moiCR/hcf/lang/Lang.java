package git.moiCR.hcf.lang;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Lang {

    NO_PERMS("no-perms", "&c&lYou don't have permissions."),
    ONLY_PLAYER("only-player", "&c&lThis command is only executable in game."),
    UNKNOWN_COMMAND("unknown-command", "&c&lUnknown command."),
    INVALID_ARGS("invalid-args", "&c&lInvalid arguments."),
    ERROR_OCCURRED("error-occurred", "&c&lAn error occurred while executing this command."),
    USAGE("usage", "&c&lUsage: %usage%"),
    OPERATION_CANCELLED("operation-cancelled", "&c&lOperation cancelled."),
    TEAM_ALREADY_EXISTS("team-already-exists", "&c&lA team with that name already exists."),
    TEAM_SUCCESSFULLY_CREATED("team-successfully-created", "&a&lTeam &e%team% &asuccessfully created."),
    PROCESS_CANCELLED("process-cancelled", "&c&lProcess cancelled."),
    PROCESS_ERROR("process-error", "&cAn error occurred while processing your request."),
    PROMPT_EXPIRED("prompt-expired", "&cYour prompt has expired."),
    CLICK_TO_EDIT("click-to-edit", "&eClick to edit."),
    REDIRECTING("redirecting", "&aRedirecting.."),
    ENTER_TEAM_NAME("enter-team-name", "&aPlease enter the team name."),
    SELECT_TEAM_TYPE("select-team-type", "&aPlease select the team type.");

    private final String path;
    private final String defaultMessage;

}