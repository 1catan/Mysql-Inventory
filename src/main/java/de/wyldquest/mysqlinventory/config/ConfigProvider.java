package de.wyldquest.mysqlinventory.config;

public interface ConfigProvider {

    void createConfig();

    void loadConfig();

    void saveConfig();

    void loadDefaultSettings();
}
