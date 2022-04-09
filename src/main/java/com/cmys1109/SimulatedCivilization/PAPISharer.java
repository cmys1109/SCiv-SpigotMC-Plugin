package com.cmys1109.SimulatedCivilization;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PAPISharer extends PlaceholderExpansion {

    public PAPISharer(main plugin) {
    }

    @Override
    public @NotNull String getAuthor() {
        return "cmys1109";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "SCiv";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.2.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    /*
     * Civ      玩家所属文明，没有归属文明则返回null
     * CivColor 玩家所属文明显示颜色,没有归属文明则返回null
     * InCiv    玩家现在所处于的文明，不在任何文明区域则返回null
     */
    @Override
    public String onPlaceholderRequest(Player p, @NotNull String string) {
        switch (string) {
            case "Civ" -> {
                player pl = player.playerMap.get(p.getUniqueId().toString());
                if (pl == null) return null;
                return pl.Team;
            }
            case "CivColor" -> {
                player pl = player.playerMap.get(p.getUniqueId().toString());
                if (pl == null) return null;
                team t = team.TeamList.get(pl.Team);
                if (t == null) return null;
                return t.TeamColor;
            }
            case "InCiv" -> {
                if (listener.SphereLog.get(p.getUniqueId()) == null) return "0";
                return listener.SphereLog.get(p.getUniqueId());
            }
        }
        return null;
    }
}
