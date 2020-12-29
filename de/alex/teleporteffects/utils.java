package de.alex.teleporteffects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class utils {
    static BarFlag[] EMPTY_ARRAY = new BarFlag[0];

    public static BossBar createBossbar(String title, org.bukkit.boss.BarColor ch, BarStyle style) {
        BossBar bs = Bukkit.createBossBar(title, ch, style, EMPTY_ARRAY);
        bs.addFlag(BarFlag.CREATE_FOG);
        bs.addFlag(BarFlag.DARKEN_SKY);
        return bs;
    }

    public static void teleport(Player p, Location loc) {
        p.sendMessage("Teleported!");
        p.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
    }
}
