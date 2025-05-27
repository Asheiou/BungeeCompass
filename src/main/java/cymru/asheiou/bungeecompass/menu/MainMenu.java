package cymru.asheiou.bungeecompass.menu;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.SmartInventory;
import io.siggi.cubecore.bukkit.CubeCoreBukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import cymru.asheiou.bungeecompass.BungeeCompass;
import cymru.asheiou.bungeecompass.Compass;
import cymru.asheiou.bungeecompass.ServersConfigAccessor;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.UUID;

public class MainMenu implements InventoryProvider {

    private static JavaPlugin plugin = JavaPlugin.getProvidingPlugin(BungeeCompass.class); // Get plugin message

    public static final SmartInventory SERVERSMENU = SmartInventory.builder()
            .id("compassGui")
            .provider(new MainMenu())
            .size(ServersConfigAccessor.getServersConfig().getInt("config.main-menu-size"),9)
            .title("Network Navigator")
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {

        ItemStack borderGlass = Compass.getBorderGlass();

        contents.fillBorders(ClickableItem.empty(borderGlass));

        FileConfiguration servers = ServersConfigAccessor.getServersConfig();

        for(String s : servers.getKeys(false)) {
            if(s.equals("config")) continue;
            List<String> lore = (List<String>) servers.getList(s + ".item.lore");
            Material material = Material.getMaterial(servers.getString(s+".item.material")); assert material != null;
            ItemStack itemStack = null;
            if (material == Material.PLAYER_HEAD) {
                itemStack = CubeCoreBukkit.createPlayerHead(UUID.fromString(servers.getString(s + ".item.uuid")));
            } else {
                itemStack = new ItemStack(material);
            }
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', servers.getString(s+".item.displayname")));
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);

            switch (servers.getString(s + ".type")) {
                case "single" -> contents.add(ClickableItem.of(itemStack, e -> {
                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(b);
                    try {
                        out.writeUTF("Connect");
                        out.writeUTF(s);
                    } catch (Exception ex) {
                        plugin.getLogger().info(ex.getMessage());
                    }
                    player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray()); // send message
                }));
                case "new-old" -> contents.add(ClickableItem.of(itemStack, e -> NewOldServersMenu.getInventory(s, servers.getString(s + ".old")).open(player)));
                default -> contents.add(ClickableItem.empty(new ItemStack(Material.BARRIER)));
            }
        }
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {} // no updating of inv allowed
}
