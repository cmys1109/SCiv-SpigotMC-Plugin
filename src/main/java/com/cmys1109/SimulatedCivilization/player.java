package com.cmys1109.SimulatedCivilization;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class player {
    static ArrayList<String> playerList = new ArrayList<>();//存储所有player
    static ArrayList<String> WhitePLayerList = new ArrayList<>();
    static HashMap<String, player> playerMap = new HashMap<>();//存储在线player，playerQuit时卸载
    static int playerSUM;
    String Name;
    String UUID;
    String Team;

    player(String MCname, File DateFolder) {
        Name = MCname;
        if (playerList.contains(Name)) {
            YamlConfiguration PlayerData = YamlConfiguration.loadConfiguration(new File(DateFolder, "players\\" + Name + ".yml"));
            UUID = Objects.requireNonNull(Bukkit.getPlayer(Name)).getUniqueId().toString();
            Team = PlayerData.getString("team");
            if (Team == null) WhitePLayerList.add(Name);
        } else {
            MakePlayerYAML();
            Team = null;
            WhitePLayerList.add(Name);
            playerList.add(Name);
            playerSUM++;
            SavePlayerList(DateFolder);
        }
        playerMap.put(Name, this);
    }

    public static void GetPlayerList(File DataFolder) {
        File PlayerListFile = new File(DataFolder, "\\players\\_PlayerList.yml");
        YamlConfiguration PlayerListData = YamlConfiguration.loadConfiguration(PlayerListFile);
        playerList = (ArrayList<String>) PlayerListData.getStringList("PlayerList");
        playerSUM = PlayerListData.getInt("PlayerSUM");
    }

    public static void SavePlayerList(File DataFolder) {
        try {
            File PlayerListFile = new File(DataFolder, "\\players\\_PlayerList.yml");
            YamlConfiguration PlayerListData = YamlConfiguration.loadConfiguration(PlayerListFile);
            PlayerListData.set("PlayerList", playerList);
            PlayerListData.set("PlayerSUM", playerSUM);
            PlayerListData.save(PlayerListFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void UpdatePlayerName() {
        if (Team == null) {
            Objects.requireNonNull(Bukkit.getPlayer(Name)).setDisplayName(Name);
            Objects.requireNonNull(Bukkit.getPlayer(Name)).setPlayerListName(Name);
            return;
        }
        String NameByTeam = "§" + team.TeamList.get(Team).TeamColor + " [" + Team + "] " + Name + ChatColor.WHITE;
        Objects.requireNonNull(Bukkit.getPlayer(Name)).setDisplayName(NameByTeam);
        Objects.requireNonNull(Bukkit.getPlayer((Name))).setPlayerListName(NameByTeam);
    }

    public void SavePlayerYAML(File DataFolder) {
        try {
            File PlayerFile = new File(DataFolder, "\\players\\" + Name + ".yml");
            YamlConfiguration PlayerData = YamlConfiguration.loadConfiguration(PlayerFile);
            PlayerData.set("name", Name);
            PlayerData.set("uuid", UUID);
            PlayerData.set("team", Team);
            PlayerData.save(PlayerFile);
            SavePlayerList(DataFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void out() {
        System.out.println("Name:" + Name);
        System.out.println("uuid:" + UUID);
        System.out.println("Team:" + Team);
    }

    private void MakePlayerYAML() {
        String path = System.getProperty("user.dir");
        File PlayerYAML = new File(path + "\\plugins\\SimulatedCivilization\\players\\" + Name + ".yml");

        try {
            if (!PlayerYAML.exists()) {
                if (!PlayerYAML.createNewFile())
                    System.out.println(PlayerYAML.getName() + "文件失败");
            }
            YamlConfiguration PlayerData = YamlConfiguration.loadConfiguration(PlayerYAML);
            PlayerData.set("name", Name);
            PlayerData.set("uuid", UUID);
            PlayerData.set("team", null);
            PlayerData.save(PlayerYAML);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

