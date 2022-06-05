package de.wyldquest.mysqlinventory.serialize;

import java.util.UUID;

public class InventoryData {

    private UUID uuid;
    private String name;
    private String inventory;

    public InventoryData(UUID uuid, String name, String inventory) {
        this.uuid = uuid;
        this.name = name;
        this.inventory = inventory;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getInventory() {
        return inventory;
    }
}
