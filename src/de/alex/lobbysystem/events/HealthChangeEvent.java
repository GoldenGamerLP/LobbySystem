package de.alex.lobbysystem.events;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class HealthChangeEvent implements Listener {

    @EventHandler
    public void onPlayerDamageEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                ((Player) event.getEntity()).playSound(event.getEntity().getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, SoundCategory.MASTER, 1F, 1F);
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDamagePlayerEvent(EntityDamageByBlockEvent event) {
        if (event.getEntity() instanceof Player || event.getDamager() instanceof Player)
            event.setCancelled(true);
    }
}
