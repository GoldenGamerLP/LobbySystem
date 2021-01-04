package de.alex.lobbysystem.events;

import de.alex.lobbysystem.main.Main;
import de.alex.lobbysystem.utils.utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerFlightToggleEvent implements Listener {

    public static final List<Player> allowDoubleJump = new ArrayList<>();
    final int countdown = 6;
    private final HashMap<Player, Integer> list = new HashMap<>();
    private BukkitTask scheduler;

    @EventHandler
    public void onFlightToggleEvent(PlayerToggleFlightEvent event) {
        Player p = event.getPlayer();
        if (list.containsKey(p) && allowDoubleJump.contains(p)) {
            event.setCancelled(true);
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(utils.colorString(String.format("&9Lobby &8» &7Du hast noch &c%d sekunden &eDoubleJump &7cooldown!", list.get(p)))));
        } else if (allowDoubleJump.contains(p)) {
            Vector v = event.getPlayer().getLocation().getDirection().multiply(10D).setY(1.1D);
            p.setVelocity(v);
            //p.setAllowFlight(false);
            p.setFlying(false);
            list.put(p, countdown);
            startScheduler();
            event.setCancelled(true);
        }
    }

    private void startScheduler() {
        if (this.scheduler != null) return;

        this.scheduler = new BukkitRunnable() {

            @Override
            public void run() {

                if (list.size() == 0) {
                    this.cancel();
                    PlayerFlightToggleEvent.this.scheduler = null;
                } else {
                    for (Map.Entry<Player, Integer> pl : list.entrySet()) {
                        Player p = pl.getKey();
                        int cd = pl.getValue();
                        if (cd >= 1 && allowDoubleJump.contains(p)) {
                            list.put(p, cd - 1);
                            p.setFlying(false);
                            //p.setAllowFlight(false);
                        } else if (cd == 0 || !allowDoubleJump.contains(p)) {
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(utils.colorString("&9Lobby &8» &7Du kannst wieder &eDoubleJumpen&7!")));
                            //p.setAllowFlight(true);
                            list.remove(p);
                            this.cancel();
                            scheduler = null;
                        }
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }

}
