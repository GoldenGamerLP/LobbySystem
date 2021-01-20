package de.alex.lobbysystem.enums;

public enum Permissions {

    PositionCommand("lobby.command.positions"),
    BuildCommandOther("lobby.command.build.other"),
    BuildCommandSelf("lobby.command.build.self"),
    FlyCommandOther("lobby.command.fly.other"),
    FlyCommandSelf("lobby.command.fly.self"),
    CompassItemCommand("lobby.command.compassitem");

    public static final Permissions[] values = values();


    Permissions(String s) {
    }
}
