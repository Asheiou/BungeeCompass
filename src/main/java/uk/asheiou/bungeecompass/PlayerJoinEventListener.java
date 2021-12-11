package uk.asheiou.bungeecompass;

import hk.siggi.bukkit.plugcubebuildersin.PlugCubeBuildersIn;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        switch (PlugCubeBuildersIn.getInstance().getServerName()) {
            case "hub":
            case "skins":
            case "minigames":
            case "creative":
                break;
            default:
                return; // if not defined servers break
        }
        Player player = event.getPlayer();
        if(Compass.hasCompass(player)) return;
        player.getInventory().addItem(Compass.getCompass()); //give compass
    }
}
