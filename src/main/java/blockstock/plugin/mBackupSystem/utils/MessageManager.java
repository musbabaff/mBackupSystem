package blockstock.plugin.mBackupSystem.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import blockstock.plugin.mBackupSystem.mBackupSystem;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MessageManager {
    private final mBackupSystem plugin;
    private FileConfiguration messages;
    private String language;

    public MessageManager(mBackupSystem plugin) {
        this.plugin = plugin;
        this.language = plugin.getConfigManager().getString("language", "ru");
        loadMessages();
    }

    private void loadMessages() {
        String fileName = "messages_" + language + ".yml";
        File messagesFile = new File(plugin.getDataFolder(), fileName);

        // Create the file from resources if it doesn't exist
        if (!messagesFile.exists()) {
            plugin.saveResource(fileName, false);
        }

        // Load the messages file
        messages = YamlConfiguration.loadConfiguration(messagesFile);

        // Load defaults from jar
        InputStream defConfigStream = plugin.getResource(fileName);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defConfigStream, StandardCharsets.UTF_8));
            messages.setDefaults(defConfig);
        }
    }

    /**
     * Get a translated message by key
     *
     * @param key the message key
     * @return the translated message, or the key if not found
     */
    public String getMessage(String key) {
        if (messages == null) {
            return key;
        }
        return messages.getString(key, key);
    }

    /**
     * Get a translated message by key with replacements
     *
     * @param key the message key
     * @param replacements key-value pairs for replacements (e.g., "{world}", "world_nether")
     * @return the translated message with replacements
     */
    public String getMessage(String key, Object... replacements) {
        String message = getMessage(key);
        
        if (replacements.length % 2 != 0) {
            plugin.getLogger().warning("Invalid replacements for message key: " + key);
            return message;
        }

        for (int i = 0; i < replacements.length; i += 2) {
            String placeholder = replacements[i].toString();
            String value = replacements[i + 1].toString();
            message = message.replace(placeholder, value);
        }

        return message;
    }

    /**
     * Reload messages from file
     */
    public void reload() {
        this.language = plugin.getConfigManager().getString("language", "en");
        loadMessages();
    }
}
