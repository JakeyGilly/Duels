package org.distantnetwork.duels.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.distantnetwork.duels.utils.Config.ConfigurationManager;
import org.distantnetwork.duels.utils.enums.ArenaState;

import java.io.File;
import java.util.List;

public class ArenaOneVOne {
    static private List<ArenaOneVOne> arenas;
    private String name;
    private Location spawn1;
    private Location spawn2;
    private ArenaState state;
    private ItemStack item;

    public ArenaOneVOne(String name, Location spawn1, Location spawn2, ArenaState state, ItemStack item) {
        this.name = name;
        this.spawn1 = spawn1;
        this.spawn2 = spawn2;
        this.state = state;
        this.item = item;
        arenas.add(this);
    }
    public ArenaOneVOne(String name, Location spawn1, Location spawn2) {
        this.name = name;
        this.spawn1 = spawn1;
        this.spawn2 = spawn2;
        this.state = ArenaState.ARENA_READY;
        this.item = new ItemStack(Material.STONE);
        arenas.add(this);
    }

    public ArenaOneVOne(String name) {
        this.name = name;
        this.spawn1 = null;
        this.spawn2 = null;
        this.state = ArenaState.ARENA_NOT_READY;
        this.item = new ItemStack(Material.STONE);
        if (ConfigurationManager.getFileFile("arenas.yml") == null) return;
        FileConfiguration config = ConfigurationManager.getConfig(ConfigurationManager.getFileFile("arenas.yml"));
        if (config == null) return;
        if (!config.contains("arenas." + name)) return;
        if (config.contains("arenas." + name + ".spawn1")) {
            String worldName = config.getString("arenas." + name + ".spawn1.world");
            double x = config.getDouble("arenas." + name + ".spawn1.x");
            double y = config.getDouble("arenas." + name + ".spawn1.y");
            double z = config.getDouble("arenas." + name + ".spawn1.z");
            float yaw = (float) config.getDouble("arenas." + name + ".spawn1.yaw");
            float pitch = (float) config.getDouble("arenas." + name + ".spawn1.pitch");
            this.spawn1 = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
        }
        if (config.contains("arenas." + name + ".spawn2")) {
            String worldName = config.getString("arenas." + name + ".spawn2.world");
            double x = config.getDouble("arenas." + name + ".spawn2.x");
            double y = config.getDouble("arenas." + name + ".spawn2.y");
            double z = config.getDouble("arenas." + name + ".spawn2.z");
            float yaw = (float) config.getDouble("arenas." + name + ".spawn2.yaw");
            float pitch = (float) config.getDouble("arenas." + name + ".spawn2.pitch");
            this.spawn2 = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
        }
        this.state = ArenaState.valueOf(config.getString("arenas." + name + ".state"));
        if (config.contains("arenas." + name + ".item")) {
            this.item = config.getItemStack("arenas." + name + ".item");
        }
        arenas.add(this);
    }

    public String getName() {
        return name;
    }

    public Location getSpawn1() {
        return spawn1;
    }

    public Location getSpawn2() {
        return spawn2;
    }

    public ArenaState getState() {
        return state;
    }

    public void setState(ArenaState state) {
        this.state = state;
    }

    public void setSpawn1(Location spawn1) {
        this.spawn1 = spawn1;
    }

    public void setSpawn2(Location spawn2) {
        this.spawn2 = spawn2;
    }

    public static List<ArenaOneVOne> getArenas() {
        return arenas;
    }

    public void save() {
        File file = ConfigurationManager.getFileFile("arenas.yml");
        if (file == null) return;
        FileConfiguration config = ConfigurationManager.getConfig(file);
        if (config == null) return;
        config.set("arenas." + name + ".spawn1.world", spawn1.getWorld().getName());
        config.set("arenas." + name + ".spawn1.x", spawn1.getX());
        config.set("arenas." + name + ".spawn1.y", spawn1.getY());
        config.set("arenas." + name + ".spawn1.z", spawn1.getZ());
        config.set("arenas." + name + ".spawn1.yaw", spawn1.getYaw());
        config.set("arenas." + name + ".spawn1.pitch", spawn1.getPitch());
        config.set("arenas." + name + ".spawn2.world", spawn2.getWorld().getName());
        config.set("arenas." + name + ".spawn2.x", spawn2.getX());
        config.set("arenas." + name + ".spawn2.y", spawn2.getY());
        config.set("arenas." + name + ".spawn2.z", spawn2.getZ());
        config.set("arenas." + name + ".spawn2.yaw", spawn2.getYaw());
        config.set("arenas." + name + ".spawn2.pitch", spawn2.getPitch());
        config.set("arenas." + name + ".state", state.toString());
        config.set("arenas." + name + ".item", item);
        ConfigurationManager.saveConfig(file, config);
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }
}
