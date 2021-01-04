package de.alex.lobbysystem.commands;

import de.alex.lobbysystem.events.BlockEvent;
import de.alex.lobbysystem.events.PlayerFlightToggleEvent;
import de.alex.lobbysystem.utils.utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = ((Player) sender).getPlayer();
            if (args.length == 0 && (utils.permissionMSG("lobby.build", p))) {
                build(p, sender);
            }
            if (args.length >= 1 && utils.permissionMSG("lobby.build.other", p)) {
                build(Bukkit.getPlayerExact(args[0]), sender);
            }
        }
        return false;
    }

    private void build(Player playerExact, CommandSender sender) {
        if (playerExact != null) {
            if (playerExact.getName().equals(sender.getName())) {
                if (!BlockEvent.build.contains(playerExact)) {
                    BlockEvent.build.add(playerExact);
                    PlayerFlightToggleEvent.allowDoubleJump.removeIf(player -> PlayerFlightToggleEvent.allowDoubleJump.contains(playerExact));
                    playerExact.setAllowFlight(true);
                    playerExact.setFlying(true);
                    playerExact.sendMessage(utils.colorString("&9Lobby &8» &7Du kannst &anun &eBauen!"));
                    playerExact.setGameMode(GameMode.CREATIVE);
                } else {
                    BlockEvent.build.remove(playerExact);
                    PlayerFlightToggleEvent.allowDoubleJump.add(playerExact);
                    playerExact.sendMessage(utils.colorString("&9Lobby &8» &7Du kannst nun &cnicht &7mehr &eBauen!"));
                    playerExact.setGameMode(GameMode.ADVENTURE);
                }
            } else {
                if (!BlockEvent.build.contains(playerExact)) {
                    BlockEvent.build.add(playerExact);
                    PlayerFlightToggleEvent.allowDoubleJump.removeIf(player -> PlayerFlightToggleEvent.allowDoubleJump.contains(playerExact));
                    playerExact.sendMessage(utils.colorString("&9Lobby &8» &7Du kannst &anun &eBauen!"));
                    sender.sendMessage(utils.colorString(String.format("&9Lobby &8» &e%s &7kann &ajetzt&7 bauen!", playerExact.getDisplayName())));
                    playerExact.setGameMode(GameMode.CREATIVE);
                } else {
                    BlockEvent.build.remove(playerExact);
                    PlayerFlightToggleEvent.allowDoubleJump.add(playerExact);
                    playerExact.sendMessage(utils.colorString("&9Lobby &8» &7Du kannst nun &cnicht &7mehr &eBauen!"));
                    sender.sendMessage(utils.colorString(String.format("&9Lobby &8» &e%s &7kann jetzt &cnicht&7 mehr bauen!", playerExact.getDisplayName())));
                    playerExact.setGameMode(GameMode.ADVENTURE);
                }
            }
        } else sender.sendMessage(utils.colorString("&9Lobby &8» &7Der Spieler ist &cnicht &7Online!"));
    }

}
