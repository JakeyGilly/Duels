package org.distantnetwork.duels.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.distantnetwork.duels.utils.DuelPlayer;
import org.jetbrains.annotations.NotNull;

public class DuelCancelCommand implements CommandExecutor {
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
            DuelPlayer dplayer = new DuelPlayer(player);
            DuelPlayer dtarget = new DuelPlayer(target);
            if (dtarget.hasDuelRequest(player.getUniqueId()) && dplayer.hasDuelInvite(target.getUniqueId())) {
                dplayer.removeDuelInvite(target.getUniqueId());
                dtarget.removeDuelRequest(player.getUniqueId());
                dtarget = dtarget.save();
                dplayer = dplayer.save();
                player.sendMessage(ChatColor.RED + "Duel request cancelled");
            } else {
                player.sendMessage(ChatColor.RED + "You have no duel requests from " + target.getName());
            }
        }
        return true;
    }
}
