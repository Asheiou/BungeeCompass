package cymru.asheiou.bungeecompass.command;

import cymru.asheiou.bungeecompass.Compass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCompassCommandExecutor implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player player) {
      if (Compass.hasCompass(player)) {
        player.sendMessage(Component.text("You already have a compass!").color(NamedTextColor.RED));
        return true;
      }
      ItemStack compass = Compass.getCompass();
      player.getInventory().addItem(compass); // Give compass
      player.sendMessage(Component.text("Compass given successfully.").color(NamedTextColor.AQUA));
      return true;
    } else {
      sender.sendMessage("This command cannot be run from the console.");
      return false;
    }
  }
}
