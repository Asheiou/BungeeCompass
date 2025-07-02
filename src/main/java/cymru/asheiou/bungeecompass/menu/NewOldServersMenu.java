package cymru.asheiou.bungeecompass.menu;

import cymru.asheiou.bungeecompass.BungeeCompass;
import cymru.asheiou.bungeecompass.Compass;
import cymru.asheiou.inv.ClickableItem;
import cymru.asheiou.inv.SmartInventory;
import cymru.asheiou.inv.content.InventoryContents;
import cymru.asheiou.inv.content.InventoryProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.LinkedHashMap;
import java.util.List;

import static cymru.asheiou.bungeecompass.menu.MainMenu.SERVERSMENU;

public class NewOldServersMenu implements InventoryProvider {
  private final String new_server;
  private final String old_server;

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

    ItemStack borderGlass = Compass.getBorderGlass();

    contents.fill(ClickableItem.empty(borderGlass));

    LinkedHashMap<String[], ItemStack> items = new LinkedHashMap<>();
    items.put(new String[]{
            "<red>Close</red>",
            "Return to the main menu.",
            "1", "1"
    }, new ItemStack(Material.BARRIER));

    items.put(new String[]{
            "<gold>NEW<reset> " + StringUtils.capitalize(new_server),
            "Try all the new features!",
            "1", "4",
            new_server
    }, new ItemStack(Material.NETHER_STAR));

    items.put(new String[]{
            "<aqua>Old<reset> " + StringUtils.capitalize(new_server),
            "Existing players only.",
            "1", "7",
            old_server
    }, new ItemStack(Material.RED_MUSHROOM));

    for (String[] i : items.keySet()) {
      ItemStack itemStack = items.get(i);
      ItemMeta itemMeta = itemStack.getItemMeta();
      itemMeta.displayName(MiniMessage.miniMessage().deserialize(i[0]));
      itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
      itemMeta.lore(List.of(Component.text(i[1])));
      itemStack.setItemMeta(itemMeta);

      if (itemStack.getType() == Material.BARRIER) {
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
  public void update(Player player, InventoryContents inventoryContents) {
  }
}
