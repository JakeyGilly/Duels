package org.distantnetwork.duels.builders;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class InventoryBuilderListeners implements Listener {
    @EventHandler
    public void onInventoryClick(@NotNull InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = player.getUniqueId();
        UUID inventoryUUID = InventoryBuilder.openInventories.get(playerUUID);
        if (inventoryUUID == null) return;
        e.setCancelled(true);
        InventoryBuilder gui = InventoryBuilder.getInventoriesByUUID().get(inventoryUUID);
        InventoryBuilder.onInventoryClick click = gui.getClickActions().get(e.getSlot());
        if (click == null) return;
        click.onClick(player);
    }

    @EventHandler
    public void onClose(@NotNull InventoryCloseEvent e){
        Player player = (Player) e.getPlayer();
        UUID playerUUID = player.getUniqueId();
        InventoryBuilder.openInventories.remove(playerUUID);
    }

    @EventHandler
    public void onQuit(@NotNull PlayerQuitEvent e){
        Player player = e.getPlayer();
        UUID playerUUID = player.getUniqueId();
        InventoryBuilder.openInventories.remove(playerUUID);
    }
}
