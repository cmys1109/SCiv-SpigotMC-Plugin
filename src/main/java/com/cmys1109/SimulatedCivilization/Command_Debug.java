package com.cmys1109.SimulatedCivilization;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Command_Debug implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        System.out.println("DEBUG-----------FirstLine");
        System.out.println(player.playerList);
        System.out.println(player.WhitePLayerList);
        System.out.println(player.playerMap);
        System.out.println(team.TeamList);
        System.out.println("DEBUG-----------LastLine");
        return true;
    }
}
