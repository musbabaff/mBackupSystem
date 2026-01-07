package blockstock.plugin.mBackupSystem.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import blockstock.plugin.mBackupSystem.mBackupSystem;

import java.util.*;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final mBackupSystem plugin;

    public CommandManager(mBackupSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            send(sender, plugin.getMessageManager().getMessage("command.usage"));
            return true;
        }

        String subcommand = args[0].toLowerCase();
        switch (subcommand) {
            case "reload":
                send(sender, plugin.getMessageManager().getMessage("command.reload.initiated"));
                plugin.reload = true;
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                plugin.getServer().getPluginManager().enablePlugin(plugin);
                plugin.reload = false;
                return true;
            case "start":
                plugin.getLogger().info(plugin.getMessageManager().getMessage("command.start.initiated"));
                plugin.getBackupManager().createBackupsAllWorlds();
                return true;
        }
        return false;
    }

    private void send(CommandSender sender, String msg) {
        sender.sendMessage(plugin.getColorizer().colorize(msg));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "start");
        }

        return Collections.emptyList();
    }
}
