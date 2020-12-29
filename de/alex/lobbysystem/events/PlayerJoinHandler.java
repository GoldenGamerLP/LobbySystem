package de.alex.lobbysystem.events;

import de.alex.lobbysystem.utils.utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinHandler implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        event.setJoinMessage(String.format(utils.colorString("&e» %s"), event.getPlayer().getDisplayName()));
    }

    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        PlayerTeleportEvent.list.remove(p);
        event.setQuitMessage(String.format(utils.colorString("&e« %s"), event.getPlayer().getDisplayName()));
    }
}
