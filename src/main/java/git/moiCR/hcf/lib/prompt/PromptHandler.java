package git.moiCR.hcf.lib.prompt;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.lib.Handler;
import git.moiCR.hcf.lib.prompt.task.PromptTask;
import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class PromptHandler extends Handler {

    private final Map<UUID, Prompt<?>> prompts;

    public PromptHandler(Main instance) {
        super(instance);
        this.prompts = new ConcurrentHashMap<>();
        new PromptTask(this);
    }

    @Override
    public Listener getEvents() {
        return new Listener() {

            @EventHandler
            public void onChat(AsyncPlayerChatEvent event){
                var player = event.getPlayer();
                var message = event.getMessage();

                if (!prompts.containsKey(player.getUniqueId())) {
                    return;
                }

                event.setCancelled(true);
                if (message.equalsIgnoreCase("cancel") || message.equalsIgnoreCase("exit")) {
                    player.sendMessage(LangHandler.INSTANCE.getMessage(player, Lang.PROCESS_CANCELLED));
                    prompts.remove(player.getUniqueId());
                    return;
                }

                var prompt = prompts.get(player.getUniqueId());
                if (!prompt.tryComplete(message)) {
                    player.sendMessage(LangHandler.INSTANCE.getMessage(player, Lang.PROCESS_ERROR));
                    return;
                }

                prompts.remove(player.getUniqueId());
            }
        };
    }
}
