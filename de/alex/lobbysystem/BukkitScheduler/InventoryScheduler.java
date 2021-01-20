package de.alex.lobbysystem.BukkitScheduler;

import de.alex.lobbysystem.main.Main;
import de.alex.lobbysystem.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class InventoryScheduler {

    static HashMap<Integer, ItemStack> list = new HashMap<>();
    static Inventory inv;
    private static BukkitTask task;

    public static void initInventory() {
        inv = Bukkit.createInventory(null, 54, utils.colorString("&8» &f&nCompass"));
        list.put(7, (utils.getItem("Hi", Material.ANDESITE_SLAB, 1, "%s")));
        list.put(6, (utils.getItem("Hi", Material.BIRCH_DOOR, 1, "TadA")));

    }

    public static void startCompassScheduler(Player p) {
        p.openInventory(inv);
        if (task != null) task.cancel();
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (inv.getViewers().size() == 0) {
                    this.cancel();
                    task = null;
                }
                inv.setItem(2, utils.getItem("asda", Material.BIRCH_DOOR, 1, String.format("%s", System.currentTimeMillis() + "")));
                inv.getViewers().forEach(HumanEntity -> {
                    Player p = (Player) HumanEntity;
                    p.updateInventory();
                });
            }
        }.runTaskTimer(Main.getInstance(), 0, 15);
    }

}
