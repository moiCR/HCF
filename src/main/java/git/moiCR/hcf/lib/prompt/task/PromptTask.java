package git.moiCR.hcf.lib.prompt.task;

import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lib.prompt.Prompt;
import git.moiCR.hcf.lib.prompt.PromptHandler;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class PromptTask extends BukkitRunnable {

    private final PromptHandler handler;

    public PromptTask(PromptHandler handler) {
        this.handler = handler;
        this.runTaskTimerAsynchronously(handler.getInstance(), 20L, 20L);
    }

    @Override
    public void run() {
        if (handler.getPrompts().isEmpty()){
            return;
        }

        Iterator<Map.Entry<UUID, Prompt<?>>> iterator = handler.getPrompts().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, Prompt<?>> entry = iterator.next();
            Prompt<?> prompt = entry.getValue();
            var player = prompt.getPlayer();
            if (System.currentTimeMillis() - prompt.getTimeMillis() >= prompt.getTimeout()) {
                if (player.isOnline()) {
                    player.sendMessage(Lang.PROMPT_EXPIRED.get(player));
                }

                prompt.getFuture().completeExceptionally(new TimeoutException("Prompt timed out"));
                iterator.remove();
            }
        }
    }
}
