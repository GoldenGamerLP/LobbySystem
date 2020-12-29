package de.alex.teleporteffects.main;

import de.alex.teleporteffects.events.PlayerTeleportEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    public void onEnable() {
        System.out.println("Lol lets go!!!!§aadsad");
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportEvent(), this);
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

}
