package uk.asheiou.bungeecompass;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerJoinEventListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerInventory playerInventory = event.getPlayer().getInventory();
        for(ItemStack i : playerInventory) {
            if(i == null) { continue; } // ignore empty item slots
            if(new CompassComparison().compare(i)) { return; } // if true they already have the compass
        }
        ItemStack compass = new CompassItemStack().getCompass();
        playerInventory.addItem(compass);
    }
}
