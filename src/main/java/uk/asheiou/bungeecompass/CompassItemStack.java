package uk.asheiou.bungeecompass;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CompassItemStack {
    public ItemStack getCompass() {
        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        compass.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 1);
        ItemMeta compassItemMeta = compass.getItemMeta();
        compassItemMeta.setDisplayName(ChatColor.AQUA + "Server Compass");
        compassItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        compass.setItemMeta(compassItemMeta);
        return compass;
    }
}
