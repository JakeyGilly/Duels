package org.distantnetwork.duels.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.distantnetwork.duels.utils.DuelPlayer;
import org.jetbrains.annotations.NotNull;

public class DuelAcceptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "/duelaccept <player>");
                return true;
            }
            Player target = player.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "Player not found");
                return true;
            }
            if (target == player) {
                player.sendMessage(ChatColor.RED + "You can't duel yourself");
                return true;
            }
            DuelPlayer dplayer = new DuelPlayer(player);
            DuelPlayer dtarget = new DuelPlayer(target);
            if (dplayer.isInDuel()) {
                player.sendMessage(ChatColor.RED + "You are currently in a duel. Please finish it first.");
                return true;
            }
            if (dtarget.isInDuel()) {
                player.sendMessage(ChatColor.RED + "That player is currently in a duel. Try again later.");
                return true;
            }
            if (dplayer.hasDuelInvite(target.getUniqueId())) {
                dplayer.removeDuelInvite(target.getUniqueId());
                dtarget.removeDuelRequest(player.getUniqueId());
                dplayer = dplayer.save();
                dtarget = dtarget.save();
                player.sendMessage(ChatColor.GREEN + "Accepting duel request from " + target.getName());
                target.sendMessage(ChatColor.GREEN + player.getName() + " has accepted your duel request");
            } else {
                player.sendMessage(ChatColor.RED + "You have no duel requests from " + target.getName());
            }
        }
        return true;
    }
}