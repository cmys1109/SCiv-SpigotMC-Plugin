package com.cmys1109.SimulatedCivilization;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Command_CivList implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command cmd, @NotNull String label, String[] args) {
        for (String t : team.TeamList.keySet()) {
            team teamCache = team.TeamList.get(t);
            sender.sendMessage(ChatColor.DARK_BLUE + "-------------------");
            sender.sendMessage(ChatColor.RED + teamCache.Name + ChatColor.WHITE + " , " + ChatColor.BLUE + "最多人数: " + ChatColor.BOLD + ChatColor.GRAY + teamCache.MemberMax);
            sender.sendMessage(ChatColor.DARK_AQUA + "出生点: " + Objects.requireNonNull(teamCache.BirthPoint.getWorld()).getName() + ChatColor.WHITE + " , " + ChatColor.AQUA + "XYZ:[" + ChatColor.BLUE + teamCache.BirthPoint.getX() + ","
                    + teamCache.BirthPoint.getY() + "," + teamCache.BirthPoint.getZ() + ChatColor.AQUA + "]");
            sender.sendMessage(ChatColor.GOLD + "成员列表:");
            StringBuilder MemberListString = new StringBuilder("[");
            boolean k = true;
            for (String Member : teamCache.MemberList) {
                if (k) {
                    MemberListString.append(Member);
                    k = false;
                    continue;
                }
                MemberListString.append(",").append(Member);
            }
            MemberListString.append("]");
            sender.sendMessage(MemberListString.toString());
            sender.sendMessage(ChatColor.DARK_BLUE + "-------------------");
            sender.sendMessage(" ");
        }
        return true;
    }
}
