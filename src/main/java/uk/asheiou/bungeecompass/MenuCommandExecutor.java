package uk.asheiou.bungeecompass;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MenuCommandExecutor extends CompassGUI implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] strings) {
        if (cmd.getName().equalsIgnoreCase("menu")) {
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage("This is a player command!");
            } else {
                CompassGui.open((Player) sender); // open menu
                return true;
            }
        }
        return false;
    }
}
