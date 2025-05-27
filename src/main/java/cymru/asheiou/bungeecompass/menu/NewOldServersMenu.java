package cymru.asheiou.bungeecompass.menu;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import cymru.asheiou.bungeecompass.BungeeCompass;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static cymru.asheiou.bungeecompass.menu.MainMenu.SERVERSMENU;

public class NewOldServersMenu implements InventoryProvider {
    private String new_server = null;
    private String old_server = null;

    public NewOldServersMenu(String n, String o) {
        new_server = n;
        old_server = o;
    }

    public static SmartInventory getInventory(String server, String old) {
        return SmartInventory.builder()
                .id("menuServersMenu")
                .provider(new NewOldServersMenu(server, old))
                .size(3, 9)
                .title("Network Navigator")
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(BungeeCompass.class); // Get plugin message

        ItemStack borderGlass = new ItemStack(Material.WHITE_STAINED_GLASS_PANE); // White borders
        ItemMeta borderGlassMeta = borderGlass.getItemMeta();
        borderGlassMeta.setDisplayName(ChatColor.DARK_GRAY + " "); // remove name
        borderGlass.setItemMeta(borderGlassMeta);

        contents.fill(ClickableItem.empty(borderGlass));

        LinkedHashMap<String[], ItemStack> items = new LinkedHashMap<>();
        items.put(new String[]{
            ChatColor.RED + "Close",
                "Return to the main menu.",
                "1","1"
        },new ItemStack(Material.BARRIER));

        items.put(new String[]{
                ChatColor.GOLD + "NEW" + ChatColor.RESET + " " + StringUtils.capitalize(new_server),
                "Try all the new features!",
                "1","4",
                new_server
        },new ItemStack(Material.NETHER_STAR));

        items.put(new String[]{
                ChatColor.RED + "Old " + StringUtils.capitalize(new_server),
                "Existing players only.",
                "1","7",
                old_server
        }, new ItemStack(Material.RED_MUSHROOM));

        for (String[] i : items.keySet()) {
            List<String> lore = new ArrayList<String>();
            lore.add(i[1]);
            ItemStack itemStack = items.get(i);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(i[0]);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);

            if(itemStack.getType() == Material.BARRIER) {
                contents.set(Integer.parseInt(i[2]), Integer.parseInt(i[3]), ClickableItem.of(itemStack, e -> {
                    SERVERSMENU.open(player);
                }));
            } else {
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

    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {}
}
