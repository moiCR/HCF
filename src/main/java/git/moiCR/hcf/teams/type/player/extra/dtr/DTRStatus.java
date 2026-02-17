package git.moiCR.hcf.teams.type.player.extra.dtr;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DTRStatus {

    NORMAL("&a", "■"),
    REGENERATING("&e", "▲"),
    FROZEN("&3", "❄"),
    RAIDABLE("&c", "⚔");

    private final String color;
    private final String icon;

}
