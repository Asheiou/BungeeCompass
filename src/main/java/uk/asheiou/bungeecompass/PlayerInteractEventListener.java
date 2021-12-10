package uk.asheiou.bungeecompass;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractEventListener extends CompassGUI implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) { return; }
        Player player = event.getPlayer();
        if (Compass.isCompass(player.getInventory().getItemInMainHand())) { // Make sure names match
            CompassGui.open(player);
        }
    }
}

