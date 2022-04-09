package org.distantnetwork.duels.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.distantnetwork.duels.utils.DuelPlayer;

import static org.distantnetwork.duels.Duels.getInstance;

public class DuelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("duels.duel")) {
                player.sendMessage(String.format("%sYou don't have permission to use this command!", ChatColor.RED));
                return true;
            }
            if (args.length < 1) {
                player.sendMessage(String.format("%s/duel <player>", ChatColor.RED));
                return true;
            }
            for (MetadataValue meta : player.getMetadata("vanished")) {
                if (meta.asBoolean()) {
                    player.sendMessage(String.format("%sYou can't duel while vanished", ChatColor.RED));
                }
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(String.format("%sPlayer not found", ChatColor.RED));
                return true;
            }
            for (MetadataValue meta : player.getMetadata("vanished")) {
                if (meta.asBoolean()) {
                    player.sendMessage(String.format("%sPlayer not found", ChatColor.RED));
                    return true;
                }
            }
            if (target.equals(player)) {
                player.sendMessage(String.format("%sYou can't duel yourself", ChatColor.RED));
                return true;
            }
            TextComponent accept = new TextComponent(String.format("%s[%sACCEPT%s]", ChatColor.GRAY, ChatColor.GREEN, ChatColor.GRAY));
            accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duelaccept " + player.getName()));
            TextComponent deny = new TextComponent(String.format(" %s[%sDENY%s]", ChatColor.GRAY, ChatColor.RED, ChatColor.GRAY));
            deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duelcancel " + player.getName()));
            target.spigot().sendMessage(new TextComponent(String.format("%s%s wants to duel you! ", ChatColor.RED, player.getName())), accept, deny);
            player.sendMessage("Duel request sent");
            DuelPlayer dplayer = new DuelPlayer(player);
            DuelPlayer dtarget = new DuelPlayer(target);
            dplayer.addDuelRequest(target.getUniqueId());
            dtarget.addDuelInvite(player.getUniqueId());
            dplayer = dplayer.save();
            dtarget = dtarget.save();
            new BukkitRunnable() {
                @Override
                public void run() {
                    DuelPlayer dplayer = new DuelPlayer(player);
                    DuelPlayer dtarget = new DuelPlayer(target);
                    if (dplayer.getDuelRequests().contains(target.getUniqueId())) {
                        dplayer.removeDuelRequest(target.getUniqueId());
                    }
                    if (dtarget.getDuelInvites().contains(player.getUniqueId())) {
                        dtarget.removeDuelInvite(player.getUniqueId());
                    }
                    dplayer = dplayer.save();
                    dtarget = dtarget.save();
                }
            }.runTaskLater(getInstance(), 20 * 60 * 5);
        } else sender.sendMessage(String.format("%sThis command is only for players!", ChatColor.RED));
        return true;
    }
}
