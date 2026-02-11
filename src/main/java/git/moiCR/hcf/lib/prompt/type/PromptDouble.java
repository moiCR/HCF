package git.moiCR.hcf.lib.prompt.type;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lib.prompt.Prompt;
import org.bukkit.entity.Player;

public class PromptDouble extends Prompt<Double> {

    public PromptDouble(Main instance, Player player) {
        super(instance, player);
    }

    @Override
    public Double getInput(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
