package com.cmys1109.SimulatedCivilization;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BanDead implements Listener {
    @EventHandler
    public void onPlayerDead(@NotNull PlayerDeathEvent event) {
        Player player = event.getEntity();

        player.kickPlayer("你已经被踢出服务器并且被Ban，因为你死亡了。");

        // 增加到BanList
        File BanListFile = new File(main.plugin.getDataFolder(), "\\players\\_DeadBanList.yml");
        YamlConfiguration BanListData = YamlConfiguration.loadConfiguration(BanListFile);
        ArrayList<String> BanList = (ArrayList<String>) BanListData.getStringList("BanList");
        BanList.add(player.getName());
        BanListData.set("BanList", BanList);
        try {
            BanListData.save(BanListFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(player.getName(), "你死亡了", null, null);
    }
}
