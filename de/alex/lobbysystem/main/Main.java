package de.alex.lobbysystem.main;

import de.alex.lobbysystem.commands.BuildCommand;
import de.alex.lobbysystem.commands.FlyCommand;
import de.alex.lobbysystem.commands.PositionCommand;
import de.alex.lobbysystem.events.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    private static Main instance;

    public File locationfile = new File(this.getDataFolder(), "locations.yml");

    public static Main getInstance() {
        return instance;
    }

    public void onEnable() {
        long ms = System.currentTimeMillis();
        instance = this;
        initListeners();
        initFiles(locationfile);
        getLogger().info(String.format("Lobby: Startup wihtout errors in %o ms!", System.currentTimeMillis() - ms));
    }

    private void initFiles(File file) {
        if (!file.exists()) {
            getLogger().info(String.format("File %s gets created!", file.getName()));
            try {
                file.createNewFile();
            } catch (IOException e) {
                getLogger().info(String.format("Couldnt create %s files!", file.getName()));
            }
        }

    }

    public void onDisable() {
        Bukkit.getServer().getOnlinePlayers().forEach(Player -> {
            Player.kickPlayer((String.format("§9Lobby §8» §cDer Server Restartet! \n §7Du kannst gleich wieder auf §%s §cconnecten§7.", "§lLobby")));
        });
    }

    private void initListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinHandler(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerFoodLevelChange(), this);
        Bukkit.getPluginManager().registerEvents(new HealthChangeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MobSpawingEvent(), this);
        Bukkit.getPluginManager().registerEvents(new JumppadPressurePlateEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerFlightToggleEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BlockEvent(), this);
        this.getCommand("fly").setExecutor(new FlyCommand());
        this.getCommand("build").setExecutor(new BuildCommand());
        this.getCommand("position").setExecutor(new PositionCommand());
    }

    public void initPlayer(Player p) {
        PlayerFlightToggleEvent.allowDoubleJump.add(p);
        BlockEvent.build.remove(p);

        p.setGameMode(GameMode.ADVENTURE);
        p.setFoodLevel(20);
        p.setLevel(0);
        p.setAllowFlight(true);
        p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.125);
    }

}
