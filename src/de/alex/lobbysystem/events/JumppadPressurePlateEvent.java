package de.alex.lobbysystem.events;

import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class JumppadPressurePlateEvent implements Listener {

    @EventHandler
    public void JumppadEvent(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.PHYSICAL) && event.getClickedBlock().getType() == Material.SPRUCE_PRESSURE_PLATE && event.getClickedBlock().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.GOLD_BLOCK) {
            Vector v = event.getPlayer().getLocation().getDirection().multiply(10D).setY(1.1D);
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_BAT_TAKEOFF, SoundCategory.MASTER, 1F, 1F);
            event.getPlayer().getLocation().getWorld().spawnParticle(Particle.TOTEM, event.getPlayer().getLocation(), 55, 0.001,0.05,0.001);
            event.getPlayer().setVelocity(v);
        }
    }
}
