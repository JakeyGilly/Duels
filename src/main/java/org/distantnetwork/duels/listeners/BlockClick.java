package org.distantnetwork.duels.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.distantnetwork.duels.commands.CreateArenaCommand;
import org.distantnetwork.duels.utils.DuelPlayer;

import static org.distantnetwork.duels.Duels.getInstance;

public class BlockClick implements Listener {
    @EventHandler
    public void onBlockClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (event.getMaterial() == Material.STICK) {
                getInstance().getLogger().info(new DuelPlayer(event.getPlayer()).isCreatingArena() + "");
                if (new DuelPlayer(event.getPlayer()).isCreatingArena()) {
                    if (CreateArenaCommand.newarena.getSpawn1() == null) {
                        CreateArenaCommand.newarena.setSpawn1(event.getClickedBlock().getLocation());
                        event.getPlayer().sendMessage("Spawn 1 set!");
                        event.getPlayer().sendMessage(ChatColor.BLUE + "Click on the block of the second players spawn point with a stick.");
                        event.setCancelled(true);
                    } else if (CreateArenaCommand.newarena.getSpawn2() == null) {
                        CreateArenaCommand.newarena.setSpawn2(event.getClickedBlock().getLocation());
                        event.getPlayer().sendMessage("Spawn 2 set!");
                        event.setCancelled(true);
                        CreateArenaCommand.newarena.save();
                        event.getPlayer().sendMessage(ChatColor.GREEN + "Arena created and saved!");
                        DuelPlayer dplayer = new DuelPlayer(event.getPlayer());
                        dplayer.setCreatingArena(false);
                        dplayer.save();
                    } else {
                        event.setCancelled(false);
                    }
                }
            }
        }
    }

}
