package de.wyldquest.mysqlinventory.database;

import de.wyldquest.mysqlinventory.MysqlInventory;
import de.wyldquest.mysqlinventory.serialize.InventoryData;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.UUID;

public class DatabaseManager implements DatabaseProvider {

    private Connection connection;

    public DatabaseManager() {
        this.connect();
    }

    @Override
    public void connect() {
        final String host = MysqlInventory.getInstance().getConfigManager().getConfiguration().getString("mysqlinventory.host");
        final int port = MysqlInventory.getInstance().getConfigManager().getConfiguration().getInt("mysqlinventory.port");
        final String database = MysqlInventory.getInstance().getConfigManager().getConfiguration().getString("mysqlinventory.database");
        final String user = MysqlInventory.getInstance().getConfigManager().getConfiguration().getString("mysqlinventory.user");
        final String password = MysqlInventory.getInstance().getConfigManager().getConfiguration().getString("mysqlinventory.password");
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":"+port+"/" + database, user, password);
            Bukkit.getServer().getConsoleSender().sendMessage("§aSuccessfully §7connected to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTableIfNotExists() {
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS MysqlInventory (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, player_uuid VARCHAR(255), player_name VARCHAR(255), inventory VARCHAR(5000))");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addInventoryData(String uuid, String name, String inventory) {
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO MysqlInventory (player_uuid, player_name, inventory) VALUES ('" + uuid + "', '" + name + "', '" + inventory + "') ON DUPLICATE KEY UPDATE player_uuid='" + uuid + "'");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InventoryData getInventoryData(String uuid) {
        InventoryData compactInventory = null;
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT player_uuid, player_name, inventory FROM MysqlInventory WHERE player_uuid='" + uuid + "'");
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                compactInventory = new InventoryData(UUID.fromString(resultSet.getString(1)), resultSet.getString(2), resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compactInventory;
    }

    @Override
    public void updateInventoryData(String uuid, String inventory) {
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE MysqlInventory SET inventory='" + inventory + "' WHERE player_uuid='" + uuid + "'");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
