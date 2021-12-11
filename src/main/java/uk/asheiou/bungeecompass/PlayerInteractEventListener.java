package uk.asheiou.bungeecompass;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractEventListener extends ServersMenu implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerUse(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;
        if (event.getAction() == Action.PHYSICAL) return; // Don't want crop jumping to be an interact!
        Player player = event.getPlayer();
        if (Compass.isCompass(player.getInventory().getItemInMainHand())) { // Make same item
            SERVERSMENU.open(player);
            event.setCancelled(true);
        }
    }
}

