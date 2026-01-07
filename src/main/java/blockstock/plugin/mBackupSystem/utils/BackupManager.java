package blockstock.plugin.mBackupSystem.utils;

import org.bukkit.Bukkit;
import blockstock.plugin.mBackupSystem.mBackupSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BackupManager {

    private final mBackupSystem plugin;
    private File backupDir;

    public BackupManager(mBackupSystem plugin) {
        this.plugin = plugin;

        boolean debug = plugin.getConfigManager().getDebug();

        try {
            plugin.getLogger().info(".------------");
            plugin.getLogger().info(plugin.getMessageManager().getMessage("plugin.loading.configuration"));

            if (plugin.getConfigManager().getBackupType().equals("main")) {
                backupDir = new File(plugin.getDataFolder().getParentFile().getParentFile(), "backups");
                plugin.getLogger().info(plugin.getMessageManager().getMessage("backup.dir.using_main"));
            } else {
                backupDir = new File(plugin.getDataFolder(), "backups");
                plugin.getLogger().info(plugin.getMessageManager().getMessage("backup.dir.using_plugin"));
            }

            if (!backupDir.exists()) {
                if (backupDir.mkdirs()) {
                    plugin.getLogger().info(plugin.getMessageManager().getMessage("backup.dir.created", 
                            "{path}", backupDir.getPath()));
                } else {
                    plugin.getLogger().warning(plugin.getMessageManager().getMessage("backup.dir.failed", 
                            "{path}", backupDir.getPath()));
                }
            }

            if (debug) plugin.getLogger().info(plugin.getMessageManager().getMessage("debug.backup_dir", 
                    "{path}", backupDir.getAbsolutePath()));

        } catch (Exception e) {
            plugin.getLogger().severe(plugin.getMessageManager().getMessage("backup.dir.init_error", 
                    "{error}", e.getMessage()));
        }
    }

    public void createBackupsAllWorlds() {
        List<String> worlds = plugin.getConfigManager().getWordlList();
        boolean debug = plugin.getConfigManager().getDebug();

        if (worlds == null || worlds.isEmpty()) {
            plugin.getLogger().warning(plugin.getMessageManager().getMessage("backup.worlds.list_empty"));
            return;
        }

        plugin.getLogger().info(plugin.getMessageManager().getMessage("backup.worlds.started"));

        if (debug) plugin.getLogger().info(plugin.getMessageManager().getMessage("debug.worlds_count", 
                "{count}", String.valueOf(worlds.size())));

        backupNextWorld(worlds, 0);
    }

    private void backupNextWorld(List<String> worlds, int index) {
        boolean debug = plugin.getConfigManager().getDebug();

        if (index >= worlds.size()) {
            plugin.getLogger().info(plugin.getMessageManager().getMessage("backup.worlds.completed"));
            return;
        }

        String worldName = worlds.get(index);

        plugin.getLogger().info(plugin.getMessageManager().getMessage("backup.world.separator"));
        plugin.getLogger().info(plugin.getMessageManager().getMessage("backup.world.started", 
                "{world}", worldName));

        if (debug) plugin.getLogger().info(plugin.getMessageManager().getMessage("debug.world_index", 
                "{index}", String.valueOf(index)));

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            createBackupWorlds(worldName);

            if (debug) plugin.getLogger().info(plugin.getMessageManager().getMessage("debug.world_processed", 
                    "{world}", worldName));

            // Переход к следующему миру с задержкой
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                backupNextWorld(worlds, index + 1);
            }, 40L); // Задержка между бэкапами: 2 секунды
        });
    }

    public void createBackupWorlds(String worldName) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            long startTime = System.currentTimeMillis();
            boolean debug = plugin.getConfigManager().getDebug();

            try {
                if (debug) plugin.getLogger().info(plugin.getMessageManager().getMessage("debug.backup_start", 
                        "{world}", worldName));

                File worldDir = Bukkit.getWorld(worldName).getWorldFolder();

                if (!worldDir.exists()) {
                    plugin.getLogger().warning(plugin.getMessageManager().getMessage("backup.world.folder_not_found", 
                            "{path}", worldDir.toString()));
                    return;
                }

                if (debug) plugin.getLogger().info(plugin.getMessageManager().getMessage("debug.world_folder", 
                        "{path}", worldDir.getAbsolutePath()));

                File archive = createZipArchive(worldDir, worldName);

                if (archive == null) {
                    plugin.getLogger().warning(plugin.getMessageManager().getMessage("backup.world.archive_error"));
                    return;
                }

                long duration = (System.currentTimeMillis() - startTime);
                plugin.getLogger().info(plugin.getMessageManager().getMessage("backup.world.completed", 
                        "{archive}", archive.getName(), 
                        "{duration}", String.valueOf(duration)));

            } catch (Exception e) {
                plugin.getLogger().severe(plugin.getMessageManager().getMessage("backup.world.error", 
                        "{error}", e.getMessage()));
                e.printStackTrace();
            }
        });
    }



    private File createZipArchive(File sourceDir, String worldName) {
        boolean debug = plugin.getConfigManager().getDebug();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String timestamp = sdf.format(new Date());
            String archiveName = worldName + "_" + timestamp + ".zip";

            if (!backupDir.exists()) backupDir.mkdirs();
            File archive = new File(backupDir, archiveName);

            if (debug) plugin.getLogger().info(plugin.getMessageManager().getMessage("debug.archive_creating", 
                    "{path}", archive.getAbsolutePath()));

            try (FileOutputStream fos = new FileOutputStream(archive);
                 ZipOutputStream zos = new ZipOutputStream(fos)) {

                String rootEntryName = sourceDir.getName() + "/";
                zipDirectory(sourceDir, rootEntryName, zos, debug);

                zos.finish();
            }

            return archive;

        } catch (Exception e) {
            plugin.getLogger().severe(plugin.getMessageManager().getMessage("archive.creation.error", 
                    "{error}", e.getMessage()));
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Recursively adds a file or directory to the ZipOutputStream.
     *
     * @param file      The current file or directory to be added
     * @param entryName The relative path within the archive (must end with '/' for directories)
     * @param zos       The ZipOutputStream to write to
     * @param debug     Flag for detailed logging (shows progress in console)
     * @throws IOException If an I/O error occurs during the process
     */
    private void zipDirectory(File file, String entryName, ZipOutputStream zos, boolean debug) throws IOException {

        if (file.isDirectory()) {
            ZipEntry dirEntry = new ZipEntry(entryName);
            zos.putNextEntry(dirEntry);
            zos.closeEntry();

            File[] children = file.listFiles();
            if (children == null) return;

            if (debug) {
                plugin.getLogger().info(plugin.getMessageManager().getMessage("debug.folder_info", 
                        "{path}", file.getAbsolutePath(), 
                        "{count}", String.valueOf(children.length)));
            }

            for (File child : children) {
                String childEntryName = entryName + child.getName() + (child.isDirectory() ? "/" : "");
                zipDirectory(child, childEntryName, zos, debug);
            }
            return;
        }
        if (debug) plugin.getLogger().info(plugin.getMessageManager().getMessage("debug.file_info", 
                "{entry}", entryName, 
                "{size}", String.valueOf(file.length())));

        ZipEntry entry = new ZipEntry(entryName);
        zos.putNextEntry(entry);

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[16384];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
        }

        zos.closeEntry();
    }
    public void deleteOldBackups() {
        if (backupDir == null || !backupDir.exists()) {
            plugin.getLogger().warning(plugin.getMessageManager().getMessage("delete.folder_not_found"));
            return;
        }

        int days = plugin.getConfigManager().getdaystodelete();

        boolean debug = plugin.getConfigManager().getDebug();
        long now = System.currentTimeMillis();
        long maxAge = days * 24L * 60L * 60L * 1000L;

        File[] files = backupDir.listFiles((dir, name) -> name.endsWith(".zip"));

        if (files == null || files.length == 0) {
            plugin.getLogger().info(plugin.getMessageManager().getMessage("delete.no_archives"));
            return;
        }

        plugin.getLogger().info(plugin.getMessageManager().getMessage("backup.world.separator"));
        plugin.getLogger().info(plugin.getMessageManager().getMessage("delete.started", 
                "{days}", String.valueOf(days)));

        for (File f : files) {
            long age = now - f.lastModified();

            if (debug) {
                plugin.getLogger().info(plugin.getMessageManager().getMessage("debug.delete_checking", 
                        "{file}", f.getName(), 
                        "{age}", String.valueOf(age / 1000 / 60 / 60 / 24)));
            }

            if (age > maxAge) {
                if (f.delete()) {
                    plugin.getLogger().info(plugin.getMessageManager().getMessage("delete.file_deleted", 
                            "{file}", f.getName()));
                } else {
                    plugin.getLogger().warning(plugin.getMessageManager().getMessage("delete.file_failed", 
                            "{file}", f.getName()));
                }
            }
        }

        plugin.getLogger().info(plugin.getMessageManager().getMessage("delete.completed"));
    }

}

