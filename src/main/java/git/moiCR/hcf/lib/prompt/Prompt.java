package git.moiCR.hcf.lib.prompt;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.utils.CC;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

@Getter @Setter
public abstract class Prompt<T> {

    private final Main instance;
    private final Player player;
    private String promptMessage;
    private final long timeMillis;
    private final long timeout;
    private final CompletableFuture<T> future;

    public Prompt(Main instance, Player player) {
        this.instance = instance;
        this.player = player;
        this.timeMillis = System.currentTimeMillis();
        this.timeout = 20 * 1000L;
        this.future = new CompletableFuture<>();
    }

    public abstract T getInput(String input);

    public boolean tryComplete(String textInput) {
        T result = getInput(textInput);

        if (result == null) {
            return false;
        }

        future.complete(result);
        getInstance().getPromptHandler().playSound(player, Sound.LEVEL_UP);
        return true;
    }

    public void start(){
        if (player.getOpenInventory() != null) {
            player.closeInventory();
        }

        player.sendMessage(CC.t(promptMessage));
        getInstance().getPromptHandler().playSound(player, Sound.LEVEL_UP);
        getInstance().getPromptHandler().getPrompts().put(getPlayer().getUniqueId(), this);
    }
}
