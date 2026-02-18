package git.moiCR.hcf.listener.impl;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lang.Lang;
import git.moiCR.hcf.lang.LangHandler;
import git.moiCR.hcf.teams.type.player.extra.chat.ChatChannel;
import git.moiCR.hcf.teams.type.player.extra.member.Member;
import git.moiCR.hcf.utils.CC;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class ChatListener implements Listener {

    private final Main plugin;

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage("");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent event){
        event.setQuitMessage("");
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncChat(AsyncPlayerChatEvent event){
        var format = plugin.getConfig().getString("chat-format");
        var player = event.getPlayer();
        var message = event.getMessage();

        var playerTeam = plugin.getTeamManager().getTeamByPlayer(player);
        var channel = playerTeam == null ? ChatChannel.GLOBAL : playerTeam.getMember(player.getUniqueId()).getChatChannel();

        Set<Player> recipients = event.getRecipients();

        String finalMessage = message;
        switch (channel){
            case GLOBAL -> event.setFormat(CC.t(format
                    .replace("%prefix%", "")
                    .replace("%color%", "&f")
                    .replace("%player%", player.getName())
                    .replace("%suffix%", "")
                    .replace("%message%", message)));

            case TEAM -> {
                if (isGlobalChat(message)){
                    message = message.substring(1).trim();
                    event.setFormat(CC.t(format
                            .replace("%prefix%", "")
                            .replace("%color%", "&f")
                            .replace("%player%", player.getName())
                            .replace("%suffix%", "")
                            .replace("%message%", message)));
                    return;
                }

                List<Member> onlineMembers = playerTeam.getOnlineMembers();
                recipients.retainAll(onlineMembers.stream().map(Member::getPlayer).toList());

                event.setFormat(LangHandler.INSTANCE.getMessage(player, Lang.TEAM_CHAT_FORMAT)
                        .replace("%prefix%", "")
                        .replace("%color%", "&f")
                        .replace("%player%", player.getName())
                        .replace("%suffix%", "")
                        .replace("%message%", finalMessage));

            }

            case ALLY -> {
                if (isGlobalChat(message)){
                    message = message.substring(1).trim();
                    event.setFormat(CC.t(format
                            .replace("%prefix%", "")
                            .replace("%color%", "&f")
                            .replace("%player%", player.getName())
                            .replace("%suffix%", "")
                            .replace("%message%", message)));
                    return;
                }

                List<Member> onlineMembers = playerTeam.getOnlineMembers();
                recipients.retainAll(onlineMembers.stream().map(Member::getPlayer).toList());

                event.setFormat(LangHandler.INSTANCE.getMessage(player, Lang.ALLY_CHAT_FORMAT)
                        .replace("%prefix%", "")
                        .replace("%color%", "&f")
                        .replace("%player%", player.getName())
                        .replace("%suffix%", "")
                        .replace("%message%", finalMessage));
            }
        }
    }



    public boolean isGlobalChat(String message){
        if (message.length() <= 1){
            return false;
        }

        if (!message.startsWith("!")){
            return false;
        }

        for (var i = 0; i < message.length(); i++){
            if (Character.isWhitespace(message.charAt(i))){
                continue;
            }

            if (message.charAt(i) == '/'){
                return false;
            }
        }

        return true;
    }

}
