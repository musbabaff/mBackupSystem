package blockstock.plugin.mBackupSystem.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import blockstock.plugin.mBackupSystem.mBackupSystem;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private final mBackupSystem plugin;
    protected FileConfiguration config;
    private File configFile;

    public ConfigManager(mBackupSystem plugin) {
        this.plugin = plugin;
        loadConfigFiles();
    }

    private void loadConfigFiles() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                plugin.saveResource("config.yml", false);
            } catch (Exception e) {
                // Can't use messageManager here as it's not initialized yet
                plugin.getLogger().warning("Failed to save config.yml: " + e.getMessage());
            }
        }
        if (configFile.exists()) {
            config = YamlConfiguration.loadConfiguration(configFile);
        }
    }
    public boolean getBoolean(String path, boolean def) {
        if (config == null) return def;
        return config.contains(path) ? config.getBoolean(path) : def;
    }

    public String getString(String path, String def) {
        if (config == null) return def;
        return config.contains(path) ? config.getString(path) : def;
    }

    public List<String> getStringList(String path) {
        if (config == null) return new ArrayList<>();
        return config.getStringList(path);
    }

    protected boolean getDebug() {
        return getBoolean("debug", true);
    }

    public String getBackupType() {
        String type = getString("backup-type", "Main-folder").toLowerCase();
        switch (type) {
            case "main-folder":
                return "main";
            case "plugin-folder":
                return "plugin";
        }
        return "main";
    }
    protected List<String> getWordlList() {
        return getStringList("world-list");
    }
    public boolean getBackupinStart() {
        return getBoolean("backup-in-start", true);
    }
    public boolean getBackupinStop() {
        return getBoolean("backup-in-stop", true);
    }
    public boolean gettask() {
        return getBoolean("task-manager.enabled", true);
    }
    public int time() {
        return config.getInt("task-manager.time", 90);
    }
    public boolean deleteolds() {
        return config.getBoolean("delete-old-backups.enabled", true);
    }
    protected int getdaystodelete() {
        return config.getInt("delete-old-backups.max-backup-age", 7);
    }
}

