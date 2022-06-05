package de.wyldquest.mysqlinventory.listener;

import de.wyldquest.mysqlinventory.database.DatabaseManager;
import de.wyldquest.mysqlinventory.MysqlInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    private DatabaseManager databaseManager;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        databaseManager = MysqlInventory.getInstance().getDatabaseManager();
        Bukkit.getScheduler().runTaskLaterAsynchronously(MysqlInventory.getInstance(), () -> {
            var compactInventory = databaseManager.getInventoryData(String.valueOf(player.getUniqueId()));
            if (compactInventory == null) {
                databaseManager.addInventoryData(String.valueOf(player.getUniqueId()), player.getName(), "Inventory");
            } else {
                ItemStack[] items = MysqlInventory.getInstance().getSerializeUtils().decodeInventory(compactInventory.getInventory());
                player.getInventory().setContents(items);
            }
        }, 20);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        databaseManager = MysqlInventory.getInstance().getDatabaseManager();
        Bukkit.getScheduler().runTaskLaterAsynchronously(MysqlInventory.getInstance(), () -> databaseManager.updateInventoryData(String.valueOf(player.getUniqueId()), MysqlInventory.getInstance().getSerializeUtils().encodeInventory(player.getInventory().getContents())), 20);
    }
}
