package uk.asheiou.bungeecompass;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

public class ServersConfigAccessor {
    File serversConfigFile = null;
    FileConfiguration serversConfig = null;
    JavaPlugin plugin = JavaPlugin.getProvidingPlugin(BungeeCompass.class);

    public void reloadServersConfig() {
        if (serversConfigFile == null) {
            serversConfigFile = new File(plugin.getDataFolder(), "servers.yml");
        }
        serversConfig = YamlConfiguration.loadConfiguration(serversConfigFile);
        Reader defConfigStream = new InputStreamReader(plugin.getResource("servers.yml"));
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            serversConfig.setDefaults(defConfig);
        }
    }
    public FileConfiguration getServersConfig() {
        if (serversConfig == null) {
            reloadServersConfig();
        }
        return serversConfig;
    }

    public void saveDefaultServersConfig() {
        if (serversConfigFile == null) {
            serversConfigFile = new File(plugin.getDataFolder(), "servers.yml");
        }
        if (!serversConfigFile.exists()) {
            plugin.saveResource("servers.yml", false);
        }
    }
}
