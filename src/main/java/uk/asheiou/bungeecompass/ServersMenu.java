package uk.asheiou.bungeecompass;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.SmartInventory;
import hk.siggi.bukkit.plugcubebuildersin.PlugCubeBuildersIn;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class ServersMenu implements InventoryProvider {
    public static final SmartInventory SERVERSMENU = SmartInventory.builder()
            .id("compassGui")
            .provider(new ServersMenu())
            .size(3,9)
            .title("Server Compass")
            .build();


    @Override
    public void init(Player player, InventoryContents contents) {

        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(BungeeCompass.class); // Get plugin message

        ItemStack borderGlass = new ItemStack(Material.WHITE_STAINED_GLASS_PANE); // White borders
        ItemMeta borderGlassMeta = borderGlass.getItemMeta();
        borderGlassMeta.setDisplayName(ChatColor.DARK_GRAY + " "); // remove name
        borderGlass.setItemMeta(borderGlassMeta);

        contents.fillBorders(ClickableItem.empty(borderGlass));

        ItemStack skinsItem = PlugCubeBuildersIn.getInstance().createSkull(UUID.fromString("4f6a3a30-7663-405b-a2b3-8aa4667057c9"));
        //ItemStack skinsItem = new ItemStack(Material.PLAYER_HEAD);

        LinkedHashMap<String[], ItemStack> items = new LinkedHashMap<>(); // list of items
        items.put(new String[]{ChatColor.AQUA+ "Hub", "hub", "The place where all the servers meet!"}, new ItemStack(Material.COMPASS));
        items.put(new String[]{ChatColor.GOLD+"Creative", "creative", "Infinite flat creative mode worlds!"}, new ItemStack(Material.PEONY));
        items.put(new String[]{ChatColor.GREEN+"Survival", "survival", "Vanilla SMP with GriefPrevention claims!"}, new ItemStack(Material.DIAMOND_PICKAXE));
        items.put(new String[]{ChatColor.RED+"Factions", "factions", "Form alliances, start wars, rise to the top!"}, new ItemStack(Material.DIAMOND_SWORD));
        items.put(new String[]{ChatColor.YELLOW+"Skyblock", "skyblock", "An island in a world all to yourself!"}, new ItemStack(Material.OAK_SAPLING));
        items.put(new String[]{ChatColor.LIGHT_PURPLE+"Minigames", "minigames", "Fast-paced games for you and your friends!"}, new ItemStack(Material.WOODEN_HOE));
        items.put(new String[]{ChatColor.DARK_AQUA+"Skin Wardrobe", "skins", "Change skins quickly and conveniently!"}, skinsItem);

        for (String[] i : items.keySet()) {
            ItemStack itemStack = items.get(i);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(i[0]);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            List<String> lore = new ArrayList<>();
            lore.add(i[2]);
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            contents.add(ClickableItem.of(itemStack, e -> { // add to inventory
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                try {
                    out.writeUTF("Connect"); // Connect channel
                    out.writeUTF(i[1]);
                } catch (Exception ex) { plugin.getLogger().info(ex.getMessage()); }
                player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray()); // send message
            }));
        }

    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {} // no updating of inv allowed
}
