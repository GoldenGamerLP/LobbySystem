package de.alex.lobbysystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MobSpawingEvent implements Listener {

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent event) {
        event.setCancelled(true);
    }
}
