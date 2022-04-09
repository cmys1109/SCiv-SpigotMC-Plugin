package com.cmys1109.SimulatedCivilization;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

class Command_Debug implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        sender.sendMessage("DEBUG-----------FirstLine");
        sender.sendMessage(player.playerList.toString());
        sender.sendMessage(player.WhitePLayerList.toString());
        sender.sendMessage(player.playerMap.toString());
        sender.sendMessage(team.TeamList.toString());
        sender.sendMessage("DEBUG-----------LastLine");
        return true;
    }
}
