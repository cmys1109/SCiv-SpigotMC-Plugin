package com.cmys1109.SimulatedCivilization;

//文明内部还是用team表示

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class team {
    static HashMap<String, team> TeamList = new HashMap<>();
    String Name;
    int MemberMax;
    ArrayList<String> MemberList;
    Location BirthPoint;
    String TeamColor;

    team(String teamName, int teamMemberMAX, ArrayList<String> teamMemberList, Location teamBrithPoint) {
        Name = teamName;
        MemberMax = teamMemberMAX;
        BirthPoint = teamBrithPoint;
        TeamList.put(Name, this);
        MemberList = teamMemberList;
        System.out.printf("%f,%f,%f\n", BirthPoint.getX(), BirthPoint.getY(), BirthPoint.getZ());
        File CivilizationFile = new File(main.plugin.getDataFolder(), "Civilization.yml");
        FileConfiguration CivilizationConf = YamlConfiguration.loadConfiguration(CivilizationFile);
        TeamColor = CivilizationConf.getString("CivilizationDetails." + Name + ".TeamColor");
    }

    public static void LoadCivYAML() {
        File CivilizationFile = new File(main.plugin.getDataFolder(), "Civilization.yml");
        FileConfiguration CivilizationConf = YamlConfiguration.loadConfiguration(CivilizationFile);
        String[] CivilizationList = CivilizationConf.getStringList("CivilizationList").toArray(new String[0]);
        for (String CivName : CivilizationList) {
            int mMAX = CivilizationConf.getInt("CivilizationDetails." + CivName + ".MemberMAX");

            Location BrithPoint = new Location(Bukkit.getWorld("world"), CivilizationConf.getDouble("CivilizationDetails." + CivName + ".BirthPoint.x"),
                    CivilizationConf.getDouble("CivilizationDetails." + CivName + ".BirthPoint.y"),
                    CivilizationConf.getDouble("CivilizationDetails." + CivName + ".BirthPoint.z"));

            ArrayList<String> teamMemberList = (ArrayList<String>) CivilizationConf.getStringList("CivilizationDetails." + CivName + ".MemberList");
            team Civ = new team(CivName, mMAX, teamMemberList, BrithPoint);
        }
    }

    public static void SaveCivYAML(File DataFolder) {
        File CivilizationFile = new File(DataFolder, "Civilization.yml");
        FileConfiguration CivilizationConf = YamlConfiguration.loadConfiguration(CivilizationFile);
        String[] CivilizationList = CivilizationConf.getStringList("CivilizationList").toArray(new String[0]);
        for (String CivName : CivilizationList) {

            CivilizationConf.set("CivilizationDetails." + CivName + ".MemberList", team.TeamList.get(CivName).MemberList);
        }
        try {
            CivilizationConf.save(CivilizationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean append(player p, File DataFolder) {
        if (MemberList.size() >= MemberMax) {// 如果现有人数大于等于设定的最大人数，则返回false
            return true;
        }
        if (MemberList.contains(p.Name)) {// 已经是该文明的成员，则返回false
            return true;
        }
        if (p.Team != null) {
            if (team.TeamList.get(p.Team).delete(p, DataFolder)) {
                return true;
            }
        }
        p.Team = Name;
        MemberList.add(p.Name);
        p.SavePlayerYAML(DataFolder);
        SaveCivYAML(DataFolder);
        return false;
    }

    public boolean delete(@NotNull player p, File DataFolder) {
        if (!MemberList.contains(p.Name)) {// 如果不是该文明成员则返回false
            return false;
        }
        MemberList.remove(p.Name);
        File PlayerFile = new File(DataFolder, "\\players\\" + p.Name + ".yml");
        FileConfiguration PlayerConf = YamlConfiguration.loadConfiguration(PlayerFile);
        PlayerConf.set("Team", null);
        player.WhitePLayerList.add(p.Name);
        p.Team = null;
        p.SavePlayerYAML(DataFolder);
        SaveCivYAML(DataFolder);
        p.UpdatePlayerName();
        return true;
    }
}
