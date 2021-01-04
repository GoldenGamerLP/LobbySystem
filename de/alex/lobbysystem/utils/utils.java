package de.alex.lobbysystem.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class utils {


    static final BarFlag[] EMPTY_ARRAY = new BarFlag[0];

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

    public static String colorString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static Boolean permissionMSG(String perm, Player p) {
        if (p.hasPermission(perm)) {
            return true;
        } else {
            p.sendMessage(utils.colorString(String.format("&9Lobby &8» &cDazu hast du keine Berechtigung! &7(&e%s&7)", perm)));
            return false;
        }
    }
}
