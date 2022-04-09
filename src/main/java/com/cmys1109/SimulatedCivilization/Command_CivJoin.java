package com.cmys1109.SimulatedCivilization;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

class Command_CivJoin implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String @NotNull [] args) {
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "SCiv:错误的指令");
            return true;
        }
        if (!player.playerList.contains(Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId().toString())) {
            sender.sendMessage(ChatColor.RED + args[0] + ",该玩家不存在");
            return true;
        }
        player Player = player.playerMap.get(Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId().toString());
        if (!team.TeamList.containsKey(args[1])) {
            sender.sendMessage(ChatColor.RED + args[1] + ",该文明不存在");
            return true;
        }
        if (team.TeamList.get(args[1]).append(Player)) {
            sender.sendMessage("Failed append.");
            return true;
        }
        Player.UpdatePlayerName();
        sender.sendMessage(Player.Name + " 已经加入 §" + team.TeamList.get(Player.Team).TeamColor + Player.Team);
        Objects.requireNonNull(Bukkit.getPlayer(Player.uuid)).sendMessage(sender.getName() + " 已经将你加入 §" + team.TeamList.get(Player.Team).TeamColor + Player.Team);

        return true;
    }
}
