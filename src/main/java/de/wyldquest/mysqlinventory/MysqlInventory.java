package de.wyldquest.mysqlinventory;

import de.wyldquest.mysqlinventory.commands.SerializeCommand;
import de.wyldquest.mysqlinventory.config.ConfigManager;
import de.wyldquest.mysqlinventory.database.DatabaseManager;
import de.wyldquest.mysqlinventory.listener.PlayerListener;
import de.wyldquest.mysqlinventory.serialize.SerializeUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class MysqlInventory extends JavaPlugin {

    private static MysqlInventory instance;
    private ConfigManager configManager;
    private DatabaseManager databaseManager;
    private SerializeUtils serializeUtils;

    @Override
    public void onEnable() {
        instance = this;
        this.getCommand("store").setExecutor(new SerializeCommand());
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.configManager = new ConfigManager();
        this.databaseManager = new DatabaseManager();
        this.serializeUtils = new SerializeUtils();
        this.databaseManager.createTableIfNotExists();
    }

    @Override
    public void onDisable() {
        this.databaseManager.disconnect();
    }

    public static MysqlInventory getInstance() {
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public SerializeUtils getSerializeUtils() {
        return serializeUtils;
    }
}
