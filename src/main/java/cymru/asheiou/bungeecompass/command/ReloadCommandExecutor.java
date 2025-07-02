package cymru.asheiou.bungeecompass.command;

import cymru.asheiou.bungeecompass.BungeeCompass;
import cymru.asheiou.bungeecompass.ServersConfigAccessor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] strings) {
    sender.sendMessage(Component.text("Starting config reload...").color(NamedTextColor.AQUA));
    JavaPlugin.getProvidingPlugin(BungeeCompass.class).reloadConfig();
    ServersConfigAccessor.reloadServersConfig();
    sender.sendMessage(Component.text("Reload complete!").color(NamedTextColor.GREEN));
    return true;
  }
}
