package blockstock.plugin.mBackupSystem;



public final class KBSManager extends mBackupSystem{

    @Override
    public void onEnable() {
        try {
            kbsManager.loadingConfiguration();
            kbsManager.registerCommands();
            if (configManager.getBackupinStart() || !reload) {
                getLogger().info(getMessageManager().getMessage("plugin.loaded.backup_started"));
                backupManager.createBackupsAllWorlds();
            }
            if (configManager.gettask()) {
                kbsManager.startTask();
                getLogger().info(getMessageManager().getMessage("plugin.tasks.started"));
            }
            if (configManager.deleteolds()) {
                backupManager.deleteOldBackups();
            }
        } catch (Exception e) {
            getLogger().severe(getMessageManager().getMessage("config.error.enabling"));
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        if (configManager.getBackupinStop()) {
            getLogger().info(getMessageManager().getMessage("plugin.shutdown.backup_started"));
            backupManager.createBackupsAllWorlds();
        }
    }



}
