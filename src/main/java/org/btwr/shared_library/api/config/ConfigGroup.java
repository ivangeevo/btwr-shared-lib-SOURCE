package org.btwr.shared_library.api.config;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigGroup {

    private final Map<String, ConfigSetting<?>> settings = new LinkedHashMap<>();
    private final String filename;
    private boolean initialized = false;

    public ConfigGroup(String filename) {
        this.filename = filename.endsWith(".toml") ? filename : filename + ".toml";
    }

    public void add(ConfigSetting<?> setting) {
        settings.put(setting.getKey(), setting);
        setting.setParentGroup(this);
    }

    public ConfigSetting<?> get(String key) {
        return settings.get(key);
    }

    public Map<String, ConfigSetting<?>> getAll() {
        return settings;
    }

    public File getFile() {
        return new File(TomlConfigManager.getConfigDir(), filename);
    }

    /** lazy initialization */
    public void init() {
        if (initialized) return;
        initialized = true;

        try {
            load(); // always attempt to load values from the existing file
            if (!getFile().exists()) {
                save(); // save defaults if the file missing
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void save() {
        try {
            TomlConfigManager.writeConfigFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            TomlConfigManager.readConfigFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}