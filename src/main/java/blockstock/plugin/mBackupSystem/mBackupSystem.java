package blockstock.plugin.mBackupSystem;

import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import blockstock.plugin.mBackupSystem.main.CommandManager;
import blockstock.plugin.mBackupSystem.utils.BackupManager;
import blockstock.plugin.mBackupSystem.utils.Color.Colorizer;
import blockstock.plugin.mBackupSystem.utils.Color.impl.LegacyColorizer;
import blockstock.plugin.mBackupSystem.utils.ConfigManager;
import blockstock.plugin.mBackupSystem.utils.MessageManager;

@Getter
public class mBackupSystem extends JavaPlugin {
    private Colorizer colorizer;
    protected ConfigManager configManager;
    protected MessageManager messageManager;
    protected CommandManager commandsManager;
    protected KBSManager kbsManager;
    protected BackupManager backupManager;
    public boolean reload = false;

    PluginManager pluginManager;


    protected void startTask() {
        long intervalTicks = getConfigManager().time() * 60L * 20L;

        getServer().getScheduler().runTaskTimerAsynchronously(
                this,
                () -> getBackupManager().createBackupsAllWorlds(),
                intervalTicks,
                intervalTicks
        );

        getLogger().info(getMessageManager().getMessage("backup.task.started", 
                "{time}", String.valueOf(getConfigManager().time())));
    }

    protected void loadingConfiguration() {
        try {
            colorizer = new LegacyColorizer();
            configManager = new ConfigManager(this);
            messageManager = new MessageManager(this);
            backupManager = new BackupManager(this);
            commandsManager = new CommandManager(this);
        } catch (Exception e) {
            getLogger().severe("CONFIGURATION LOADING ERROR! Disabling plugin......");
            getServer().getPluginManager().disablePlugin(this);
        }
    }
    protected void registerCommands() {
        // Komutu bir değişkene atayarak IDE'nin Null uyarısını gideriyoruz
        org.bukkit.command.PluginCommand cmd = getCommand("mBackupSystem");

        if (cmd != null) {
            cmd.setExecutor(commandsManager);
        } else {
            // plugin.yml dosyasında komutun tanımlanmadığını bildirir
            getLogger().severe(getMessageManager().getMessage("plugin.command.not_found"));
        }
    }





}
