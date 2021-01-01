package de.alex.lobbysystem.main;

import de.alex.lobbysystem.commands.FlyCommand;
import de.alex.lobbysystem.events.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public void onEnable() {
        System.out.println("Lol lets go!!!!§aadsad");
        instance = this;
        initListeners();
    }

    private void initListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinHandler(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerFoodLevelChange(), this);
        Bukkit.getPluginManager().registerEvents(new HealthChangeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MobSpawingEvent(), this);
        Bukkit.getPluginManager().registerEvents(new JumppadPressurePlateEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerFlightToggleEvent(), this);
        this.getCommand("fly").setExecutor(new FlyCommand());
    }

}
