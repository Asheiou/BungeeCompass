package cymru.asheiou.bungeecompass.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import cymru.asheiou.bungeecompass.Compass;

public class GiveCompassCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player player) {
            if(Compass.hasCompass(player)) {
                player.sendMessage(ChatColor.RED + "You already have a compass!");
                return true;
            }
            ItemStack compass = Compass.getCompass();
            player.getInventory().addItem(compass); // Give compass
            player.sendMessage(ChatColor.AQUA+"Compass given successfully.");
            return true;
        } else {
            sender.sendMessage("This command cannot be run from the console.");
            return false;
        }
    }
}
