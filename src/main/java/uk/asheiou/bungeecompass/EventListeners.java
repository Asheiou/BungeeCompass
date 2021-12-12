package uk.asheiou.bungeecompass;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

public class EventListeners extends ServersMenu implements Listener {
    private final JavaPlugin plugin;
    public EventListeners(JavaPlugin plugin) { this.plugin = plugin; }

    public void spawnHandler(Player player) {
        if(Compass.hasCompass(player)) return;
        if(plugin.getConfig().getBoolean("give-compass-on-login")) player.getInventory().addItem(Compass.getCompass()); //give compass
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.getDrops().removeIf(Compass::isCompass);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if(Compass.isCompass(event.getItemDrop().getItemStack())) {
            Player player = event.getPlayer();
            event.setCancelled(true); // Undo player's drop
            if(plugin.getConfig().getBoolean("block-compass-drop")) {
                player.sendMessage(ChatColor.RED+"You can't drop this!");
                return;
            }
            Bukkit.getScheduler().runTask(plugin, () -> player.getInventory().removeItem(Compass.getCompass()));
            player.sendMessage(ChatColor.AQUA+"Compass removed. You can get it back by running /givecompass.");
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        spawnHandler(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        spawnHandler(event.getPlayer());
    }

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