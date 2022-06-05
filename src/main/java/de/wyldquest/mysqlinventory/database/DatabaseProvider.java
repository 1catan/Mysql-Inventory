package de.wyldquest.mysqlinventory.database;

import de.wyldquest.mysqlinventory.serialize.InventoryData;

public interface DatabaseProvider {

    void connect();

    void createTableIfNotExists();

    void addInventoryData(String uuid, String name, String inventory);

    InventoryData getInventoryData(String uuid);

    void updateInventoryData(String uuid, String inventory);

    void disconnect();
}
