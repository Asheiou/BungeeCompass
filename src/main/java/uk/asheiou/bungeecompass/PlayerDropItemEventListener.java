package uk.asheiou.bungeecompass;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemEventListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if(new CompassComparison().compare(event.getItemDrop().getItemStack())) {
            event.setCancelled(true); // Undo player's drop
            event.getPlayer().sendMessage(ChatColor.RED+"You can't drop this!");
        }
    }
}
