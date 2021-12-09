package uk.asheiou.bungeecompass;

import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CompassGUI implements InventoryProvider {
    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("compassGui")
            .provider(new CompassGUI())
            .size(3,9)
            .title("Server Compass")
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {
        //TODO
    }
}
