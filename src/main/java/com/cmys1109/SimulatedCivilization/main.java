package com.cmys1109.SimulatedCivilization;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class main extends JavaPlugin implements Listener, CommandExecutor {
    public static main plugin;

    {
        plugin = this;
    }

    @Override
    public void onEnable() {

        // 文明配置初始化
        this.saveResource("Civilization.yml", false);
        team.LoadCivYAML();

        // player初始化
        this.saveResource("players\\_PlayerList.yml", false);
        player.GetPlayerList(this.getDataFolder());
        for (Player p : getServer().getOnlinePlayers()) {
            player New = new player(p.getName(), this.getDataFolder());
        }

        // 注册监听器
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getServer().getPluginCommand("CivList")).setExecutor(new Command_CivList());
        Objects.requireNonNull(getServer().getPluginCommand("CivJoin")).setExecutor(new Command_CivJoin());
        Objects.requireNonNull(getServer().getPluginCommand("CivStart")).setExecutor(new Command_CivStart());
        Objects.requireNonNull(getServer().getPluginCommand("CivDebug")).setExecutor(new Command_Debug());
        Objects.requireNonNull(getServer().getPluginCommand("CivQuit")).setExecutor(new Command_CivQuit());

        getLogger().info("Simulated civilization loaded.");
    }

    @Override
    public void onDisable() {
        team.SaveCivYAML(this.getDataFolder());
        getLogger().info("Bye~");
    }

    @EventHandler
    private void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        String name = event.getPlayer().getName();
        //player.GetPlayerList(this.getDataFolder());
        player New = new player(name, this.getDataFolder());
        if (New.Team != null) New.UpdatePlayerName();
        event.setJoinMessage("Welcome " + name + " join the server!");
    }

    @EventHandler
    private void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        String name = event.getPlayer().getName();
        team.SaveCivYAML(this.getDataFolder());
        player.SavePlayerList(this.getDataFolder());
        player.playerMap.remove(name);
        player.WhitePLayerList.remove(name);
    }
}