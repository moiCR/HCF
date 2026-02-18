package git.moiCR.hcf.lang;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Lang {

    NAME("name-value", "name"),
    DISPLAY_NAME("display-name-value", "displayName"),
    COLOR("color-value", "color"),
    CLAIM("claim-value", "claim"),
    ALREADY_IN_TEAM("already-in-team", "&cYou are already in a team."),
    TEAM_CREATED("team-created", "&eTeam &9&l%team% &ewas succesfully created by &f%player%&e."),
    LENGTH_ERROR("length-error", "&cThe %value% must be more than %minLength% characters long"),
    NOT_IN_TEAM("not-in-team", "&cYou are not in team."),
    CHAT_CHANGED("chat-changed", "&eYou now are in %chat% chat."),

    MEMBER_DEATH("member-death", "&cMember Death: &e%player% &7(-1.0 DTR)"),
    TEAM_FROZEN("team-frozen", "&cYour team's DTR is now &l%dtr% &cand is &lFROZEN &cfor %time%."),
    TEAM_RAIDABLE("team-raidable", "&4&lWARNING! &cYour team is now RAIDABLE!"),
    DTR_REGEN_START("dtr-regen-start", "&aYour team is no longer frozen and DTR will begin regenerating!"),
    DTR_REGEN_FULL("dtr-regen-full", "&aYour team has reached maximum DTR!"),

    // Points
    POINTS_LOSS("points-loss", "&cYour team lost &e%points% &cpoints!"),
    POINTS_GAIN("points-gain", "&aYour team gained &e%points% &apoints!"),

    TEAM_CHAT_FORMAT("team-chat-format", "&e(Team) &f%player%&7: &e%message%"),
    ALLY_CHAT_FORMAT("ally-chat-format", "&d(Ally) &f%player%&7: &d%message%"),

    FROM_CLAIM("from-claim", "&7From: %team% &7(%deathban%&7)"),
    TO_CLAIM("to-claim", "&7From: %team% &7(%deathban%&7)"),
    DEATH_BAN("deathban", "&cDeathban"),
    NON_DEATH_BAN("non-deathban", "&aNon-Deathban"),
    TEAM_WHO_INFO("team-who-info", "&cError in lang file"),

    BOTH_CORNERS("both-corners", "&cYou must select both corners of the claim."),

    TEAM_COMMAND_USAGE("team-command-usage", "&cError in lang file: team-command-usage"),
    TEAM_SYSTEM_COMMAND_USAGE("team-system-command-usage", "&cError in lang file: team-system-command-usage"),

    FIRST_CORNER_SELECTED("first-corner-selected", "&aFirst corner selected at &e%location%"),
    SECOND_CORNER_SELECTED("second-corner-selected", "&aSecond corner selected at &e%location%"),

    TEAM_NEW_CLAIM("team-new-claim", "&aCreate a new claim"),
    TEAM_NEW_CLAIM_LORE("team-new-claim-lore", "&eClick to create a new claim."),

    TEAM_CLAIM_LORE("team-claim-lore", "&eClick to teleport to this claim."),

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
    SELECT_TEAM_TYPE("select-team-type", "Please select the team type."),
    SELECT_TEAM_TO_EDIT("select-team-to-edit", "Select a team."),
    NEXT_PAGE("next-page", "&eNext page"),
    PREVIOUS_PAGE("previous-page", "&ePrevious page"),
    EDITING("editing", "Editing %name%'s"),
    NEW_TEAM_CREATE("new-team-create", "&aCreate a new team"),
    NEW_TEAM_CREATE_LORE("new-team-create-lore", "&eClick to create a new team."),
    CLICK_TO_CHANGE("click-to-change", "&aClick to change %value%"),
    CURRENT_VALUE("current-value", "&aCurrent value&7: %value%"),
    ENTER_NEW_VALUE("enter-new-value", "&aEnter new %value%"),
    SELECT_COLOR("select-color", "Select a color"),

    BACK_BUTTON("back-button", "&cBack"),
    CLICK_TO_MANAGE("click-to-manage", "Click to manage %value%");

    private final String path;
    private final String defaultMessage;

}