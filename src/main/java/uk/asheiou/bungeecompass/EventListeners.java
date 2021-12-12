package uk.asheiou.bungeecompass;

import hk.siggi.bukkit.plugcubebuildersin.PlugCubeBuildersIn;
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

public class EventListeners extends ServersMenu implements Listener {

    public void spawnHandler(Player player) {
        switch (PlugCubeBuildersIn.getInstance().getServerName()) {
            case "hub":
            case "skins":
            case "minigames":
            case "creative":
                break;
            default:
                return; // if not defined servers break
        }
        if(Compass.hasCompass(player)) return;
        player.getInventory().addItem(Compass.getCompass()); //give compass
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.getDrops().removeIf(Compass::isCompass);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if(Compass.isCompass(event.getItemDrop().getItemStack())) {
            event.setCancelled(true); // Undo player's drop
            event.getPlayer().sendMessage(ChatColor.RED+"You can't drop this!");
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
