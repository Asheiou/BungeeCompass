package uk.asheiou.bungeecompass;

import org.bukkit.plugin.java.JavaPlugin;
import uk.asheiou.bungeecompass.command.BetaCommandExecutor;
import uk.asheiou.bungeecompass.command.GiveCompassCommandExecutor;
import uk.asheiou.bungeecompass.command.MenuCommandExecutor;
import uk.asheiou.bungeecompass.command.ReloadCommandExecutor;

import java.io.File;

public final class BungeeCompass extends JavaPlugin {
    @Override
    public void onEnable() {
        if(!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
            getLogger().info("Config file not found! Creating one.");
        }
        if(!new File(getDataFolder(), "servers.yml").exists()) {
            new ServersConfigAccessor().saveDefaultServersConfig();
            getLogger().info("servers.yml not found! Creating the default.");
        }
        getConfig().options().copyDefaults(true);
        saveConfig();
        // Register BungeeCord Plugin Channel
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getLogger().info("BungeeCord PluginChannel registered.");
        // Register events
        getServer().getPluginManager().registerEvents(new EventListeners(this), this);
        getLogger().info("Events registered.");
        // Register commands
        this.getCommand("beta").setExecutor(new BetaCommandExecutor());
        this.getCommand("givecompass").setExecutor(new GiveCompassCommandExecutor());
        this.getCommand("menu").setExecutor(new MenuCommandExecutor());
        this.getCommand("reloadcompass").setExecutor(new ReloadCommandExecutor());
        getLogger().info("Load complete.");

    }
    @Override
    public void onDisable() {
        // Unregister BungeeCord Plugin Channel
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
    }
}
