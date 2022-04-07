package com.cmys1109.SimulatedCivilization;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class listener implements Listener {
    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        String name = event.getPlayer().getName();
        player New = new player(name);
        if (New.Team != null) New.UpdatePlayerName();
        event.setJoinMessage(event.getPlayer().getDisplayName() + " 进入了服务器");
    }

    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        String name = event.getPlayer().getName();
        team.SaveCivYAML();
        player.SavePlayerList();
        player.playerMap.remove(name);
        player.WhitePLayerList.remove(name);
    }

    @EventHandler
    public void onPlayerDead(@NotNull PlayerDeathEvent event) {
        if (player.playerMap.get(event.getEntity().getName()).Team == null) return;
        team t = team.TeamList.get(player.playerMap.get(event.getEntity().getName()).Team);
        Bukkit.broadcastMessage("§" + t.TeamColor + t.Name + "减员！");
    }
}
