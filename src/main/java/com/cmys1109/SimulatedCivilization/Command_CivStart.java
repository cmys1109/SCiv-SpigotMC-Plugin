package com.cmys1109.SimulatedCivilization;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Command_CivStart implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command cmd, @NotNull String label, String @NotNull [] args) {
        int Number = Integer.parseInt(args[0]);
        String Civ = args[1];
        String TP = args[2];
        if (!team.TeamList.containsKey(Civ)) {
            sender.sendMessage(ChatColor.RED + args[1] + ",该文明不存在");
            return true;
        }
        team Civil = team.TeamList.get(Civ);
        int SpaceNum = Civil.MemberMax - Civil.MemberList.size();
        if (SpaceNum < Number) {
            sender.sendMessage("超出人数限制");
            return true;
        }
        if (Number > player.WhitePLayerList.size()) {
            sender.sendMessage("在线无文明玩家不足，现有" + player.WhitePLayerList.size());
            return true;
        }
        while (Number > 0) {
            player p = player.playerMap.get(player.WhitePLayerList.get(Number - 1));
            if (Civil.append(p, Bukkit.getUpdateFolderFile())) {
                sender.sendMessage("Failed append");
                return true;
            }
            if (Objects.equals(TP, "1"))
                Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).teleport(Civil.BirthPoint);
            --Number;
            p.SavePlayerYAML(main.plugin.getDataFolder());
            p.UpdatePlayerName();
        }
        team.SaveCivYAML(main.plugin.getDataFolder());

        return true;
    }
}
