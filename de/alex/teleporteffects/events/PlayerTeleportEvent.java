package de.alex.teleporteffects.events;

import de.alex.teleporteffects.main.Main;
import de.alex.teleporteffects.utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class PlayerTeleportEvent implements Listener {


    HashMap<Player, Boolean> list = new HashMap<>();
    public int timertime = 3;

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerTeleport(org.bukkit.event.player.PlayerTeleportEvent event) {
        Player p = event.getPlayer();
        BossBar bs = utils.createBossbar("countdown", BarColor.WHITE, BarStyle.SOLID);
        Location to = event.getTo();
        Location is = event.getFrom();
        if (event.getCause() == org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.SPECTATE) {
            if (p.getLocation().getBlock().getLocation().distance(to.getBlock().getLocation()) >= 1.5) {
                if (list.containsKey(p) && list.get(p)) {
                    p.sendMessage("2");
                    event.setCancelled(true);
                    bs.removePlayer(p);
                    bs.removeAll();
                } else {
                    event.setTo(is);
                    bs.addPlayer(p);
                    new BukkitRunnable() {
                        int time = 0;

                        @Override
                        public void run() {
                            double percentage = 1D - time / ((double) timertime * 10);
                            if (!p.isOnline()) {
                                this.cancel();
                            }
                            if (is.getBlock().getLocation().distance(p.getLocation().getBlock().getLocation()) >= 0.8) {
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Teleportation canceld"));
                                list.remove(p);
                                bs.removeAll();
                                bs.removePlayer(p);
                                cancel();
                            } else
                                bs.setTitle(ChatColor.GRAY + "Teleportiere in " + ChatColor.YELLOW + (timertime - (time / 10)));
                            if (time == 1) {
                                list.put(p, true);
                                bs.setProgress(1.0);
                            } else if (time == 10 * timertime) {
                                list.put(p, false);
                                bs.removePlayer(p);
                                teleportPlayer(p, to);
                                bs.removeAll();
                                list.remove(p);
                                cancel();
                            }
                            bs.setProgress(percentage);
                            time = time + 1;
                        }
                    }.runTaskTimer(Main.getInstance(), 0, 2);

                }
            } else {
                event.setCancelled(true);
                p.sendMessage("canceld!");
            }
        } return;
    }

    private void teleportPlayer(Player p, Location loc) {
        p.setVelocity(new Vector(0, 2.1, 0));
        p.playEffect(EntityEffect.TELEPORT_ENDER);
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 65,1, false));
        new BukkitRunnable() {
            @Override
            public void run() {
                p.teleport(loc);
                p.playEffect(EntityEffect.TELEPORT_ENDER);
            }
        }.runTaskLater(Main.getInstance(), 40);
    }

}
