package com.cmys1109.SimulatedCivilization;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

class Command_CivQuit implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "SCiv:错误的指令");
            return true;
        }
        String uuid = Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId().toString();
        if (!player.playerList.contains(uuid)) {
            sender.sendMessage(ChatColor.RED + args[0] + ",该玩家不存在");
            return true;
        }

        String Team = player.playerMap.get(uuid).Team;

        if (!team.TeamList.containsKey(Team)) {
            sender.sendMessage(ChatColor.RED + args[0] + ",该玩家不归属任何文明");
            return true;
        }

        if (!team.TeamList.get(Team).delete(player.playerMap.get(uuid))) {
            sender.sendMessage("Failed delete");
            return true;
        }

        Objects.requireNonNull(Bukkit.getPlayer(args[0])).sendMessage("你已经被 " + sender.getName() + " 移出" + Team);
        sender.sendMessage(args[0] + "已经退出" + Team);
        return true;
    }
}
