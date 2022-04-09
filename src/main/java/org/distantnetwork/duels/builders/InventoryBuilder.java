package org.distantnetwork.duels.builders;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryBuilder implements Listener {
    private final UUID uuid;
    private final Inventory inventory;
    private final Map<Integer, onInventoryClick> actionsClick;
    public static Map<UUID, InventoryBuilder> inventoriesByUUID = new HashMap<>();
    public static Map<UUID, UUID> openInventories = new HashMap<>();
    public InventoryBuilder(int size, String title) {
        uuid = UUID.randomUUID();
        inventory = Bukkit.createInventory(null, size, title);
        actionsClick = new HashMap<>();
        inventoriesByUUID.put(getUuid(), this);
    }
    public Inventory getInventory() {
        return inventory;
    }
    public UUID getUuid() {
        return uuid;
    }
    public static Map<UUID, InventoryBuilder> getInventoriesByUUID() {
        return inventoriesByUUID;
    }
    public static Map<UUID, UUID> getOpenInventories() {
        return openInventories;
    }
    public Map<Integer, onInventoryClick> getClickActions() {
        return actionsClick;
    }
    public void setItem(int slot, ItemStack stack, onInventoryClick action){
        inventory.setItem(slot, stack);
        if (action != null){
            actionsClick.put(slot, action);
        }
    }
    public void setItem(int slot, ItemStack stack){
        setItem(slot, stack, null);
    }
    public void addItem(ItemStack stack, onInventoryClick action){
        inventory.addItem(stack);
        if (action != null){
            actionsClick.put(inventory.firstEmpty(), action);
        }
    }
    public void addItem(ItemStack stack){
        addItem(stack, null);
    }
    public interface onInventoryClick {
        void onClick(Player player);
    }

    public void open(@NotNull Player p) {
        p.openInventory(inventory);
        openInventories.put(p.getUniqueId(), getUuid());
    }
}
