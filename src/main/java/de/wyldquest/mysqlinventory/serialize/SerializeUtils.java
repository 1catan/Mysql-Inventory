package de.wyldquest.mysqlinventory.serialize;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class SerializeUtils {
    
    public String serializeItemStack(ItemStack itemStack) {
        String encodedObject = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
            bukkitObjectOutputStream.writeObject(itemStack);
            bukkitObjectOutputStream.flush();
            byte[] serializedObject = byteArrayOutputStream.toByteArray();
            encodedObject = Base64.getEncoder().encodeToString(serializedObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedObject;
    }

    public String encodeInventory(ItemStack[] items) {
        String encodedString = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
            bukkitObjectOutputStream.writeInt(items.length);
            for (ItemStack item : items) {
                bukkitObjectOutputStream.writeObject(item);
            }
            bukkitObjectOutputStream.flush();
            byte[] serializedObject = byteArrayOutputStream.toByteArray();
            encodedString = Base64.getEncoder().encodeToString(serializedObject);

            bukkitObjectOutputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedString;
    }

    public ItemStack[] decodeInventory(String encodedInventory) {
        ItemStack[] items = null;
        try {
            byte[] serializedObject = Base64.getDecoder().decode(encodedInventory);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedObject);
            BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(byteArrayInputStream);
            int itemCount = bukkitObjectInputStream.readInt();
            items = new ItemStack[itemCount];
            for (int i = 0; i < itemCount; i++) {
                items[i] = (ItemStack) bukkitObjectInputStream.readObject();
            }
            bukkitObjectInputStream.close();
            byteArrayInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return items;
    }
}
