package de.alex.lobbysystem.commands;

import de.alex.lobbysystem.enums.Permissions;
import de.alex.lobbysystem.main.Main;
import de.alex.lobbysystem.utils.utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Objects;

public class PositionCommand implements CommandExecutor {

    YamlConfiguration posyaml = YamlConfiguration.loadConfiguration(Main.getInstance().locationfile);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (utils.permissionMSG(Permissions.PositionCommand.name(), p)) {
                if (args.length == 0) {
                    p.sendMessage(utils.colorString("&7 \n &9Lobby &8» &e&lPositions \n &7» &a/positions &2list \n &7» &a/positions &2set <name> \n &7» &a/positions &2remove \n &7» &a/positions &2tp <name>"));
                } else switch (args[0].toLowerCase()) {
                    case "list":
                        if (posyaml.getConfigurationSection("Location.").getKeys(false).size() != 0) {
                            int time = 0;
                            for (String name : posyaml.getConfigurationSection("Location.").getKeys(false)) {
                                Location loc = posyaml.getLocation("Location." + name);
                                p.sendMessage(utils.colorString("&9Lobby &8» &7(&f%time&7) &eName &8» &7%name &8| &9X &8» &7%x &8| &9Y &8» &7%y &8| &9Z &8» &7%z".replace("%time", Integer.toString(time)).replace("%name", name).replace("%x", Double.toString(loc.getX())).replace("%y", Double.toString(loc.getY())).replace("%z", Double.toString(loc.getZ()))));
                                time++;
                            }
                        } else p.sendMessage(utils.colorString("&9Lobby &8» &7Es gibt &ckeine &7locations!"));
                        break;
                    case "set":
                        if (args.length == 2) {
                            if (posyaml.get("Location." + args[1].toLowerCase()) == null) {
                                p.sendMessage(utils.colorString(String.format("&9Lobby &8» &7Die Location &e%s &7wird gesetzt!", args[1].toLowerCase())));
                                posyaml.set("Location." + args[1].toLowerCase(), p.getLocation());
                                try {
                                    posyaml.save(Main.getInstance().locationfile);
                                } catch (IOException e) {
                                    p.sendMessage(utils.colorString(String.format("&9Lobby &8» &7Es gab einen &cFehler&7 beim setzen der Location &e%s&7!", args[1].toLowerCase())));
                                    e.printStackTrace();
                                    break;
                                }
                                p.sendMessage(utils.colorString(String.format("&9Lobby &8» &7Die Location &e%s &7wurde &aerfolgreich &7gesetzt!", args[1].toLowerCase())));
                            } else
                                p.sendMessage(utils.colorString(String.format("&9Lobby &8» &7Die Location &e%s &cexestiert &7schon!", args[1].toLowerCase())));
                        } else p.sendMessage(utils.colorString("&9Lobby &8» &7Bitte gebe einen &cNamen&7 an!"));
                        break;
                    case "remove":
                        if (args.length == 2) {
                            if (posyaml.get("Location." + args[1].toLowerCase()) != null) {

                                posyaml.set("Location." + args[1].toLowerCase(), null);
                                try {
                                    posyaml.save(Main.getInstance().locationfile);
                                } catch (IOException e) {
                                    p.sendMessage(utils.colorString(String.format("&9Lobby &8» &7Es gab einen &cFehler&7 beim entfernen der Location &e%s&7!", args[1].toLowerCase())));
                                    e.printStackTrace();
                                }
                                p.sendMessage(utils.colorString(String.format("&9Lobby &8» &7Die Location &e%s &7wurde &cgelöscht!", args[1].toLowerCase())));
                            } else
                                p.sendMessage(utils.colorString(String.format("&9Lobby &8» &7Die Location &e%s &cexestiert &7nicht!", args[1].toLowerCase())));
                        }
                        p.sendMessage(utils.colorString("&9Lobby &8» &7Bitte gebe einen &cNamen&7 an!"));
                        break;
                    case "tp":
                    case "teleport":
                        if (args.length == 2) {
                            if (posyaml.get("Location." + args[1].toLowerCase()) != null) {
                                p.sendMessage(utils.colorString(String.format("&9Lobby &8» &7Du wurdest zu &e%s&7 teleportiert!", args[1].toLowerCase())));
                                p.teleport(Objects.requireNonNull(posyaml.getLocation("Location." + args[1].toLowerCase())));
                            } else
                                p.sendMessage(utils.colorString(String.format("&9Lobby &8» &7Die Location &e%s &cexestiert &7nicht!", args[1].toLowerCase())));
                        } else p.sendMessage(utils.colorString("&9Lobby &8» &7Bitte gebe einen &cNamen&7 an!"));
                        break;
                    default:
                        p.sendMessage(utils.colorString("&7 \n &9Lobby &8» &e&lPositions \n &7» &a/positions &2list \n &7» &a/positions &2set <name> \n &7» &a/positions &2remove \n &7» &a/positions &2tp <name>"));
                        break;
                }
            }
        }
        return false;
    }
}

