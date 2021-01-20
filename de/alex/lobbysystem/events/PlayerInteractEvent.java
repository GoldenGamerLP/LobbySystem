package de.alex.lobbysystem.events;

import de.alex.lobbysystem.BukkitScheduler.InventoryScheduler;
import de.alex.lobbysystem.api.PositionAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerInteractEvent implements Listener {

    PositionAPI ps = new PositionAPI();

    @EventHandler
    public void onPlayerInteractEvent(org.bukkit.event.player.PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getMaterial() == Material.BLAZE_POWDER) {
                InventoryScheduler.startCompassScheduler(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onInvenotryInteract(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        Player p = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("§8» §f§nCompass")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType() == Material.BIRCH_DOOR) {
                if (ps.getLocation("spawn") != null) {
                    p.teleport(ps.getLocation("spawn"), PlayerTeleportEvent.TeleportCause.SPECTATE);
                    p.closeInventory();
                }
            }
        }

    }
}
