package uk.asheiou.bungeecompass;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerInteractEventListener extends CompassGUI implements Listener {
    private Plugin plugin;
    public PlayerInteractEventListener(Plugin plugin) { this.plugin = plugin; }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) { return; }
        Player player = event.getPlayer();
        plugin.getLogger().info("DEBUG: PIE INIT.");
        if (new CompassComparison().compare(player.getInventory().getItemInMainHand())) {
            CompassGui.open(player);
        }
    }
}

