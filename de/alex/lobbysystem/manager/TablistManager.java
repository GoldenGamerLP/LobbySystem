package de.alex.lobbysystem.manager;

import de.simonsator.partyandfriendsgui.api.datarequest.party.PartyData;
import de.simonsator.partyandfriendsgui.api.datarequest.party.PartyDataCallBackRunnable;
import de.simonsator.partyandfriendsgui.api.datarequest.party.PartyDataRequestCallbackAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;

public class TablistManager implements Listener {

    public static TablistManager tm;
    private Scoreboard scoreboard;
    private HashMap<ArrayList<Player>, Team> teams;

    public TablistManager() {
        tm = this;
    }

}
