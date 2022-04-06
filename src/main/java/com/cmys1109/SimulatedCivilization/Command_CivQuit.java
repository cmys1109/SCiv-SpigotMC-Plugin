package com.cmys1109.SimulatedCivilization;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Command_CivQuit implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender Sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!player.playerList.contains(args[0])) {
            Sender.sendMessage(ChatColor.RED + args[0] + ",该玩家不存在");
            return true;
        }

        String Team = player.playerMap.get(args[0]).Team;

        if (!team.TeamList.containsKey(Team)) {
            Sender.sendMessage(ChatColor.RED + args[0] + ",该玩家不为加入文明");
            return true;
        }

        if (!team.TeamList.get(Team).delete(player.playerMap.get(Sender.getName()), main.plugin.getDataFolder())) {
            Sender.sendMessage("Failed delete");
            return true;
        }

        Objects.requireNonNull(Bukkit.getPlayer(args[0])).sendMessage("你已经被 " + Sender.getName() + " 移出" + Team);
        Sender.sendMessage(args[0] + "已经退出" + Team);
        return true;
    }
}
