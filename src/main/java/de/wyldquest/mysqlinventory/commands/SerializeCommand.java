package de.wyldquest.mysqlinventory.commands;

import de.wyldquest.mysqlinventory.serialize.SerializeUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SerializeCommand implements CommandExecutor {

    private static String inventory;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            SerializeUtils serializeUtils = new SerializeUtils();
            if(args[0].equalsIgnoreCase("encode")) {
                inventory = serializeUtils.encodeInventory(player.getInventory().getContents());
                player.sendMessage(inventory);
            } else if(args[0].equalsIgnoreCase("decode")) {
                if(inventory != null) {
                    ItemStack[] items = serializeUtils.decodeInventory(inventory);
                    player.sendMessage("Slots: " + items.length);
                    for(int i = 0; i < items.length; i++) {
                        if(items[i] != null) {
                            player.sendMessage("Item: " + items[i].toString());
                        }
                    }
                    Inventory inventory = Bukkit.createInventory(null, 54, "Test");
                    inventory.setContents(items);
                    player.openInventory(inventory);
                }
            }
        }
        return false;
    }
}
