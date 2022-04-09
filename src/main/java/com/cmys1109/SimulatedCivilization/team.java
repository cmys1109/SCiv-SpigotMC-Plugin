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
import java.util.Objects;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class team {
    static HashMap<String, team> TeamList = new HashMap<>();
    String Name;
    int MemberMax;
    ArrayList<String> MemberList;
    Location BirthPoint;
    String TeamColor;
    String SphereShape;
    int SphereRadius;

    team(String teamName) {
        Name = teamName;
        TeamList.put(Name, this);
        File CivilizationFile = new File(main.plugin.getDataFolder(), "Civilization.yml");
        FileConfiguration CivilizationConf = YamlConfiguration.loadConfiguration(CivilizationFile);
        BirthPoint = new Location(Bukkit.getWorld("world"), CivilizationConf.getDouble("CivilizationDetails." + Name + ".BirthPoint.x"),
                CivilizationConf.getDouble("CivilizationDetails." + Name + ".BirthPoint.y"),
                CivilizationConf.getDouble("CivilizationDetails." + Name + ".BirthPoint.z"));
        MemberMax = CivilizationConf.getInt("CivilizationDetails." + Name + ".MemberMAX");
        MemberList = (ArrayList<String>) CivilizationConf.getStringList("CivilizationDetails." + Name + ".MemberList");
        TeamColor = CivilizationConf.getString("CivilizationDetails." + Name + ".TeamColor");
        if (main.plugin.getConfig().getBoolean("SphereOfInfluence")) {
            SphereShape = CivilizationConf.getString("CivilizationDetails." + Name + ".SphereOfInfluence.shape");
            SphereRadius = CivilizationConf.getInt("CivilizationDetails." + Name + ".SphereOfInfluence.radius");
        }
    }

    public static void LoadCivYAML() {
        File CivilizationFile = new File(main.plugin.getDataFolder(), "Civilization.yml");
        FileConfiguration CivilizationConf = YamlConfiguration.loadConfiguration(CivilizationFile);
        String[] CivilizationList = CivilizationConf.getStringList("CivilizationList").toArray(new String[0]);
        for (String CivName : CivilizationList) {
            team Civ = new team(CivName);
        }
    }

    public static void SaveCivYAML() {
        File CivilizationFile = new File(main.plugin.getDataFolder(), "Civilization.yml");
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

    public boolean append(player p) {
        if (MemberList.size() >= MemberMax) {// 如果现有人数大于等于设定的最大人数，则返回false
            return true;
        }
        if (MemberList.contains(p.uuid.toString())) {// 已经是该文明的成员，则返回false
            return true;
        }
        if (p.Team != null) {
            if (team.TeamList.get(p.Team).delete(p)) {
                return true;
            }
        }
        p.Team = Name;
        MemberList.add(p.uuid.toString());
        player.WhitePLayerList.remove(p.uuid.toString());
        p.SavePlayerYAML();
        SaveCivYAML();
        return false;
    }

    public boolean delete(@NotNull player p) {
        if (!MemberList.contains(p.uuid.toString())) {// 如果不是该文明成员则返回false
            return false;
        }
        MemberList.remove(p.uuid.toString());
        File PlayerFile = new File(main.plugin.getDataFolder(), "\\players\\" + p.uuid + ".yml");
        FileConfiguration PlayerConf = YamlConfiguration.loadConfiguration(PlayerFile);
        PlayerConf.set("Team", null);
        player.WhitePLayerList.add(p.uuid.toString());
        p.Team = null;
        p.SavePlayerYAML();
        SaveCivYAML();
        p.UpdatePlayerName();
        return true;
    }

    public boolean inSphere(@NotNull Location location) {
        // 判断是否为同一维度
        if (!Objects.requireNonNull(BirthPoint.getWorld()).getName().equals(Objects.requireNonNull(location.getWorld()).getName())) {
            main.plugin.getLogger().info("No");
            return false;
        }
        if (SphereShape == null) {
            main.plugin.getLogger().info("SphereShape == null");
            return false;
        }
        double xx, yy, zz, xMax, xMin, yMax, yMin, zMax, zMin;
        switch (SphereShape) {
            case "circular" -> {
                xx = pow(location.getX() - BirthPoint.getX(), 2);
                zz = pow(location.getZ() - BirthPoint.getZ(), 2);
                if (sqrt(xx + zz) > SphereRadius) return false;
            }
            case "ball" -> {
                xx = pow(location.getX() - BirthPoint.getX(), 2);
                yy = pow(location.getY() - BirthPoint.getY(), 2);
                zz = pow(location.getZ() - BirthPoint.getZ(), 2);
                if (sqrt(yy + xx + zz) > SphereRadius) return false;
            }
            case "square" -> {
                xMax = BirthPoint.getX() + SphereRadius;
                xMin = BirthPoint.getX() - SphereRadius;
                zMax = BirthPoint.getZ() + SphereRadius;
                zMin = BirthPoint.getZ() - SphereRadius;
                if (!((xMin < location.getX() && xMax > location.getX()) && (zMin < location.getZ() && zMax > location.getZ())))
                    return false;
            }
            case "cube" -> {
                xMax = BirthPoint.getX() + SphereRadius;
                xMin = BirthPoint.getX() - SphereRadius;
                yMax = BirthPoint.getY() + SphereRadius;
                yMin = BirthPoint.getY() - SphereRadius;
                zMax = BirthPoint.getZ() + SphereRadius;
                zMin = BirthPoint.getZ() - SphereRadius;
                if (!((xMin < location.getX() && xMax > location.getX()) && (yMin < location.getY() && yMax > location.getY()) && (zMin < location.getZ() && zMax > location.getZ())))
                    return false;
            }
            default -> {
                main.plugin.getLogger().info("inSphere 错误");
                return false;
            }
        }
        return true;
    }
}
