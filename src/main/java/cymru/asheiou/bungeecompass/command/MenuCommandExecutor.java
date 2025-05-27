package cymru.asheiou.bungeecompass.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import cymru.asheiou.bungeecompass.menu.MainMenu;

public class MenuCommandExecutor extends MainMenu implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] strings) {
        if (sender instanceof Player) {
            SERVERSMENU.open((Player) sender); // open menu
            return true;
        }
        return false;
    }
}
