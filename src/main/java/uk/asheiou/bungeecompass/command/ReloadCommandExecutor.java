package uk.asheiou.bungeecompass.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import uk.asheiou.bungeecompass.BungeeCompass;
import uk.asheiou.bungeecompass.ServersConfigAccessor;

public class ReloadCommandExecutor implements CommandExecutor  {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] strings) {
        sender.sendMessage(ChatColor.AQUA+"Starting config reload...");
        JavaPlugin.getProvidingPlugin(BungeeCompass.class).reloadConfig();
        ServersConfigAccessor.reloadServersConfig();
        sender.sendMessage(ChatColor.GREEN+"Reload complete!");
        return true;
    }
}
