package git.moiCR.hcf.lib.prompt.type;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lib.prompt.Prompt;
import org.bukkit.entity.Player;

public class PromptString extends Prompt<String> {

    public PromptString(Main instance, Player player) {
        super(instance, player);
    }

    @Override
    public String getInput(String input) {
        return (input == null || input.isEmpty()) ? null : input;
    }

}
