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

public class Command_CivDeadUnban implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        String name = args[0];
        if (!Bukkit.getBanList(org.bukkit.BanList.Type.NAME).isBanned(name)) {
            sender.sendMessage(name + "没有被Ban");
            return true;
        }

        File BanListFile = new File(main.plugin.getDataFolder(), "\\players\\_DeadBanList.yml");
        YamlConfiguration BanListData = YamlConfiguration.loadConfiguration(BanListFile);
        ArrayList<String> BanList = (ArrayList<String>) BanListData.getStringList("BanList");
        if (!BanList.contains(name)) {
            sender.sendMessage(name + "不在BanList中，详见\\players\\_DeadBanList.yml");
            return true;
        }
        BanList.remove(name);
        BanListData.set("BanList", BanList);
        try {
            BanListData.save(BanListFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        sender.sendMessage(name + "已经解除DeadBan");
        Bukkit.getBanList(org.bukkit.BanList.Type.NAME).pardon(name);

        return true;
    }
}
