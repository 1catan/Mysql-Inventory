package de.wyldquest.mysqlinventory.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager implements ConfigProvider {

    private final String PATH = "plugins/MysqlInventory";
    private FileConfiguration configuration;
    private File file;

    public ConfigManager() {
        this.createConfig();
        this.loadConfig();
        if(file.length() == 0) {
            this.loadDefaultSettings();
        }
    }

    @Override
    public void createConfig() {
        final File folder = new File(PATH);
        folder.mkdir();
        file = new File(PATH, "config.yml");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadConfig() {
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void saveConfig() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadDefaultSettings() {
        configuration.set("mysqlinventory.host", "127.0.0.1");
        configuration.set("mysqlinventory.port", 3306);
        configuration.set("mysqlinventory.database", "mysqlinventory");
        configuration.set("mysqlinventory.user", "admin");
        configuration.set("mysqlinventory.password", "password");
        this.saveConfig();
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }
}
