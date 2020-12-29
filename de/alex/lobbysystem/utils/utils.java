package de.alex.lobbysystem.utils;

import de.alex.lobbysystem.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class utils {
    DatabaseManager db = new DatabaseManager();
    public static File config = new File(Main.getInstance().getDataFolder(), "config.yml");
    public static YamlConfiguration yaml = YamlConfiguration.loadConfiguration(config);

    public static String joinMessage;
    public static String quitMessage;
    public static Boolean useJoinLeaveMessages;

    public static void reloadFiles() {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(config);
        joinMessage = yml.getString("options.messages.playermessages.joinMessages");
        quitMessage = yml.getString("options.messages.playermessages.quitMessages");
        useJoinLeaveMessages = yml.getBoolean("options.useJoinLeaveMessages");
    }

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

    public static String colorString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
