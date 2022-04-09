package org.distantnetwork.duels;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.distantnetwork.duels.commands.*;
import org.distantnetwork.duels.listeners.BlockClick;
import org.distantnetwork.duels.utils.ArenaOneVOne;
import org.distantnetwork.duels.utils.Config.ConfigurationManager;
import org.distantnetwork.duels.utils.enums.ArenaState;

import java.io.File;

public final class Duels extends JavaPlugin {
    private static Duels instance;
    public static Duels getInstance() {return instance;}
    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Duels has been enabled!");
        RegisterEvents();
        RegisterCommands();
        File file = ConfigurationManager.getFileFile("arenas.yml");
        if (file == null) return;
        FileConfiguration config = ConfigurationManager.getConfig(file);
        if (config == null) return;
        if (config.getConfigurationSection("arenas") == null) return;
        config.getConfigurationSection("arenas").getKeys(false).forEach(arena -> {
            ArenaState state = null;
            Location spawn1 = null;
            Location spawn2 = null;
            ItemStack item = null;
            for (String s : config.getConfigurationSection("arenas." + arena).getKeys(false)) {
                if (s.equals("spawn1")) {
                    if (!config.contains("arenas." + arena + "." + s + ".world")) return;
                    if (!config.contains("arenas." + arena + "." + s + ".x")) return;
                    if (!config.contains("arenas." + arena + "." + s + ".y")) return;
                    if (!config.contains("arenas." + arena + "." + s + ".z")) return;
                    final String world = config.getString("arenas." + arena + "." + s + ".world");
                    float yaw = 0;
                    float pitch = 0;
                    double x = config.getDouble("arenas." + arena + "." + s + ".x");
                    double y = config.getDouble("arenas." + arena + "." + s + ".y");
                    double z = config.getDouble("arenas." + arena + "." + s + ".z");
                    if (config.contains("arenas." + arena + "." + s + ".yaw")) {
                        yaw = (float) config.getDouble("arenas." + arena + "." + s + ".yaw");
                    }
                    if (config.contains("arenas." + arena + "." + s + ".pitch")) {
                        pitch = (float) config.getDouble("arenas." + arena + "." + s + ".pitch");
                    }
                    spawn1 = new Location(getServer().getWorld(world), x, y, z, yaw, pitch);
                }
                if (s.equals("spawn2")) {
                    if (!config.contains("arenas." + arena + "." + s + ".world")) return;
                    if (!config.contains("arenas." + arena + "." + s + ".x")) return;
                    if (!config.contains("arenas." + arena + "." + s + ".y")) return;
                    if (!config.contains("arenas." + arena + "." + s + ".z")) return;
                    final String world = config.getString("arenas." + arena + "." + s + ".world");
                    float yaw = 0;
                    float pitch = 0;
                    double x = config.getDouble("arenas." + arena + "." + s + ".x");
                    double y = config.getDouble("arenas." + arena + "." + s + ".y");
                    double z = config.getDouble("arenas." + arena + "." + s + ".z");
                    if (config.contains("arenas." + arena + "." + s + ".yaw")) {
                        yaw = (float) config.getDouble("arenas." + arena + "." + s + ".yaw");
                    }
                    if (config.contains("arenas." + arena + "." + s + ".pitch")) {
                        pitch = (float) config.getDouble("arenas." + arena + "." + s + ".pitch");
                    }
                    spawn2 = new Location(getServer().getWorld(world), x, y, z, yaw, pitch);
                }
                if (s.equals("item")) {
                    item = config.getItemStack("arenas." + arena + ".item");
                }
                if (s.equals("state")) {
                    state = ArenaState.valueOf(config.getString("arenas." + arena + ".state"));
                }
            }
            if (spawn1 == null || spawn2 == null) return;
            if (item == null) return;
            if (state == null) return;
            ArenaOneVOne arenaOneVOne = new ArenaOneVOne(arena, spawn1, spawn2, state, item);
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("Duels has been disabled!");
    }

    private void RegisterCommands() {
        getCommand("duel").setExecutor(new DuelCommand());
        getCommand("duelaccept").setExecutor(new DuelAcceptCommand());
        getCommand("duelcancel").setExecutor(new DuelCancelCommand());
        getCommand("createarena").setExecutor(new CreateArenaCommand());

        getCommand("discord").setExecutor(new DiscordCommand());
        getCommand("store").setExecutor(new StoreCommand());
    }

    private void RegisterEvents() {
        getServer().getPluginManager().registerEvents(new BlockClick(), this);
    }
}