package git.moiCR.hcf.lang;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lib.Handler;
import git.moiCR.hcf.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LangHandler extends Handler {

    private final Map<String, YamlConfiguration> langFiles;
    public static LangHandler INSTANCE;

    public LangHandler(Main instance) {
        super(instance);
        this.langFiles = new HashMap<>();
        INSTANCE = this;
    }

    @Override
    public void load() {
        File langFolder = new File(getInstance().getDataFolder(), "langs");

        if (!langFolder.exists()){
            langFolder.mkdirs();
        }

        File[] checkFiles = langFolder.listFiles();
        if (checkFiles == null || checkFiles.length == 0) {
            getInstance().saveResource("langs/es.yml", false);
            getInstance().saveResource("langs/en.yml", false);
        }

        File[] files = langFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null){
            return;
        }

        langFiles.clear();
        for (File file : files) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            langFiles.put(file.getName().replace(".yml", ""), config);
        }

        Bukkit.getConsoleSender().sendMessage(CC.t("&aLoaded " + langFiles.size() + " language files."));
    }


    public String getMessage(Player player, Lang dictionary) {

        if (player == null) {
            return CC.t(dictionary.getDefaultMessage());
        }

        var profile = getInstance().getProfileManager().findProfile(player);

        if (profile == null) return CC.t(dictionary.getDefaultMessage());

        var langKey = profile.getLanguage();

        var langConfig = langFiles.get(langKey);
        if (langConfig == null) {
            langConfig = langFiles.get("en");
            if (langConfig == null) return CC.t(dictionary.getDefaultMessage());
        }

        String message = langConfig.getString(dictionary.getPath());
        if (message == null) {
            return CC.t(dictionary.getDefaultMessage());
        }

        return CC.t(message);
    }

    public List<String> getMessageList(Player player, Lang dictionary) {

        if (player == null) {
            return List.of(CC.t(dictionary.getDefaultMessage()));
        }

        var profile = getInstance().getProfileManager().findProfile(player);

        if (profile == null) return List.of(CC.t(dictionary.getDefaultMessage()));

        var langKey = profile.getLanguage();
        var langConfig = langFiles.get(langKey);

        if (langConfig == null) {
            langConfig = langFiles.get("en");
            if (langConfig == null) return List.of(CC.t(dictionary.getDefaultMessage()));
        }

        List<String> messageList = langConfig.getStringList(dictionary.getPath());

        if (messageList.isEmpty()) {
            return List.of(CC.t(dictionary.getDefaultMessage()));
        }

        return CC.t(messageList);
    }

    private void updateProfileLocale(Player player, String newLocale) {
        var profile = getInstance().getProfileManager().findProfile(player);
        if (profile == null) return;

        var langKey = getLangKey(newLocale);

        if (!profile.getLanguage().equals(langKey)) {
            profile.setLanguage(langKey);
            // profile.saveAsync();
        }
    }

    private String getLangKey(String playerLocale) {
        if (langFiles.containsKey(playerLocale)) {
            return playerLocale;
        }

        if (playerLocale.contains("_")) {
            String baseLang = playerLocale.split("_")[0];
            if (langFiles.containsKey(baseLang)) {
                return baseLang;
            }
        }
        return "en";
    }


    @Override
    public Listener getEvents() {
        return new Listener() {

            @EventHandler
            public void onJoin(PlayerJoinEvent event){
                updateProfileLocale(event.getPlayer(), event.getPlayer().spigot().getLocale());
            }

            @EventHandler
            public void onLocaleChange(PlayerLocaleChangeEvent event){
                updateProfileLocale(event.getPlayer(), event.getNewLocale());
            }
        };
    }
}
