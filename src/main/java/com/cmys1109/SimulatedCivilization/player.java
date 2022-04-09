package com.cmys1109.SimulatedCivilization;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class player {
    static ArrayList<String> playerList = new ArrayList<>();//存储所有player
    static ArrayList<String> WhitePLayerList = new ArrayList<>();
    static HashMap<String, player> playerMap = new HashMap<>();//存储在线player，playerQuit时卸载
    String Name;
    UUID uuid;
    String Team;

    player(@NotNull UUID MCuuid) {
        uuid = MCuuid;
        if (playerList.contains(MCuuid.toString())) {
            Name = Objects.requireNonNull(Bukkit.getPlayer(uuid)).getName();
            YamlConfiguration PlayerData = YamlConfiguration.loadConfiguration(new File(main.plugin.getDataFolder(), "players\\" + uuid + ".yml"));
            Team = PlayerData.getString("team");
            if (Team == null) WhitePLayerList.add(uuid.toString());
        } else {
            Name = Objects.requireNonNull(Bukkit.getPlayer(uuid)).getName();
            MakePlayerYAML();
            WhitePLayerList.add(uuid.toString());
            playerList.add(uuid.toString());
            SavePlayerList();
        }
        playerMap.put(uuid.toString(), this);
    }

    public static void GetPlayerList() {
        File PlayerListFile = new File(main.plugin.getDataFolder(), "\\players\\_PlayerList.yml");
        YamlConfiguration PlayerListData = YamlConfiguration.loadConfiguration(PlayerListFile);
        playerList = (ArrayList<String>) PlayerListData.getStringList("PlayerList");
    }

    public static void SavePlayerList() {
        try {
            File PlayerListFile = new File(main.plugin.getDataFolder(), "\\players\\_PlayerList.yml");
            YamlConfiguration PlayerListData = YamlConfiguration.loadConfiguration(PlayerListFile);
            PlayerListData.set("PlayerList", playerList);
            PlayerListData.save(PlayerListFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void UpdatePlayerName() {
        if (Team == null) {
            Objects.requireNonNull(Bukkit.getPlayer(uuid)).setDisplayName(uuid.toString());
            Objects.requireNonNull(Bukkit.getPlayer(uuid)).setPlayerListName(uuid.toString());
            return;
        }
        String NameByTeam = "§" + team.TeamList.get(Team).TeamColor + " [" + Team + "] " + Name + ChatColor.WHITE;
        Objects.requireNonNull(Bukkit.getPlayer(uuid)).setDisplayName(NameByTeam);
        Objects.requireNonNull(Bukkit.getPlayer((uuid))).setPlayerListName(NameByTeam);
    }

    public void SavePlayerYAML() {
        try {
            File PlayerFile = new File(main.plugin.getDataFolder(), "\\players\\" + uuid + ".yml");
            YamlConfiguration PlayerData = YamlConfiguration.loadConfiguration(PlayerFile);
            PlayerData.set("name", Name);
            PlayerData.set("uuid", uuid.toString());
            PlayerData.set("team", Team);
            PlayerData.save(PlayerFile);
            SavePlayerList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void MakePlayerYAML() {
        String path = System.getProperty("user.dir");
        File PlayerYAML = new File(path + "\\plugins\\SimulatedCivilization\\players\\" + uuid + ".yml");

        try {
            if (!PlayerYAML.exists()) {
                if (!PlayerYAML.createNewFile())
                    System.out.println(PlayerYAML.getName() + "文件失败");
            }
            YamlConfiguration PlayerData = YamlConfiguration.loadConfiguration(PlayerYAML);
            PlayerData.set("name", Name);
            PlayerData.set("uuid", uuid.toString());
            PlayerData.set("team", null);
            PlayerData.save(PlayerYAML);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

