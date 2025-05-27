package cymru.asheiou.bungeecompass.menu;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import cymru.asheiou.bungeecompass.BungeeCompass;
import cymru.asheiou.bungeecompass.Compass;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class BetaMenu implements InventoryProvider {
    public static final SmartInventory BETAMENU = SmartInventory.builder()
            .id("betaMenu")
            .provider(new BetaMenu())
            .size(3, 9)
            .title("Beta Testing")
            .build();

    public static JavaPlugin plugin = JavaPlugin.getProvidingPlugin(BungeeCompass.class);

    @Override
    public void init(Player player, InventoryContents contents) {
        ItemStack borderGlass = Compass.getBorderGlass();
        contents.fill(ClickableItem.empty(borderGlass));

        LinkedHashMap<String[], ItemStack> items = new LinkedHashMap<>();
        items.put(new String[]{
                ChatColor.YELLOW + "Compound",
                "This will be the new Quake 2 map!",
                "1","3",
                "event2"
        },new ItemStack(Material.WOODEN_HOE));

        items.put(new String[]{
                ChatColor.RED + "Kingdom",
                "This will be the new Quake 4 map!",
                "1","5",
                "event3"
        }, new ItemStack(Material.WOODEN_HOE));

        for (String[] i : items.keySet()) {
            List<String> lore = new ArrayList<String>();
            lore.add(i[1]);
            ItemStack itemStack = items.get(i);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(i[0]);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);

            contents.set(Integer.parseInt(i[2]), Integer.parseInt(i[3]), ClickableItem.of(itemStack, e -> {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                try {
                    out.writeUTF("Connect"); // Connect channel
                    out.writeUTF(i[4]);
                } catch (Exception ex) {
                    plugin.getLogger().info(ex.getMessage());
                }
                player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray()); // send message
            }));
        }
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {}
}
