package git.moiCR.hcf.utils;

import git.moiCR.hcf.Main;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Getter
public class YmlFile extends YamlConfiguration {

    private final File file;

    public YmlFile(Main instance, String name) {
        this.file = new File(instance.getDataFolder(), name + ".yml");

        if(!file.exists()){
            instance.saveResource(name + ".yml", false);
        }

        try{
            this.load(file);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void save() {
        try {
            this.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            this.load(this.file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ConfigurationSection getSection(String name) {
        return super.getConfigurationSection(name);
    }

    @Override
    public int getInt(String path) {
        return super.getInt(path, 0);
    }

    @Override
    public double getDouble(String path) {
        return super.getDouble(path, 0.0);
    }

    @Override
    public boolean getBoolean(String path) {
        return super.getBoolean(path, false);
    }

    @Override
    public String getString(String path) {
        return CC.t(super.getString(path, ""));
    }

    @Override
    public List<String> getStringList(String path) {
        return super.getStringList(path).stream().map(CC::t).toList();
    }
}
