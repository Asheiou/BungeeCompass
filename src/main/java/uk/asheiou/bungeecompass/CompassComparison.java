package uk.asheiou.bungeecompass;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CompassComparison {
    public boolean compare(ItemStack itemToCompare) {
        ItemMeta itemMetaToCompare = itemToCompare.getItemMeta();
        if (itemMetaToCompare == null) { return false; }
        ItemMeta compass = new CompassItemStack().getCompass().getItemMeta();
        return itemMetaToCompare.getDisplayName().equals(compass.getDisplayName());
    }
}
