package com.cmys1109.SimulatedCivilization;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class listener implements Listener {
    static HashMap<UUID, Integer> Tasks = new HashMap<>();//Log tasks
    static HashMap<UUID, String> SphereLog = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        player New = new player(event.getPlayer().getUniqueId());
        if (New.Team != null) New.UpdatePlayerName();
        event.setJoinMessage(event.getPlayer().getDisplayName() + " 进入了服务器");

        // Run task to track location
        if (main.plugin.getConfig().getBoolean("SphereOfInfluence")) {
            Tasks.put(event.getPlayer().getUniqueId(), Bukkit.getScheduler().runTaskTimer(main.plugin, () -> {
                String SphereName = null;
                UUID uuid = event.getPlayer().getUniqueId();
                for (team Team :
                        team.TeamList.values()) {
                    if (Team.inSphere(event.getPlayer().getLocation())) {
                        SphereName = Team.Name;// 可以实现更加详细的判断，比如双重势力范围显示
                    }                          // 考虑到这种情况应当避免，并且从各方面考虑还是直接采用for并且直接覆盖写入的方法
                }
                SphereLog.put(uuid, SphereName);
                String OldSphere = SphereLog.get(uuid);
                if (!Objects.equals(SphereName, OldSphere) && SphereName != null) {
                    SphereLog.put(uuid, SphereName);
                    //TODO：接下来提供提示信息的开关，可以通过第三方插件对接接口进行自定义化提示
                    event.getPlayer().sendMessage("你进入了 §" + team.TeamList.get(SphereName).TeamColor + SphereName);
                } else if (!Objects.equals(SphereName, OldSphere)) {
                    SphereLog.put(uuid, SphereName);
                    event.getPlayer().sendMessage("你离开了 §" + team.TeamList.get(OldSphere).TeamColor + OldSphere);
                }
            }, 0L, 20L).getTaskId());
        }
    }

    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {

        // cancel task when player quit
        if (Tasks.containsKey(event.getPlayer().getUniqueId()))
            Bukkit.getScheduler().cancelTask(Tasks.get(event.getPlayer().getUniqueId()));

        String uuid = event.getPlayer().getUniqueId().toString();
        team.SaveCivYAML();
        player.SavePlayerList();
        player.playerMap.remove(uuid);
        player.WhitePLayerList.remove(uuid);
    }

    @EventHandler
    public void onPlayerDead(@NotNull PlayerDeathEvent event) {
        if (player.playerMap.get(event.getEntity().getUniqueId().toString()).Team == null) return;
        team t = team.TeamList.get(player.playerMap.get(event.getEntity().getUniqueId().toString()).Team);
        Bukkit.broadcastMessage("§" + t.TeamColor + t.Name + "减员！");
    }
}
