package de.alex.lobbysystem.commands;

import de.alex.lobbysystem.events.PlayerFlightToggleEvent;
import de.alex.lobbysystem.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = ((Player) sender).getPlayer();
            if(utils.permissionMSG("lobby.fly", p)) {
                if(args.length == 0) {
                    fly(p,sender);
                } if(args.length >= 1) {
                    fly(Objects.requireNonNull(Bukkit.getPlayer(args[0])), sender);
                }
            }
        }
        return false;
    }

    private void fly(Player p, CommandSender s) {
        if(s.getName().equals(p.getName())) {
            if(p.isFlying()) {
                p.sendMessage(utils.colorString("&9Lobby &8» &7Du kannst nun &cnicht &7mehr &eFliegen&7!"));
                PlayerFlightToggleEvent.allowDoubleJump.add(p);
                p.setFlying(false);
            } else {
                p.sendMessage(utils.colorString("&9Lobby &8» &7Du kannst &anun &eFliegen&7!"));
                PlayerFlightToggleEvent.allowDoubleJump.remove(p);
                p.setAllowFlight(true);
                p.setAllowFlight(true);
            }
        } else {
            if(p.isOnline()) {
                if(p.getAllowFlight()) {
                    p.sendMessage(utils.colorString("&9Lobby &8» &7Du kannst nun &cnicht &7mehr &eFliegen&7!"));
                    PlayerFlightToggleEvent.allowDoubleJump.add(p);
                    p.setFlying(false);
                } else {
                    p.sendMessage(utils.colorString("&9Lobby &8» &7Du kannst &anun &eFliegen&7!"));
                    PlayerFlightToggleEvent.allowDoubleJump.remove(p);
                    p.setAllowFlight(true);
                    p.setAllowFlight(true);
                }
            } else s.sendMessage(String.format("&9Lobby &8» &7Der Spieler &e%s &7ist &cnicht &7online!", p.getDisplayName()));
        }
    }
}
