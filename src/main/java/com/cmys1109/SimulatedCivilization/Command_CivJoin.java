package com.cmys1109.SimulatedCivilization;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Command_CivJoin implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command cmd, @NotNull String label, String @NotNull [] args) {
        if (!player.playerList.contains(args[0])) {
            sender.sendMessage(ChatColor.RED + args[0] + ",该玩家不存在");
            return true;
        }
        player Player = player.playerMap.get(args[0]);
        if (!team.TeamList.containsKey(args[1])) {
            sender.sendMessage(ChatColor.RED + args[1] + ",该文明不存在");
            return true;
        }
        if (team.TeamList.get(args[1]).append(Player, main.plugin.getDataFolder())) {
            sender.sendMessage("Failed append.");
            return true;
        }
        Player.UpdatePlayerName();
        sender.sendMessage("执行成功");
        sender.sendMessage(player.playerMap.get(args[0]).Name + "已经归属于" + player.playerMap.get(args[0]).Team);
        return true;
    }
}
