package com.cmys1109.SimulatedCivilization;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Command_CivDeadUnbanAll implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        File BanListFile = new File(main.plugin.getDataFolder(), "\\players\\_DeadBanList.yml");
        YamlConfiguration BanListData = YamlConfiguration.loadConfiguration(BanListFile);
        ArrayList<String> BanList = (ArrayList<String>) BanListData.getStringList("BanList");
        BanListData.set("BanList", null);
        try {
            BanListData.save(BanListFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String name : BanList) {
            Bukkit.getBanList(org.bukkit.BanList.Type.NAME).pardon(name);
            sender.sendMessage(name + "已经解除DeadBan");
        }


        return true;
    }
}
