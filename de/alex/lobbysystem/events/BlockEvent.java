package de.alex.lobbysystem.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class BlockEvent implements Listener {

    public static final List<Player> build = new ArrayList<>();

    @EventHandler
    public void onBreakEvent(BlockBreakEvent event) {
        Player p = event.getPlayer();
        event.setCancelled(!build.contains(p));

    }

    @EventHandler
    public void onBuildEvent(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        event.setCancelled(!build.contains(p));
    }

    @EventHandler
    public void onBlockDamageEvent(BlockDamageEvent event) {
        Player p = event.getPlayer();
        if (build.contains(p)) {
            event.setCancelled(false);
            event.setInstaBreak(true);
        } else event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPhysicsEvent(BlockPhysicsEvent event) {
        event.setCancelled(event.getBlock().getType() != Material.TNT);
    }

    @EventHandler
    public void EntityChangeBlock(EntityChangeBlockEvent event) {
        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);
    }

    @EventHandler
    public void BlockExplodeEvent(EntityExplodeEvent event) {
        List<Block> blocks = event.blockList();
        for (org.bukkit.block.Block Block : blocks) {
            double x = Math.random();
            double y = 1.0;
            double z = Math.random();

            FallingBlock fallingBlock = Block.getWorld().spawnFallingBlock(event.getLocation(), Block.getBlockData());
            fallingBlock.setVelocity(new Vector(x, y, z));
            fallingBlock.setGravity(true);
            fallingBlock.setDropItem(false);
        }
        event.setCancelled(true);
    }
}
