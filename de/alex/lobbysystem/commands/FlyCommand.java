package de.alex.lobbysystem.commands;

import de.alex.lobbysystem.events.PlayerFlightToggleEvent;
import de.alex.lobbysystem.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    public static void fly(Player playerExact, CommandSender sender) {
        if (playerExact != null) {
            if (playerExact.getName().equals(sender.getName())) {
                if (PlayerFlightToggleEvent.allowDoubleJump.contains(playerExact)) {
                    PlayerFlightToggleEvent.allowDoubleJump.remove(playerExact);
                    playerExact.sendMessage(utils.colorString("&9Lobby &8» &7Du kannst &ajetzt &eFliegen!"));
                    playerExact.setAllowFlight(true);
                    playerExact.setFlying(true);
                } else {
                    PlayerFlightToggleEvent.allowDoubleJump.add(playerExact);
                    playerExact.sendMessage(utils.colorString("&9Lobby &8» &7Du kannst &cnicht &7mehr &eFliegen!"));
                    playerExact.setFlying(false);
                }
            } else {
                if (PlayerFlightToggleEvent.allowDoubleJump.contains(playerExact)) {
                    PlayerFlightToggleEvent.allowDoubleJump.remove(playerExact);
                    playerExact.sendMessage(utils.colorString("&9Lobby &8» &7Du kannst &ajetzt &eFliegen!"));
                    playerExact.setAllowFlight(true);
                    playerExact.setFlying(true);
                } else {
                    PlayerFlightToggleEvent.allowDoubleJump.add(playerExact);
                    playerExact.sendMessage(utils.colorString("&9Lobby &8» &7Du kannst &cnicht &7mehr &eFliegen!"));
                    playerExact.setFlying(false);
                }
            }
        } else sender.sendMessage(utils.colorString("&9Lobby &8» &7Der Spieler ist &cnicht &7Online!"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = ((Player) sender).getPlayer();
            if (args.length == 0 && (utils.permissionMSG("lobby.fly", p))) {
                fly(p, sender);
            }
            if (args.length >= 1 && utils.permissionMSG("lobby.fly.other", p)) {
                fly(Bukkit.getPlayerExact(args[0]), sender);
            }
        }
        return false;
    }
}
