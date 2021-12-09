package uk.asheiou.bungeecompass;

import org.bukkit.plugin.java.JavaPlugin;

public final class BungeeCompass extends JavaPlugin {
    @Override
    public void onEnable() {
        // Register BungeeCord Plugin Channel
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getLogger().info("BungeeCord PluginChannel registered.");
        this.getCommand("givecompass").setExecutor(new GiveCompassCommandExecutor());
    }
    @Override
    public void onDisable() {
        // Unregister BungeeCord Plugin Channel
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
    }
}
