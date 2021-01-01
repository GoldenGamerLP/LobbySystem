package de.alex.lobbysystem.events;

import de.alex.lobbysystem.main.Main;
import de.alex.lobbysystem.utils.utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class PlayerTeleportEvent implements Listener {

    public static HashMap<Player, Boolean> list = new HashMap<>();
    public int timertime = 3;

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerTeleport(org.bukkit.event.player.PlayerTeleportEvent event) {

        Player p = event.getPlayer();
        Location to = event.getTo();
        Location is = event.getFrom();
        BossBar bs = utils.createBossbar("countdown", BarColor.WHITE, BarStyle.SOLID);

        if (event.getCause() == org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.SPECTATE) {
            assert to != null;
            if (p.getLocation().getBlock().getLocation().distance(to.getBlock().getLocation()) >= 1) {
                if (list.containsKey(p)) {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(utils.colorString("&9Lobby &8» &7Du wirst schon teleportiert!")));
                    event.setCancelled(true);
                    bs.removePlayer(p);
                    bs.removeAll();
                } else {
                    PlayerFlightToggleEvent.allowDoubleJump.remove(p);
                    list.put(p, true);
                    event.setCancelled(true);
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
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(utils.colorString("&9Lobby &8» &7Teleporation &cabgebrochen&7!")));
                                list.remove(p);
                                bs.removeAll();
                                bs.removePlayer(p);
                                PlayerFlightToggleEvent.allowDoubleJump.add(p);
                                this.cancel();
                            } else
                                bs.setTitle(utils.colorString(String.format("&9Lobby &8» &7Du wirst in &e%d &7teleportiert!", (timertime - (time / 10)))));
                            if (time == 1) {
                                bs.setProgress(1.0);
                            } else if (time == 10 * timertime) {
                                list.put(p, false);
                                bs.removePlayer(p);
                                teleportPlayer(p, to);
                                bs.removeAll();
                                PlayerFlightToggleEvent.allowDoubleJump.add(p);
                                this.cancel();
                            }
                            bs.setProgress(percentage);
                            time = time + 1;
                        }
                    }.runTaskTimer(Main.getInstance(), 0, 2);

                }
            } else {
                event.setCancelled(true);
                p.sendMessage(utils.colorString("&9Lobby &8» &7Du bist zu &cnah&7 am Entpunkt der Teleportations."));
            }
        }
    }

    private void teleportPlayer(Player p, Location loc) {
        p.setVelocity(new Vector(0, 2.4, 0));
        p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, SoundCategory.MASTER, 1f, 1f);
        p.playEffect(EntityEffect.FIREWORK_EXPLODE);
        new BukkitRunnable() {
            @Override
            public void run() {
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 35, 1, false));
                p.teleport(loc.add(0, 2, 0));
                p.playEffect(EntityEffect.TELEPORT_ENDER);
            }
        }.runTaskLater(Main.getInstance(), 40);
        list.remove(p);
    }

    @EventHandler
    public void onLaura(PlayerDropItemEvent event) {
        event.setCancelled(true);
        event.getPlayer().teleport(event.getPlayer().getLocation().add(0,2,5), org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.SPECTATE);
    }

}
