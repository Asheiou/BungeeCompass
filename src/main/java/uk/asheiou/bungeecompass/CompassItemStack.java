package uk.asheiou.bungeecompass;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CompassItemStack {
    public static ItemStack getCompass() {
        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        compass.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 1); // Random enchant not likely to be useful
        ItemMeta compassItemMeta = compass.getItemMeta();
        compassItemMeta.setDisplayName(ChatColor.AQUA + "Server Compass"); // For checking later
        compassItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS); // Glow without enchants appearing
        compass.setItemMeta(compassItemMeta);
        return compass;
    }
}
