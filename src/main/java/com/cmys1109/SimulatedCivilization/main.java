package com.cmys1109.SimulatedCivilization;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class main extends JavaPlugin implements Listener, CommandExecutor {
    public static main plugin;

    {
        plugin = this;
    }

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        FileConfiguration Conf = this.getConfig();
        this.saveResource("SimulatedCivilization_user_manual.md", false);

        // 文明配置初始化
        this.saveResource("Civilization.yml", false);
        team.LoadCivYAML();

        // player初始化
        this.saveResource("players\\_PlayerList.yml", false);
        player.GetPlayerList();
        for (Player p : getServer().getOnlinePlayers()) {
            player New = new player(p.getName());
        }

        // 注册监听器
        getServer().getPluginManager().registerEvents(new listener(), this);
        Objects.requireNonNull(getServer().getPluginCommand("CivList")).setExecutor(new Command_CivList());
        Objects.requireNonNull(getServer().getPluginCommand("CivJoin")).setExecutor(new Command_CivJoin());
        Objects.requireNonNull(getServer().getPluginCommand("CivStart")).setExecutor(new Command_CivStart());
        Objects.requireNonNull(getServer().getPluginCommand("CivQuit")).setExecutor(new Command_CivQuit());

        // Debug 开关
        if (Conf.getBoolean("Debug"))
            Objects.requireNonNull(getServer().getPluginCommand("CivDebug")).setExecutor(new Command_Debug());

        // BanDead 开关
        if (Conf.getBoolean("BanDead")) {
            this.saveResource("players\\_DeadBanList.yml", false);
            getServer().getPluginManager().registerEvents(new BanDead(), this);
            Objects.requireNonNull(getServer().getPluginCommand("CivDeadUnban")).setExecutor(new Command_CivDeadUnban());
            Objects.requireNonNull(getServer().getPluginCommand("CivDeadUnbanAll")).setExecutor(new Command_CivDeadUnbanAll());
        }

        getLogger().info("Simulated civilization loaded.");
    }

    @Override
    public void onDisable() {
        team.SaveCivYAML();
        getLogger().info("Bye~");
    }
}