package cymru.asheiou.bungeecompass;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

public class ServersConfigAccessor {
  static File serversConfigFile = null;
  static FileConfiguration serversConfig = null;
  static JavaPlugin plugin = JavaPlugin.getProvidingPlugin(BungeeCompass.class);

  public static void reloadServersConfig() {
    if (serversConfigFile == null) {
      serversConfigFile = new File(plugin.getDataFolder(), "servers.yml");
    }
    serversConfig = YamlConfiguration.loadConfiguration(serversConfigFile);
    Reader defConfigStream = null;
    try {
      defConfigStream = new InputStreamReader(Objects.requireNonNull(plugin.getResource("servers.yml")));
    } catch (NullPointerException e) {
      plugin.getLogger().warning("Could not load internal servers.yml! Please check your build.");
    }
    if (defConfigStream != null) {
      YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
      serversConfig.setDefaults(defConfig);
    }
  }

  public static FileConfiguration getServersConfig() {
    if (serversConfig == null) {
      reloadServersConfig();
    }
    return serversConfig;
  }

  public static void saveDefaultServersConfig() {
    if (serversConfigFile == null) {
      serversConfigFile = new File(plugin.getDataFolder(), "servers.yml");
    }
    if (!serversConfigFile.exists()) {
      plugin.saveResource("servers.yml", false);
    }
  }
}
