package de.alex.lobbysystem.api;

import de.alex.lobbysystem.main.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nonnull;

public class PositionAPI {
    public static PositionAPI pa;
    private final YamlConfiguration yml = YamlConfiguration.loadConfiguration(Main.getInstance().locationfile);

    public PositionAPI() {
        pa = this;
    }

    public Location getLocation(@Nonnull String name) {
        if (Main.getInstance().locationfile.exists()) {
            if (yml.get("Location." + name.toLowerCase()) != null) {
                return yml.getLocation("Location." + name.toLowerCase());

            }
            return null;
        } else return null;
    }

}
