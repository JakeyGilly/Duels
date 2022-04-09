package org.distantnetwork.duels.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.distantnetwork.duels.utils.ArenaOneVOne;
import org.distantnetwork.duels.utils.DuelPlayer;
import org.jetbrains.annotations.NotNull;

public class CreateArenaCommand implements CommandExecutor {
    public static ArenaOneVOne newarena;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("duels.createarena")) {
                player.sendMessage(String.format("%sYou don't have permission to use this command!", ChatColor.RED));
                return true;
            }
            if (args.length > 1) {
                player.sendMessage(String.format("%sUsage: /%s <arenaName>", ChatColor.RED, label));
                return true;
            }
            if (args.length < 1) {
                player.sendMessage(String.format("%sUsage: /%s <arena name>", ChatColor.RED, label));
                return true;
            }
            player.getInventory().addItem(new ItemStack(Material.STICK));
            newarena = new ArenaOneVOne(args[0]);
            DuelPlayer dplayer = new DuelPlayer(player);
            dplayer.setCreatingArena(true);
            dplayer.save();
            player.sendMessage(ChatColor.BLUE + "Click on the block of the first players spawn point with a stick.");
        } else sender.sendMessage(String.format("%sThis command is only for players!", ChatColor.RED));
        return true;
    }
}
