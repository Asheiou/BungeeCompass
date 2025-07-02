package cymru.asheiou.bungeecompass.menu;

import cymru.asheiou.bungeecompass.BungeeCompass;
import cymru.asheiou.bungeecompass.Compass;
import cymru.asheiou.bungeecompass.ServersConfigAccessor;
import cymru.asheiou.inv.ClickableItem;
import cymru.asheiou.inv.SmartInventory;
import cymru.asheiou.inv.content.InventoryContents;
import cymru.asheiou.inv.content.InventoryProvider;
import io.siggi.cubecore.AsyncValue;
import io.siggi.cubecore.CubeCore;
import io.siggi.cubecore.bukkit.CubeCoreBukkit;
import io.siggi.cubecore.userinfo.UserInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainMenu implements InventoryProvider {

  public static final SmartInventory SERVERSMENU = SmartInventory.builder()
          .id("compassGui")
          .provider(new MainMenu())
          .size(ServersConfigAccessor.getServersConfig().getInt("config.main-menu-size"), 9)
          .title("Network Navigator")
          .build();
  private static final JavaPlugin plugin = JavaPlugin.getProvidingPlugin(BungeeCompass.class); // Get plugin message

  @Override
  public void init(Player player, InventoryContents contents) {

    ItemStack borderGlass = Compass.getBorderGlass();

    contents.fillBorders(ClickableItem.empty(borderGlass));

    FileConfiguration servers = ServersConfigAccessor.getServersConfig();

    for (String s : servers.getKeys(false)) {
      if (s.equals("config")) continue;
      List<String> lore = servers.getStringList(s + ".item.lore");
      Material material = Material.getMaterial(servers.getString(s + ".item.material"));
      assert material != null;
      final ItemStack[] itemStack = new ItemStack[1];
      if (material == Material.PLAYER_HEAD) {
        AsyncValue<UserInfo> future = CubeCore.getUserDatabase().getByUniqueId(UUID.fromString(
                servers.getString(s + ".item.uuid")));
        UserInfo u = future.getCompletableFuture().join();
        if (u != null) {
          itemStack[0] = CubeCoreBukkit.createPlayerHead(u);
        } else {
          itemStack[0] = new ItemStack(Material.PLAYER_HEAD);
        }
      } else {
        itemStack[0] = new ItemStack(material);
      }
      ItemMeta itemMeta = itemStack[0].getItemMeta();
      Component displayName = LegacyComponentSerializer.legacy('&').deserialize(servers.getString(
              s + ".item.displayname"));
      itemMeta.displayName(displayName);
      itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
      List<Component> componentList = new ArrayList<>();
      for (String line : lore) {
        componentList.add(Component.text(line));
      }
      itemMeta.lore(componentList);
      itemStack[0].setItemMeta(itemMeta);

      switch (servers.getString(s + ".type")) {
        case "single" -> contents.add(ClickableItem.of(itemStack[0], e -> {
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
        case "new-old" ->
                contents.add(ClickableItem.of(itemStack[0], e -> NewOldServersMenu.getInventory(
                        s, servers.getString(s + ".old")).open(player)));
        default -> contents.add(ClickableItem.empty(new ItemStack(Material.BARRIER)));
      }
    }
  }

  @Override
  public void update(Player player, InventoryContents inventoryContents) {
  } // no updating of inv allowed
}
