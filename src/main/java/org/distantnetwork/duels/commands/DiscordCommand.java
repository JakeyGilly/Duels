package org.distantnetwork.duels.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiscordCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("powermage.discord")) {
                player.sendMessage(String.format("%sYou don't have permission to use this command!", ChatColor.RED));
                return true;
            }
            TextComponent link = new TextComponent(String.format("%sClick here to join our %sDiscord %sto get help and support!", ChatColor.GOLD, ChatColor.AQUA, ChatColor.GOLD));
            link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/QWQWQWQ"));
            player.spigot().sendMessage(link);
        } else sender.sendMessage(String.format("%sThis command is only for players!", ChatColor.RED));
        return true;
    }
}
