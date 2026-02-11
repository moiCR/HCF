package git.moiCR.hcf.lib.prompt.type;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lib.prompt.Prompt;
import org.bukkit.entity.Player;

public class PromptInteger extends Prompt<Integer> {

    public PromptInteger(Main instance, Player player) {
        super(instance, player);
    }

    @Override
    public Integer getInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
