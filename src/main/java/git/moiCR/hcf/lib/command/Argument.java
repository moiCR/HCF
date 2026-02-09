package git.moiCR.hcf.lib.command;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.util.List;

@Getter @Setter
public abstract class Argument {

    private List<String> names;

    public Argument(CommandManager manager, List<String> names) {
        this.names = names;
    }

    public abstract void execute(CommandSender sender, String[] args);
    public abstract List<String> getUsage();

}
