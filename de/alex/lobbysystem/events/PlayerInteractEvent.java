package de.alex.lobbysystem.events;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class PlayerInteractEvent implements Listener {

    public void onPlayerInteractEvent(org.bukkit.event.player.PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (event.getItem().getType() == Material.BLAZE_POWDER) {

            }
        }
    }
}
