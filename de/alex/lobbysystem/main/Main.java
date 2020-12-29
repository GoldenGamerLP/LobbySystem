package de.alex.lobbysystem.main;

import de.alex.lobbysystem.events.PlayerJoinHandler;
import de.alex.lobbysystem.events.PlayerTeleportEvent;
import de.alex.lobbysystem.utils.DatabaseManager;
import de.alex.lobbysystem.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Main extends JavaPlugin {

    private static Main instance;
    DatabaseManager db = new DatabaseManager();

    public static Main getInstance() {
        return instance;
    }

    public void onEnable() {
        System.out.println("Lol lets go!!!!§aadsad");
        instance = this;
        initFile();
        initListeners();
        initValues();
    }

    private void initValues() {
        utils.reloadFiles();
    }

    private void initListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinHandler(), this);
    }

    private void initFile(){
        if(db.existsFile("config")) {
            Bukkit.getConsoleSender().sendMessage(utils.colorString("&9Lobby &8» &eConfig wurde gefunden!"));
        } else {
            Bukkit.getConsoleSender().sendMessage(utils.colorString("&9Lobby &8» &eConfig wurde nicht gefunden, wird erstellt!"));
            try {
                this.getDataFolder().mkdirs();
                utils.config.createNewFile();
                utils.yaml.set("options.useJoinLeaveMessages", false);
                utils.yaml.set("options.messages.playermessages.joinMessages", "&9Lobby &8» &a%p");
                utils.yaml.set("options.messages.playermessages.quitMessages", "&9Lobby &8» &c%p");
                utils.yaml.save(utils.config);
            }catch (IOException ex) {
                this.setEnabled(false);
                ex.printStackTrace();
            }
        }
    }

}
