package uk.asheiou.bungeecompass.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.asheiou.bungeecompass.menu.BetaMenu;

public class BetaCommandExecutor extends BetaMenu implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            BETAMENU.open(player);
            return true;
        }
        sender.sendMessage("This command cannot be run from the console.");
        return false;
    }
}
